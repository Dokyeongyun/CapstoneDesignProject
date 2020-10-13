package com.example.capstonedesignproject.Server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Task extends AsyncTask<String, Void, String> {
    private static String ip = "211.222.234.14"; // IP
    private String serverip = "http://"+ip+":8080/"; // JSP 주소
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
            URL url = new URL(serverip += "member/insertTest.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); // 데이터를 POST방식으로 전송
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 매개변수로 주어진 String[] 배열의 값을 sendMsg에 POST 방식으로 저장
            // ex) sendMsg = "type=" + strings[0] + "&userNo=" + strings[1];
            if(strings[0].equals("Join")){
                sendMsg = "id=" + strings[1] + "&nickName=" + strings[2] +  "&pw=" + strings[3];
            }

            osw.write(sendMsg);
            osw.flush();

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
}
