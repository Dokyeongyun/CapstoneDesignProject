package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpImageTest extends AppCompatActivity {
    LinearLayout linear;
    Bitmap mybitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image_test);

        //==================== 적용할 레이아웃 불러오기, 신규 이미지 생성하기 ==================
        linear = findViewById (R.id.linearLayout5);
        ImageView img= new ImageView(this);

        Thread mThread = new Thread ()
        {
            @Override
            public void run() {
                try {
                    URL url1 = new URL("http://211.222.234.14:8080/resources/test.jpg");
                    try {
                        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        //==================== 이미지 파일 읽어오기 =============================

                        InputStream is= conn.getInputStream();
                        mybitmap = BitmapFactory.decodeStream(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace() ;
                }
            }
        };

        mThread.start();
        try {
            mThread.join();
            img.setImageBitmap(mybitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        linear.addView(img);
        Toast.makeText(getApplicationContext(),"이미지를 불러왔습니다.", Toast.LENGTH_LONG).show();
    }
}