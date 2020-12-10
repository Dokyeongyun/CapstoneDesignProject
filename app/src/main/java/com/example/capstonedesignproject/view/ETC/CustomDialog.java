package com.example.capstonedesignproject.view.ETC;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstonedesignproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialog extends Dialog {

    @BindView(R.id.TV_dialogTitle) TextView TV_dialogTitle;
    @BindView(R.id.TV_dialogContent) TextView TV_dialogContent;
    @BindView(R.id.BT_dialogOK) Button BT_dialogOK;
    @BindView(R.id.BT_dialogCancel) Button BT_dialogCancel;

    private String title;
    private String content;
    private View.OnClickListener OKListener;
    private View.OnClickListener CancelListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams window = new WindowManager.LayoutParams();
        window.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.dimAmount = 0.8f;
        getWindow().setAttributes(window);

        setContentView(R.layout.activity_custom_dialog);
        ButterKnife.bind(this);

        TV_dialogTitle.setText(title);
        TV_dialogContent.setText(content);

        if(OKListener != null && CancelListener != null){
            BT_dialogOK.setOnClickListener(OKListener);
            BT_dialogCancel.setOnClickListener(CancelListener);
        }
    }


    public CustomDialog(Context context, String title, String content, View.OnClickListener ok, View.OnClickListener cancel){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
        this.content = content;
        this.OKListener = ok;
        this.CancelListener = cancel;
    }
}
