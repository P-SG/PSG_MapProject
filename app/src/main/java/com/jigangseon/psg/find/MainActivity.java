package com.jigangseon.psg.find;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.jigangseon.psg.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button filter;
    GridView gridView;

    //이미지 배열 선언
    ArrayList<Bitmap> food_image = new ArrayList<Bitmap>();
    //텍스트 배열선언
    ArrayList<String> food_name = new ArrayList<String>();
    Activity act = this;

    //확장형 리스트뷰 선언
    private ExpandableListView ex_ListView;



    String[] foodList={"떡볶이", "호떡", "타코야끼", "붕어빵/잉어빵", "빵 종류", "전" ,"기타"};
    String[] sortList={"최근 순서", "오래된 순서", "사람 많은 순서", "사람 적은 순서", "친구가 있는 방"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        //드로우레이아웃 부분 ---------------------------------
        //필터 버튼
        filter = (Button) findViewById(R.id.filter);


        //필터 버튼 클릭시 함수
        filter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                //drawerLayout 객체 생성
                if(!drawer.isDrawerOpen(Gravity.RIGHT)){
                    //drawerLayout이 오픈이 오른쪽으로 되어있지 않을경우에만 실행
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
        //---------------------------------------------------


        //그리드뷰 부분 --------------------------------------

        Bitmap tteokbokki = BitmapFactory.decodeResource(getResources(),R.drawable.tteokbokki);
        Bitmap fishshaped = BitmapFactory.decodeResource(getResources(),R.drawable.fishshaped);
        Bitmap hodduk = BitmapFactory.decodeResource(getResources(),R.drawable.hodduk);
        Bitmap jun = BitmapFactory.decodeResource(getResources(),R.drawable.jun);

        food_image.add(tteokbokki);
        food_image.add(fishshaped);
        food_image.add(hodduk);
        food_image.add(jun);

        food_name.add("떡볶이 (1/3)");
        food_name.add("붕어빵/잉어빵 (2/3)");
        food_name.add("호떡 (1/2)");
        food_name.add("전 (1/4)");

        gridView = (GridView)findViewById(R.id.food_view);
        gridView.setAdapter(new gridAdapter());







        //------------------------------------





        //확장형 리스트뷰 부분---------------------------
        Display newDisplay = getWindowManager().getDefaultDisplay();
        //화면의 크기를 구하는 객체
        Point size = new Point();
        newDisplay.getSize(size);
        int width = size.x;

        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        ex_ListView = (ExpandableListView)findViewById(R.id.expandableListView);
        myGroup temp = new myGroup("즐겨찾기");
        temp.child.add("떡볶이");
        DataList.add(temp);
        temp = new myGroup("메뉴");
        temp.child.add("떡볶이");
        temp.child.add("호떡");
        temp.child.add("전");
        DataList.add(temp);
        temp = new myGroup("정렬");
        temp.child.add("최근 순서");
        temp.child.add("최근 순서");

        /*for(String count : sortList){
            temp.child.add(count);
        }*/
        DataList.add(temp);

        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,DataList);
        ex_ListView.setIndicatorBounds(width-50, width);//이 코드를 지우면 화살표 위치가 바뀐다.
        ex_ListView.setAdapter((ExpandableListAdapter) adapter);

        //-------------------------------------------------



    }


    public class gridAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public gridAdapter(){
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return food_image.size();
        }

        @Override
        public Object getItem(int position) {
            return food_image.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.food_image_row, parent,false);

            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.food_image);
            TextView textView = (TextView) convertView.findViewById(R.id.food_name);
            imageView.setImageBitmap(food_image.get(position));
            textView.setText(food_name.get(position));
            return convertView;
        }
    }
}