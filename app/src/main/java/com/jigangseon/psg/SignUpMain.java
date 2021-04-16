package com.jigangseon.psg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_main);
    }

    public void onClick(View view){
        Intent intent = null;
        int id = view.getId();  // 해당 view의 id를 가져옴 (R.id.ㅇㅇㅇ)
        switch (id){
            // 로그인
            case R.id.sign_in_button:
                intent = new Intent(this, LoginActivity.class);
                break;
            // 회원 가입
/*            case R.id.sign_up_button:
                intent = new Intent(this, LoginActivity.class);
                break;*/
            // 비회원 이용
            case R.id.guest_in_button:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }

//    public void signIn(View view){
////        Intent intent = new Intent(getApplicationContext(), SignIn.class);
////        startActivity(intent);
//    }
//    public void signUp(View view){
////        Intent intent = new Intent(getApplicationContext(), SignUp.class);
////        startActivity(intent);
//    }
//    public void guestIn(View view){
////        Intent intent = new Intent(getApplicationContext(), GuestIn.class);
////        startActivity(intent);
//    }
}