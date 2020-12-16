package com.example.capstonedesignproject.VO;

import java.io.Serializable;

public class -ArticleVO implements Serializable {
    private int articleId;
    private String memberId;
    private String nickName;
    private String title;
    private String content;
    private String urlPath;
    private String createTime;

    public ArticleVO(int articleId, String memberId, String title, String content, String urlPath, String createTime) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.urlPath = urlPath;
        this.createTime = createTime;
    }

    public int getArticleId() { return articleId; }
    public String getMemberId() { return memberId; }
    public String getNickName() { return nickName; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getUrlPath() { return urlPath; }
    public String getCreateTime() { return createTime; }
}
