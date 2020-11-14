package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.FileDownloadTask;
import com.example.capstonedesignproject.Server.FileUploadTask;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    @BindView(R.id.IB_photo) ImageButton IB_photo;
    @BindView(R.id.ET_title) EditText ET_title;
    @BindView(R.id.ET_content) EditText ET_content;
    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.BT_addPhoto) void AddPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.IB_photo) void ChangePhoto() {
        AddPhoto();
    }

    @OnClick(R.id.BT_complete) void writeComplete() {
        if (ET_title.getText().toString().equals("") || ET_title.getText() == null) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ET_content.getText().toString().equals("") || ET_content.getText() == null) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String createTime = format.format(new Date());
        String id = HomeActivity.memberID;
        String title = ET_title.getText().toString();
        String content = ET_content.getText().toString();
        String fileName = new Date().getTime() + ".jpg";

        String result = "";
        String fileUploadResult = "";

        try {
            if (photoUri != null) { // 이미지 첨부하여 게시글 작성 시
                File file = new File(getPathFromUri(photoUri));
                try { fileUploadResult = new FileUploadTask(this).execute(id, file, fileName).get();
                } catch (Exception e) { Toast.makeText(this, "게시글 작성에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } finally { result = new Task(this).execute("article/insert.do", id, title, content, "true", fileName, createTime).get();
                }
                checkResult(true, fileUploadResult, result);
            } else { // 이미지 첨부 없이 게시글 작성 시
                result = new Task(this).execute("article/insert.do", id, title, content, "", "", createTime).get();
                checkResult(false, result);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkResult(boolean isAttached, String... result){
        if(result[0] == null){
            Toast.makeText(this, "네트워크 상태가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isAttached){
            if(result[0].equals("Success") && result[1].equals("\"success\"")){
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
        if(!isAttached){
            if(result[0].equals("\"success\"")){
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
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
                    InputStream in = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));
                    Bitmap img = BitmapFactory.decodeStream(in);
                    if (img != null) {
                        // TODO setImageBitmap 했을 때 사진이 90도 회전되어 첨부되는 현상 수정하기
                        IB_photo.setVisibility(View.VISIBLE);
                        IB_photo.setImageBitmap(img);
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
