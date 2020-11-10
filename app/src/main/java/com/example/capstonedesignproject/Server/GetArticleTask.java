package com.example.capstonedesignproject.Server;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.capstonedesignproject.Data.ArticleData;
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

import static com.example.capstonedesignproject.view.ETC.HomeActivity.CONNECT_TIME_OUT;

public class GetArticleTask extends AsyncTask<Object, Void, List<ArticleData>> {
    public GetArticleTask() {
    }

    @Override
    protected List<ArticleData> doInBackground(Object... objects) {

        if ((objects[0]).equals("article/selectOne.do")) {
            return getArticle((String) objects[0], (int) objects[1]);
        } else if ((objects[0]).equals("article/get.do")) {
            return getArticleList((String) objects[0], (int) objects[1]);
        }
        return null;
    }

    public List<ArticleData> getArticleList(String path, int num) {
        try {
            URL url = new URL(HomeActivity.SERVER_URL + "/" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST"); // 데이터를 POST방식으로 전송
            connection.setConnectTimeout(CONNECT_TIME_OUT);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            bw.write("num=" + num);
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

                JSONObject object = new JSONObject(sb.toString());
                JSONArray jsonArray = object.getJSONArray("my");

                ArrayList<ArticleData> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String articleId = obj.getString("articleId");
                    String memberId = obj.getString("memberId");
                    String title = obj.getString("title");
                    String content = obj.getString("content");
                    String urlPath = obj.getString("urlPath");
                    String createTime = obj.getString("createTime");

                    list.add(new ArticleData(Integer.parseInt(articleId), memberId, title, content, urlPath, createTime));
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

    public List<ArticleData> getArticle(String path, int articleID) {
        try {
            URL url = new URL(HomeActivity.SERVER_URL + "/" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST"); // 데이터를 POST방식으로 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            bw.write("articleId=" + articleID);
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

                JSONObject object = new JSONObject(sb.toString());
                JSONArray jsonArray = object.getJSONArray("my");

                ArrayList<ArticleData> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String articleId = obj.getString("articleId");
                    String memberId = obj.getString("memberId");
                    String title = obj.getString("title");
                    String content = obj.getString("content");
                    String urlPath = obj.getString("urlPath");
                    String createTime = obj.getString("createTime");

                    list.add(new ArticleData(Integer.parseInt(articleId), memberId, title, content, urlPath, createTime));
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

}
