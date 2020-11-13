package com.example.capstonedesignproject.Server;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.capstonedesignproject.view.ETC.HomeActivity.CONNECT_TIME_OUT;

public class FileUploadTask extends AsyncTask<Object, Void, String> {
    private final static String boundary = "aJ123Af2318";
    private final static String LINE_FEED = "\r\n";
    private final static String charset = "utf-8";
    private OutputStream outputStream;
    private PrintWriter writer;
    private String fileName;

    public FileUploadTask(){}

    @Override
    protected String doInBackground(Object... objects) {
        fileName = (String) objects[2];
        Map<String, String> map = new HashMap<>();
        map.put("id", (String) objects[0]);
        return sendMultiPart((File)objects[1], map);
    }

    private String sendMultiPart(File file, Map<String, String> map) {
        try {
            URL url = new URL(HomeActivity.SERVER_URL + "/file/upload.do");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "multipart/form-data;charset=" + charset + ";boundary=" + boundary);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(CONNECT_TIME_OUT);

            outputStream = connection.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            //원하는 데이터 세팅
            for (String key : map.keySet()) {
                addTextPart(key, map.get(key));
            }
            addFilePart("image", file);
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("오류", "아아아아아아아");
        }
        return null;
    }

    private void addTextPart(String name, String value) throws IOException {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    private void addFilePart(String name, File file) throws IOException {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(LINE_FEED);
        writer.flush();
    }
}