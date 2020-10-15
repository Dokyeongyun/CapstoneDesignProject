package com.example.capstonedesignproject.Server;
import android.os.AsyncTask;
import android.util.Log;

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

public class FileDownloadTask extends AsyncTask<Object, Void, String> {
    private final static String boundary = "aJ123Af2318";
    private final static String LINE_FEED = "\r\n";
    private final static String charset = "utf-8";
    private OutputStream outputStream;
    private PrintWriter writer;

    public FileDownloadTask(){}

    @Override
    protected String doInBackground(Object... objects) {

        return sendMultiPart();
    }

    public String sendMultiPart() {
        try {
            URL url = new URL("http://211.222.234.14:8080/gy.do?id=mk");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            outputStream = connection.getOutputStream();

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
        }
        return null;
    }
}