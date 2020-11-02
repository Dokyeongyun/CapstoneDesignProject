package com.example.capstonedesignproject.Server;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChabakjiInfoTask extends AsyncTask<Object, Void, List<ChabakjiDAO>> {

    private static String query = "";
    private static String requestUrl = "";
    public ChabakjiInfoTask() { }

    @Override
    protected List<ChabakjiDAO> doInBackground(Object... objects) {
        requestUrl = (String) objects[0];
        InitQuery(requestUrl, objects[1]);
        return getChabak();
    }

    private List<ChabakjiDAO> getChabak() {
        try {
            URL url = new URL(HomeActivity.SERVER_URL + "/chabak/" + requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST"); // 데이터를 POST방식으로 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            bw.write(query);
            bw.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                sb.append("{\"my\":");
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                sb.append("}");
                Log.d("도경윤", sb.toString()+"\n");

                JSONObject object = new JSONObject(sb.toString());
                JSONArray jsonArray = object.getJSONArray("my");

                ArrayList<ChabakjiDAO> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String place_name = obj.getString("place_name");
                    String address = obj.getString("address");
                    String utility = obj.getString("utility");
                    String notify = obj.getString("notify");
                    String introduce = obj.getString("introduce");
                    String filePath = obj.getString("filePath");
                    int jjim = obj.getInt("jjim");
                    double latitude = obj.getDouble("latitude");
                    double longitude = obj.getDouble("longitude");

                    list.add(new ChabakjiDAO(place_name, address, utility, notify, introduce, filePath, jjim, latitude, longitude));
                }
                return list;

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void InitQuery(String url, Object... objects){
        switch (url) {
            case "get.do":
                query = "num=" + objects[0];
                break;
            case "getKey.do":
                query = "key=" + objects[0];
                break;
            case "getAds.do":
                query = "address=" + objects[0];
                break;
        }
    }
}
