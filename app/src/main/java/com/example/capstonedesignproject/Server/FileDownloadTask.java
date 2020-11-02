package com.example.capstonedesignproject.Server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileDownloadTask extends AsyncTask<String, Void, Bitmap> {

    private Bitmap mybitmap;

    @Override
    protected Bitmap doInBackground(String... strings) {
        String filePath = strings[0];
        return getImage(filePath);
    }

    private Bitmap getImage(final String filePath) {
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url1 = new URL(HomeActivity.SERVER_URL+ "/" + filePath);
                    try {
                        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        mybitmap = BitmapFactory.decodeStream(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
        try {
            mThread.join();
            return mybitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}