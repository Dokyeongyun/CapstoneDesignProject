package com.example.capstonedesignproject.Server;

import android.os.AsyncTask;
import android.os.FileUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Task extends AsyncTask<String, Void, String> {
    private static String ip = "211.222.234.14"; // IP
    private String serverip = "http://" + ip + ":8080/"; // JSP 주소
    private String sendMsg, receiveMsg;

    Task(String sendmsg) {
        this.sendMsg = sendmsg;
    }

    public Task() {
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            HttpURLConnection conn = returnConnection(strings[0]);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8));

            // 매개변수로 주어진 String[] 배열의 값을 sendMsg에 POST 방식으로 저장
            // ex) sendMsg = "type=" + strings[0] + "&userNo=" + strings[1];
            if (strings[0].equals("member/insert.do")) {
                sendMsg = "id=" + strings[1] + "&nickName=" + strings[2] + "&password=" + strings[3];
            } else if (strings[0].equals("article/insert.do")) {
                sendMsg = "id=" + strings[1] + "&title=" + strings[2] + "&content=" + strings[3]
                        + "&isAttached=" + strings[4] + "&fileName=" + strings[5] + "&createTime=" + strings[6];
            } else if (strings[0].equals("member/login.do")) {
                sendMsg = "id=" + strings[1] + "&password=" + strings[2];
            } else if (strings[0].equals("member/jjim.do")) {
                sendMsg = "id=" + strings[1] + "&placeName=" + strings[2];
            } else if (strings[0].equals("member/jjim.undo")) {
                sendMsg = "id=" + strings[1] + "&placeName=" + strings[2];
            }

            bw.write(sendMsg);
            bw.flush();

            // jsp와의 통신이 정상적으로 이루어졌다면,
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder sb = new StringBuilder();

                // jsp에서 보낸 값들을 모두 수신
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                receiveMsg = sb.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }

    private HttpURLConnection returnConnection(String urlString) {
        try {
            URL url = new URL(serverip += urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 데이터를 POST방식으로 전송
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
