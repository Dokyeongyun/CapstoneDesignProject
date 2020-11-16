package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;

    @BindView(R.id.IB_suggestImage) ImageButton IB_suggestImage;
    @BindView(R.id.BT_back_suggest) ImageButton BT_back_suggest;
    @BindView(R.id.ET_suggestPlaceName) EditText ET_suggestPlaceName;
    @BindView(R.id.BT_suggestAddress) Button BT_suggestAddress;
    @BindView(R.id.ET_suggestIntro) EditText ET_suggestIntro;
    @BindView(R.id.ET_suggestPhoneNum) EditText ET_suggestPhoneNum;
    @BindView(R.id.ET_suggestETC) EditText ET_suggestETC;
    @BindView(R.id.BT_suggestComplete) Button BT_suggestComplete;

    String[] suggestInfo = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.IB_suggestImage) void AddPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.BT_back_suggest) void back() { finish(); }
    @OnClick(R.id.BT_suggestComplete) void suggestComplete() {
        if(CheckEditText()){
            // TODO DB 삽입
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < suggestInfo.length; i++) {
                String s = suggestInfo[i];
                sb.append(s).append(" ");
            }
            finish();
        }

    }

    private boolean CheckEditText(){
        suggestInfo[0] = ET_suggestPlaceName.getText().toString();
        suggestInfo[1] = BT_suggestAddress.getText().toString();
        suggestInfo[2] = ET_suggestIntro.getText().toString();
        suggestInfo[3] = ET_suggestPhoneNum.getText().toString();
        suggestInfo[4] = ET_suggestETC.getText().toString();

        for (String s : suggestInfo) {
            if (s == null || s.equals("")) {
                Toast.makeText(this, "입력하지 않은 정보가 있습니다. 모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    if (img != null) {
                        IB_suggestImage.setImageBitmap(img);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
