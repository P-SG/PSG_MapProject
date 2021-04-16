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

public class Subway_fragment_line_5 extends ListFragment {
    private final static String TAG = "ListPlayersFragment";
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    public boolean test = false;
    private JSONObject obj;
    private JSONArray jsonArray;

    String[] subway_line_5 = new String[]{"강동","개롱","개화산","거여","고덕","공덕","광나루","광화문","군자","굽은다리","길동","김포공항","까치산","답십리","동대문역사문화공원","둔촌동","마곡","마장","마천","마포","명일","목동","미사","발산","방이","방화","상일동","서대문","송정","신금호","신길","신정","아차산","애오개","양평","여의나루","여의도","영등포구청","영등포시장","오금","오목교","올림픽공원","왕십리","우장산","을지로4가","장한평","종로3가","천호","청구","충정로","하남풍산","행당","화곡"
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
            for(int i= 0; i<jsonArray.length(); i++){
                 obj = jsonArray.getJSONObject(i);
                if(obj.getString("subway_line").equals("5호선")){
                    Log.i("subway_Tb",obj.getString("subway_name"));
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_5),obj.getString("subway_name"));
                }
            }
            for(int j=0; j<subway_line_5.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_5),subway_line_5[j]);
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

            JSONArray jsonArray = util.get();

            jsonArray = util.get();
            for(int i= 0; i<jsonArray.length(); i++){
                obj = jsonArray.getJSONObject(i);
                if(obj.getString("subway_line").equals("5호선")){


                    Log.i("subway_code",""+obj.getInt("subway_code"));
                    if (obj.getInt("subway_code")%13 == position){
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