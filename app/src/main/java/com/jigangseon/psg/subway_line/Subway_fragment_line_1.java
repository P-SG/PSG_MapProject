package com.jigangseon.psg.subway_line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jigangseon.psg.MapActivity;
import com.jigangseon.psg.R;
import com.jigangseon.psg.search.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subway_fragment_line_1 extends ListFragment {
    private final static String TAG = "ListPlayersFragment";
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    public boolean test = false;
    String[] subway_line_1 = new String[]{ "관악", "광명", "광운대", "구로", "구일", "군포", "금정", "금천구청", "남영", "노량진", "녹양", "녹천", "당정", "대방", "덕계", "덕정", "도봉", "도봉산", "도원", "도화", "독산", "동대문", "동두천", "동두천중앙", "동묘앞", "동암", "동인천", "두정", "망월사", "명학", "방학", "배방", "백운", "병점", "보산", "봉명", "부개", "부천", "부평", "서동탄", "서울역", "서정리", "석계", "석수", "성균관대", "성환", "세류", "세마", "소사", "소요산", "송내", "송탄", "수원", "시청", "신길", "신도림", "신설동", "신이문", "신창", "쌍용", "아산", "안양", "양주", "역곡", "영등포", "오류동", "오산", "오산대", "온수", "온양온천", "외대앞", "용산", "월계", "의왕", "의정부", "인천", "제기동", "제물포", "종각", "종로3가", "종로5가", "주안", "중동", "지제", "지행", "직산", "진위", "창동", "천안", "청량리", "평택", "화서", "회기", "회룡"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> map = new HashMap<>();

            String json = objectMapper.writeValueAsString(map);

            Search_HttpUtil util = new Search_HttpUtil();


            util.execute(json);

            JSONArray jsonArray = util.get();
            for(int i= 0; i<jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("subway_line").equals("1호선")){
                    Log.i("subway_Tb",obj.getString("subway_name"));
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_1),obj.getString("subway_name"));
                }
            }

            for(int j=0; j<subway_line_1.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_1),subway_line_1[j]);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*//첫 번째 아이템 추가
        for (int i=0; i< subway_line_1.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_1),subway_line_1[i]);
        }
        */
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> map = new HashMap<>();

            String json = objectMapper.writeValueAsString(map);

            Search_HttpUtil util = new Search_HttpUtil();


            util.execute(json);

            JSONArray jsonArray = util.get();

                JSONObject obj = jsonArray.getJSONObject(position);
                if(obj.getString("subway_line").equals("1호선")){
                    switch (position){
                        case 0:
                        case 1:
                        case 2:
                            //눌린 값을 받아옴
                            obj = jsonArray.getJSONObject(position);
                            // 인텐트 설정 맵 액티비티로 설정 해놓음
                            Intent intent =new Intent(getActivity(),MapActivity.class);
                            intent.putExtra("subway_latitude",Double.parseDouble(obj.getString("subway_latitude")));
                            intent.putExtra("subway_longitude",Double.parseDouble(obj.getString("subway_longitude")));
                            //위도와 경도의 값을 인텐트에 넣어줌
                            Log.i("알려줘",obj.getString("subway_latitude"));
                            //맵 액티비티로 이동
                            startActivity(intent);
                            break;
                    }
            }
                else{
                    Toast.makeText(getContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
