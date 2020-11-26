package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.FileUploadTask;
import com.example.capstonedesignproject.Server.Task;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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
    Uri photoUri;

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
            String result = "";
            String fileUploadResult = "";
            String fileName = new Date().getTime() + ".jpg";
            File file = new File(getPathFromUri(photoUri));

            // 이미지 파일 먼저 업로드
            try {
                fileUploadResult = new FileUploadTask(this).execute("suggest", file, fileName).get();
            } catch (Exception e) {
                Toast.makeText(this, "이미지 업로드에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 이미지 파일 업로드 되면 나머지 정보 DB 삽입
            if(fileUploadResult.equals("Success")){
                try{
                    result = new Task(this).execute("chabak/suggest.do",
                            suggestInfo[0], suggestInfo[1], suggestInfo[2], suggestInfo[3], fileName, String.valueOf(37.665), String.valueOf(125.668)).get();
                } catch (Exception e){
                    Toast.makeText(this, "차박지 등록에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                return;
            }

            if(result.equals("\"success\"")){
                Toast.makeText(this, "참여해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "차박지 등록에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
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

        // 이미지 첨부했는지 확인
        if(photoUri == null){
            Toast.makeText(this, "차박지 사진을 첨부해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /* Uri를 입력받아 파일의 절대경로를 리턴하는 메서드 */
    public String getPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        cursor.close();
        return path;
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
                        photoUri = data.getData();
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
