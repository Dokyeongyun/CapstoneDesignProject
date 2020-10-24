package com.example.capstonedesignproject.Data;


import android.graphics.Bitmap;

public class ArticleData {
    // 게시글에 포함되어야 할 정보
    // 게시글번호, 등록날짜 및 시간, 작성자, 제목, 사진, 본문, (댓글)
    private int articleId;
    private String memberId;
    private String title;
    private String content;
    private String urlPath;
    private String createTime;

    private Bitmap image;

    public ArticleData(int articleId, String memberId, String title, String content, String urlPath, String createTime) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.urlPath = urlPath;
        this.createTime = createTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
