package com.jigangseon.psg;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Email_login extends AppCompatActivity {

    Button email_login_button;


    TextView find_email, find_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        email_login_button = findViewById(R.id.email_login_button);
        find_email = findViewById(R.id.find_email);
        find_pw = findViewById(R.id.find_pw);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.email_login_button:
                        Log.i("button", "로그인버튼클릭");
                        break;
                    case R.id.find_email:
                        Log.i("이메일", "이메일찾기클릭");
                        break;
                    case R.id.find_pw:

                        Log.i("비밀번호", "비밀번호찾기 클릭");
                        break;

                }
            }
        };

        email_login_button.setOnClickListener(clickListener);
        find_email.setOnClickListener(clickListener);
        find_pw.setOnClickListener(clickListener);


    }
}
