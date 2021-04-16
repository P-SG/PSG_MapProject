package com.jigangseon.psg.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Stack;
import java.util.zip.Inflater;

import com.jigangseon.psg.R;
import com.jigangseon.psg.search.*;
import com.jigangseon.psg.subway_line.*;

import static java.security.AccessController.getContext;

public class Search extends AppCompatActivity {

    /*String[] sub_array = getResources().getStringArray(R.array.subway_line_name);
     */

    String[] sub_name = {"subway_line_1", "subway_line_2", "subway_line_3", "subway_line_4", "subway_line_5",
            "subway_line_6", "subway_line_7", "subway_line_8", "subway_line_9"};


    TextView subway_search;
    TextView direct_input_text;


    LinearLayout linearLayout;
    //프래그먼트 객체화
    Subway_fragment subway_fragment = new Subway_fragment();
    Subway_fragment_line_1 subway_fragment_line_1 = new Subway_fragment_line_1();
    Subway_fragment_line_2 subway_fragment_line_2 = new Subway_fragment_line_2();
    Subway_fragment_line_3 subway_fragment_line_3 = new Subway_fragment_line_3();
    Subway_fragment_line_4 subway_fragment_line_4 = new Subway_fragment_line_4();
    Subway_fragment_line_5 subway_fragment_line_5 = new Subway_fragment_line_5();
    Subway_fragment_line_6 subway_fragment_line_6 = new Subway_fragment_line_6();
    Subway_fragment_line_7 subway_fragment_line_7 = new Subway_fragment_line_7();
    Subway_fragment_line_8 subway_fragment_line_8 = new Subway_fragment_line_8();
    Subway_fragment_line_9 subway_fragment_line_9 = new Subway_fragment_line_9();
    Subway_fragment_line_bundang subway_fragment_line_bundang = new Subway_fragment_line_bundang();
    Subway_fragment_line_gimpodosi subway_fragment_line_gimpodosi = new Subway_fragment_line_gimpodosi();
    Subway_fragment_line_gonghang subway_fragment_line_gonghang = new Subway_fragment_line_gonghang();
    Subway_fragment_line_gyeongchun subway_fragment_line_gyeongchun = new Subway_fragment_line_gyeongchun();
    Subway_fragment_line_gyeonggang subway_fragment_line_gyeonggang = new Subway_fragment_line_gyeonggang();
    Subway_fragment_line_gyeongui subway_fragment_line_gyeongui = new Subway_fragment_line_gyeongui();
    Subway_fragment_line_incheon1hoseon subway_fragment_line_incheon1hoseon = new Subway_fragment_line_incheon1hoseon();
    Subway_fragment_line_incheon2hoseon subway_fragment_line_incheon2hoseon = new Subway_fragment_line_incheon2hoseon();
    Subway_fragment_line_seohae subway_fragment_line_seohae = new Subway_fragment_line_seohae();
    Subway_fragment_line_sinbundang subway_fragment_line_sinbundang = new Subway_fragment_line_sinbundang();
    Subway_fragment_line_uijeongbugyeong subway_fragment_line_uijeongbugyeong = new Subway_fragment_line_uijeongbugyeong();
    Subway_fragment_line_uisinseolgyeong subway_fragment_line_uisinseolgyeong = new Subway_fragment_line_uisinseolgyeong();
    Subway_fragment_line_yongingyeong subway_fragment_line_yongingyeong = new Subway_fragment_line_yongingyeong();

    //프래그먼트 확인 변수
    int i = 0;


    private ArrayList<Integer> imageList;
    ViewPager viewPager;
    private static final int DP = 24;
    private ListView subwayList;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       /* ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new Subway_ViewPagerAdapter(this, imageList));
*/

        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        linearLayout = (LinearLayout) findViewById(R.id.subway_line);
        ImageView line_1 = new ImageView(this);
        line_1.setImageResource(R.drawable.subway_line_1);
        line_1.setLayoutParams(layoutParams);


