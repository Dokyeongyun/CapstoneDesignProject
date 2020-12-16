package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.VO.ArticleVO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.FileUploadTask;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.Server.SetApplication;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    @BindView(R.id.IB_photo) ImageButton IB_photo;
    @BindView(R.id.ET_title) EditText ET_title;
    @BindView(R.id.ET_content) EditText ET_content;
    @BindView(R.id.TV_title) TextView TV_title;
    Uri photoUri;
    String writeMode = "";
    ArticleVO articleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        writeMode = intent.getStringExtra("writeMode");

        if(writeMode.equals("UPDATE")){
            TV_title.setText("글 수정하기");
            articleData = (ArticleVO) intent.getSerializableExtra("articleData");
            ET_title.setText(articleData.getTitle());
            ET_content.setText(articleData.getContent());
            if(articleData.getUrlPath().equals("")){
                IB_photo.setVisibility(View.GONE);
            }
            Glide.with(this)
                    .load(HomeActivity.SERVER_URL + articleData.getUrlPath())
                    .placeholder(R.drawable.button_border_gray)
                    .into(IB_photo);
        }
    }

    /**
     * 사진 첨부하기
     */
    @OnClick(R.id.BT_addPhoto) void AddPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.IB_photo) void ChangePhoto() { AddPhoto(); }

    /**
     * 글 쓰기 OR 글 수정하기
     */
    @OnClick(R.id.BT_complete) void writeComplete() {
        if (ET_title.getText().toString().equals("") || ET_title.getText() == null) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ET_content.getText().toString().equals("") || ET_content.getText() == null) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // DB에 삽입할 데이터 정리
        String id = HomeActivity.memberID;
        String title = ET_title.getText().toString();
        String content = ET_content.getText().toString();
        String fileName = id + "_" + new Date().getTime() + ".jpg";

        boolean isAttached = false;

        if (photoUri != null) { // 이미지 첨부하여 게시글 작성 및 수정 시 파일 업로드 먼저하기
            isAttached = true;
            File file = new File(getPathFromUri(photoUri));

            try {
                new FileUploadTask(this).execute(id, file, fileName);
            } catch (Exception e) {
                Toast.makeText(this, "게시글 작성에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(!writeMode.equals("UPDATE")){ // 새로운 글 작성
            writeArticle(id, title, content, fileName, isAttached);
        }else{ // 글 수정
            updateArticle(articleData.getArticleId(), title, content, fileName, isAttached);
        }
    }

    /**
     * 새로운 글 작성하기
     */
    private void writeArticle(String memberId, String title, String content, String fileName, boolean isAttached){
        if(!isAttached){
            fileName = "";
        }
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getArticleService().writeArticle(memberId, title, content, fileName);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "게시글 작성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        setResult(1);
                        finish();
                    }
                });
    }

    /**
     * 글 수정하기
     */
    private void updateArticle(int articleId, String title, String content, String fileName, boolean isAttached){
        if(!isAttached){
            fileName = "";
        }
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getArticleService().updateArticle(articleId, title, content, fileName);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "게시글 작성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        setResult(1);
                        finish();
                    }
                });
    }

    /**
     *  Uri를 입력받아 파일의 절대경로를 리턴하는 메서드 */
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
