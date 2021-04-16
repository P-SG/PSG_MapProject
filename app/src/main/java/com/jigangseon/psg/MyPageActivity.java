package com.jigangseon.psg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        // 커스텀 툴바를 액션바로 설정
        Toolbar toolbar = findViewById(R.id.my_page_toolbar);
        setSupportActionBar(toolbar);

        // 툴바에 뒤로가기 버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 앱 이름 사용하지 않기
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 프로필 동그랗게
        ImageView profileImage = findViewById(R.id.mypage_profile_image);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);
    }

    // 툴바의 메뉴를 선택했을때 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // 왼쪽 상단 버튼 눌렀을 때
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}