        ImageView line[] = new ImageView[22];
        for (i = 0; i < line.length; i++) {

            line[i] = new ImageView(this);
            line[i].setLayoutParams(layoutParams);
            TypedArray subway_images = getResources().obtainTypedArray(R.array.subway_line);
            Drawable subway_image = subway_images.getDrawable(i);

//            int lid = getResources().getIdentifier(sub_image[i], "drawable", getPackageName());
            line[i].setId(i);
            line[i].setImageDrawable(subway_image);
            line[i].setOnClickListener(this::Click);
            linearLayout.addView(line[i]);
        }
        Subway_fragment subway_fragment = new Subway_fragment();
        setFragment(subway_fragment);
        i = 0;

        //역주변 아이디값 갖고옴
        subway_search = findViewById(R.id.subway_search);
        //역주변 클릭 리스너
        subway_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subway_fragment subway_fragment = new Subway_fragment();
                setFragment(subway_fragment);
                i = 0;
            }
        });




        //직접입력 아이디값 가져옴
        direct_input_text = findViewById(R.id.direct_input_text);
        //직접입력 클릭시
        direct_input_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),direct_input.class);
                startActivity(intent);
            }
        });



    }

    //프래그먼트 생성하는 메소드
    public Fragment setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.subway_fragment, fragment).commitAllowingStateLoss();
        return fragment;
    }

    //프래그먼트 지우는 메소드
    public Fragment removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment).commitAllowingStateLoss();

        return fragment;
    }

    //뒤로가기 버튼클릭시
    @Override
    public void onBackPressed() {

        //프래그먼트가 호선들이 클릭되어있을때 뒤로가기버튼을 누르면 초기화면으로 이동하게만듬
        if (i != 0) {
            setFragment(subway_fragment);
            i = 0;

        }
        //차후에 초기화면일때 뒤로가기 버튼을 누르면 페이지 이동하게 만들면 된다.
        else if (i == 0) {
            finish();


        }

    }

    //버튼 클릭 메소드
    public void Click(View view) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //클릭된 값 확인 스위치
        switch (view.getId()) {
            case 0:

                if (i == 1) {
                    removeFragment(subway_fragment_line_1);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_1 = new Subway_fragment_line_1();
                    setFragment(subway_fragment_line_1);
                    transaction.addToBackStack(null);

                    i = 1;
                }
                break;
            case 1:
                if (i == 2) {
                    removeFragment(subway_fragment_line_2);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_2 = new Subway_fragment_line_2();
                    setFragment(subway_fragment_line_2);
                    transaction.addToBackStack(null);
                    i = 2;
                }
                break;
            case 2:
                if (i == 3) {
                    removeFragment(subway_fragment_line_3);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_3 = new Subway_fragment_line_3();
                    setFragment(subway_fragment_line_3);
                    transaction.addToBackStack(null);
                    i = 3;
                }
                break;
            case 3:
                if (i == 4) {
                    removeFragment(subway_fragment_line_4);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_4 = new Subway_fragment_line_4();
                    setFragment(subway_fragment_line_4);
                    transaction.addToBackStack(null);
                    i = 4;
                }
                break;
            case 4:
                if (i == 5) {
                    removeFragment(subway_fragment_line_5);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_5 = new Subway_fragment_line_5();
                    setFragment(subway_fragment_line_5);
                    transaction.addToBackStack(null);
                    i = 5;
                }
                break;
            case 5:
                if (i == 6) {
                    removeFragment(subway_fragment_line_6);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_6 = new Subway_fragment_line_6();
                    setFragment(subway_fragment_line_6);
                    transaction.addToBackStack(null);
                    i = 6;
                }
                break;
            case 6:
                if (i == 7) {
                    removeFragment(subway_fragment_line_7);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_7 = new Subway_fragment_line_7();
                    setFragment(subway_fragment_line_7);
                    transaction.addToBackStack(null);
                    i = 7;
                }
                break;
            case 7:
                if (i == 8) {
                    removeFragment(subway_fragment_line_8);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_8 = new Subway_fragment_line_8();

                    setFragment(subway_fragment_line_8);
                    transaction.addToBackStack(null);
                    i = 8;
                }
                break;
            case 8:
                if (i == 9) {
                    removeFragment(subway_fragment_line_9);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_9 = new Subway_fragment_line_9();
                    setFragment(subway_fragment_line_9);
                    transaction.addToBackStack(null);
                    i = 9;
                }
                break;
            case 9:
                //분당
                if (i == 10) {
                    removeFragment(subway_fragment_line_bundang);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_bundang = new Subway_fragment_line_bundang();
                    setFragment(subway_fragment_line_bundang);
                    transaction.addToBackStack(null);
                    i = 10;
                }
                break;

            case 10:
                //김포도시
                if (i == 11) {
                    removeFragment(subway_fragment_line_gimpodosi);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_gimpodosi = new Subway_fragment_line_gimpodosi();
                    setFragment(subway_fragment_line_gimpodosi);
                    transaction.addToBackStack(null);
                    i = 11;
                }



                break;
            case 11:
                //공항철도
                if (i == 12) {
                    removeFragment(subway_fragment_line_gonghang);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_gonghang = new Subway_fragment_line_gonghang();
                    setFragment(subway_fragment_line_gonghang);
                    transaction.addToBackStack(null);
                    i = 12;
                }
                break;
            case 12:
                //경춘선
                if (i == 13) {
                    removeFragment(subway_fragment_line_gyeongchun);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_gyeongchun = new Subway_fragment_line_gyeongchun();
                    setFragment(subway_fragment_line_gyeongchun);
                    transaction.addToBackStack(null);
                    i = 13;
                }
                break;

            case 13:
                //경강
                if (i == 14) {
                    removeFragment(subway_fragment_line_gyeonggang);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_gyeonggang = new Subway_fragment_line_gyeonggang();
                    setFragment(subway_fragment_line_gyeonggang);
                    transaction.addToBackStack(null);
                    i = 14;
                }
                break;

            case 14:
                //경의중앙
                if (i == 15) {
                    removeFragment(subway_fragment_line_gyeongui);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_gyeongui = new Subway_fragment_line_gyeongui();
                    setFragment(subway_fragment_line_gyeongui);
                    transaction.addToBackStack(null);
                    i = 15;
                }
                break;

            case 15:
                //인천1호선
                if (i == 16) {
                    removeFragment(subway_fragment_line_incheon1hoseon);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_incheon1hoseon = new Subway_fragment_line_incheon1hoseon();
                    setFragment(subway_fragment_line_incheon1hoseon);
                    transaction.addToBackStack(null);
                    i = 16;
                }
                break;

            case 16:
                //인천2호선
                if (i == 17) {
                    removeFragment(subway_fragment_line_incheon2hoseon);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_incheon2hoseon = new Subway_fragment_line_incheon2hoseon();
                    setFragment(subway_fragment_line_incheon2hoseon);
                    transaction.addToBackStack(null);
                    i = 17;
                }
                break;

            case 17:
                //서해
                if (i == 18) {
                    removeFragment(subway_fragment_line_seohae);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_seohae = new Subway_fragment_line_seohae();
                    setFragment(subway_fragment_line_seohae);
                    transaction.addToBackStack(null);
                    i = 18;
                }
                break;

            case 18:
                //신분당
                if (i == 19) {
                    removeFragment(subway_fragment_line_sinbundang);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_sinbundang = new Subway_fragment_line_sinbundang();
                    setFragment(subway_fragment_line_sinbundang);
                    transaction.addToBackStack(null);
                    i = 19;
                }
                break;
            case 19:
                //의정부
                if (i == 20) {
                    removeFragment(subway_fragment_line_uijeongbugyeong);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_uijeongbugyeong = new Subway_fragment_line_uijeongbugyeong();
                    setFragment(subway_fragment_line_uijeongbugyeong);
                    transaction.addToBackStack(null);
                    i = 20;
                }
                break;

            case 20:
                //우이신설
                if (i == 21) {
                    removeFragment(subway_fragment_line_uisinseolgyeong);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_uisinseolgyeong = new Subway_fragment_line_uisinseolgyeong();
                    setFragment(subway_fragment_line_uisinseolgyeong);
                    transaction.addToBackStack(null);
                    i = 21;
                }
                break;

            case 21:
                //용인
                if (i == 22) {
                    removeFragment(subway_fragment_line_yongingyeong);
                    setFragment(subway_fragment);
                    i = 0;
                } else {
                    subway_fragment_line_yongingyeong = new Subway_fragment_line_yongingyeong();
                    setFragment(subway_fragment_line_yongingyeong);
                    transaction.addToBackStack(null);
                    i = 22;
                }
                break;






        }

    }
}


