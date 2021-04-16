package com.jigangseon.psg.subway_line;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jigangseon.psg.MapActivity;
import com.jigangseon.psg.R;
import com.jigangseon.psg.search.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Subway_fragment_line_2 extends ListFragment {
    private final static String TAG = "ListPlayersFragment";
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    public boolean test = false;
    private JSONObject obj;
    private JSONArray jsonArray;

    String[] subway_line_2 = new String[]{"교대","구로디지털단지","구의","까치산","낙성대","당산","대림","도림천","동대문역사문화공원","뚝섬","문래","방배","봉천","사당","삼성","상왕십리","서울대입구","서초","선릉","성수","시청","신답","신당","신대방","신도림","신림","신설동","신정네거리","신촌","아현","양천구청","역삼","영등포구청","왕십리","용답","용두","을지로3가","을지로4가","을지로입구","이대","잠실","잠실나루","잠실새내","종합운동장","충정로","한양대","합정","홍대입구"
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


            jsonArray = util.get();
            /*for(int j=0; j<jsonArray.length();j++){
                            obj = jsonArray.getJSONObject(j);
                            if(obj.getString("subway_line").equals("2호선")){
                                obj_line_2 = obj;
                                Log.v("obj.getString(\"subway_line\").equals(\"2호선\")",""+obj_line_2.get("subway_latitude"));

                            }
                        }*/
            //제이슨 어레이 만큼 돌림
            for(int i= 0; i<jsonArray.length(); i++){
                //제이슨 오브젝트에 i번째 제이슨 어레이의 값을 넣어줌
                obj = jsonArray.getJSONObject(i);
                //제이슨 오브젝트에 i번째 값의 subway_line 명이 2호선일경우에만 실행
                if(obj.getString("subway_line").equals("2호선")){
                    Log.i("subway_Tb",obj.getString("subway_code"));
                    //그 데이터들 리스트뷰에 아이템 추가
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_2),obj.getString("subway_name"));
                }
            }
            for(int j=0; j<subway_line_2.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_2),subway_line_2[j]);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
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
//            JSONArray jsonArray = util.get();
//            JSONObject obj = jsonArray.getJSONObject(position);
//            if(obj.getString("subway_line").equals("2호선")){

           /* for(int i=0; i<jsonArray.length();i++){

                obj = jsonArray.getJSONObject(i);
                if(obj.getInt("subway_code")%3 == position){
                    switch (position) {
                        case 1:
                        case 2:
                        case 3:
                            //눌린 값을 받아옴
                            // 인텐트 설정 맵 액티비티로 설정 해놓음
                            Intent intent = new Intent(getActivity(), MapActivity.class);
                            intent.putExtra("subway_latitude", Double.parseDouble(obj.getString("subway_latitude")));
                            intent.putExtra("subway_longitude", Double.parseDouble(obj.getString("subway_longitude")));
                            //위도와 경도의 값을 인텐트에 넣어줌
                            Log.i("알려줘", obj.getString("subway_latitude"));
                            //맵 액티비티로 이동
                            startActivity(intent);
                            break;
//                }
                    }
                }
            }*/

                    /*switch (position){
                        default:
                            //눌린 값을 받아옴
                            // 인텐트 설정 맵 액티비티로 설정 해놓음
                            Intent intent =new Intent(getActivity(), MapActivity.class);
                            intent.putExtra("subway_latitude",Double.parseDouble(obj.getString("subway_latitude")));
                            intent.putExtra("subway_longitude",Double.parseDouble(obj.getString("subway_longitude")));
                            //위도와 경도의 값을 인텐트에 넣어줌
                            Log.i("알려줘",obj.getString("subway_latitude"));
                            //맵 액티비티로 이동
                            startActivity(intent);
                            break;
                    }
                }*/
            jsonArray = util.get();
            for(int i= 0; i<jsonArray.length(); i++){
                obj = jsonArray.getJSONObject(i);
                if(obj.getString("subway_line").equals("2호선")){


                    Log.i("subway_code",""+obj.getInt("subway_code"));
                    if (obj.getInt("subway_code")%4 == position){
                        Intent intent =new Intent(getActivity(), MapActivity.class);
                        intent.putExtra("subway_latitude",Double.parseDouble(obj.getString("subway_latitude")));
                        intent.putExtra("subway_longitude",Double.parseDouble(obj.getString("subway_longitude")));
                        //위도와 경도의 값을 인텐트에 넣어줌
                        Log.i("알려줘",obj.getString("subway_latitude"));
                        //맵 액티비티로 이동
                        startActivity(intent);
//                        break;
                    }



                }

            }

            if(position >2)
                Toast.makeText(getContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();


        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
