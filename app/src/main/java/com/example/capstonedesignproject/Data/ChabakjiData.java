package com.example.capstonedesignproject.Data;

import android.graphics.Bitmap;

public class ChabakjiData {
    public String chabakjiName;
    public String chabakjiAddr;
    public String chabakjiUtil;
    public String chabakjiIntro;
    public String chabakjiRating;
    public String filePath; // 사진 파일 경로


    public ChabakjiData(String chabakjiName, String chabakjiAddr, String chabakjiUtil, String chabakjiIntro,
                        String chabakjiRating, String filePath) {
        this.chabakjiName = chabakjiName;
        this.chabakjiAddr = chabakjiAddr;
        this.chabakjiUtil = chabakjiUtil;
        this.chabakjiIntro = chabakjiIntro;
        this.chabakjiRating = chabakjiRating;
        this.filePath = filePath;
    }
}

