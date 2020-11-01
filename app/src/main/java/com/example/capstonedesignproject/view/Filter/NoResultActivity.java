package com.example.capstonedesignproject.view.Filter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

public class NoResultActivity extends AppCompatActivity {

    private Button BT_research, BT_moveToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_result);

        Init();
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.BT_research:
                        setResult(1);
                        finish();
                        break;
                    case R.id.BT_moveToHome:
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                }
            }
        };
        BT_research.setOnClickListener(onClickListener);
        BT_moveToHome.setOnClickListener(onClickListener);

    }

    private void Init(){
        BT_research = findViewById(R.id.BT_research);
        BT_moveToHome = findViewById(R.id.BT_moveToHome);
    }
}
