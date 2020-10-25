package com.example.capstonedesignproject.Data;

import android.graphics.Bitmap;

public class ChabakjiData {
    public String chabakjiName;
    public String chabakjiAddr;
    public String chabakjiUtil;
    public String chabakjiIntro;
    public String chabakjiRating;
    public Bitmap chabakjiImage;

    public ChabakjiData(String chabakjiName, String chabakjiAddr, String chabakjiUtil, String chabakjiIntro,
                        String chabakjiRating, Bitmap chabakjiImage) {
        this.chabakjiName = chabakjiName;
        this.chabakjiAddr = chabakjiAddr;
        this.chabakjiUtil = chabakjiUtil;
        this.chabakjiIntro = chabakjiIntro;
        this.chabakjiRating = chabakjiRating;
        this.chabakjiImage = chabakjiImage;
    }
}

