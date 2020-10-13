package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.capstonedesignproject.Server.FileUploadTask;
import com.example.capstonedesignproject.Server.Task;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    ImageButton IB_photo;
    Uri photoUri;
    EditText ET_title, ET_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        ET_title = findViewById(R.id.ET_title);
        ET_content = findViewById(R.id.ET_content);
        IB_photo = findViewById(R.id.IB_photo);
    }

    // 카메라 버튼 클릭 시 -> 사진 첨부기능
    public void AddPhoto(View view) {
        // 갤러리를 열어 사진을 고를 수 있도록 하는 코드 -> 사진 선택하면 onActivityResult()로 ㄱㄱ
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    // 첨부한 사진 클릭 시 -> 사진 첨부기능 재실행
    public void ChangePhoto(View view) {
        AddPhoto(view);
    }

    // 게시글 작성 완료
    public void writeComplete(View view) {
        // TODO 글 쓰기 완료 -> DB 에 저장 및 액티비티 종료
        if(ET_title.getText()!=null && !ET_title.getText().toString().equals("")){
            if(ET_content.getText()!=null && !ET_content.getText().toString().equals("")){
                // DB 삽입

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
                String createTime = format.format(new Date());
                String id = "익명";
                String title = ET_title.getText().toString();
                String content = ET_content.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("title", title);
                map.put("content", content);
                map.put("createTime", createTime);

                String result = "";

                try{
                    if(photoUri!=null){ // 이미지 첨부하여 게시글 작성 시
                        File file = new File(getPathFromUri(photoUri));
                        result = new FileUploadTask().execute("article/write.do", id, title, content, file, createTime).get();
                    }else { // 이미지 첨부 없이 게시글 작성 시
                        result = new Task().execute("article/write.do", id, title, content, "", createTime).get();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if(result != null && !result.equals("") && result.equals("WritePost_OK")){
                    finish();
                    Toast.makeText(this, "결과: "+ result + "  게시글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "결과: "+ result + "  잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /* Uri를 입력받아 파일의 절대경로를 리턴하는 메서드 */
    public String getPathFromUri(Uri uri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
/*        Uri uri2 = Uri.fromFile(new File(path));
        Log.d("하", "getRealPathFromURI(), path : " + uri2.toString());*/
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
                    if(img!=null){
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
