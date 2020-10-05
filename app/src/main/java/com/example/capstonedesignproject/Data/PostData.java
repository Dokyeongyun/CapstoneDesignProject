package com.example.capstonedesignproject.Data;


public class PostData {
    // 게시글에 포함되어야 할 정보
    // 게시글번호, 등록날짜 및 시간, 작성자, 제목, 사진, 본문, (댓글)
    private int pID;
    private String date;
    private String writer;
    private String title;
    private String content;
    private int picture;

    public PostData(String date, String writer, String title, String content, int picture){
        this.date = date;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.picture = picture;
    }

    public int getpID() {
        return pID;
    }

    public String getDate() {
        return date;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getPicture() {
        return picture;
    }
}
