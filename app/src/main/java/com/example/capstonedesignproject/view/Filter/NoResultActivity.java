package com.example.capstonedesignproject.view.Filter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoResultActivity extends AppCompatActivity {

    @BindView(R.id.BT_research) Button BT_research;
    @BindView(R.id.BT_moveToHome) Button BT_moveToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_result);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.BT_research) void ClickResearch(){
        setResult(1);
        finish();
    }
    @OnClick(R.id.BT_moveToHome) void ClickMoveToHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
