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
import androidx.fragment.app.ListFragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jigangseon.psg.MapActivity;
import com.jigangseon.psg.R;
import com.jigangseon.psg.search.Search_HttpUtil;
import com.jigangseon.psg.search.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Subway_fragment_line_incheon1hoseon extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    private JSONObject obj;
    private JSONArray jsonArray;

    String[] subway_line_incheon1hoseon = new String[]{"계산","계양","국제업무지구","귤현","동막","동수","동춘","문학경기장","박촌","부평삼거리","부평시장","선학","센트럴파크","신연수","예술회관","원인재","인천대입구","인천시청","인천터미널","임학","작전","지식정보단지","캠퍼스타운","테크노파크"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                if(obj.getString("subway_line").equals("인천1호선")){
                    Log.i("subway_Tb",obj.getString("subway_name"));
                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_incheon1hoseon),obj.getString("subway_name"));
                }
            }
            for(int j=0; j<subway_line_incheon1hoseon.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_incheon1hoseon),subway_line_incheon1hoseon[j]);
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
                if(obj.getString("subway_line").equals("인천1호선")){


                    Log.i("subway_code",""+obj.getInt("subway_code"));
                    if (obj.getInt("subway_code")%61 == position){
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