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

public class Subway_fragment_line_uijeongbugyeong extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    private JSONObject obj;
    private JSONArray jsonArray;

    String[] subway_line_uijeongbugyeong = new String[]{"동오","발곡","범골","새말","송산","어룡","의정부시청","의정부중앙","탑석","효자","흥선"
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
                if(obj.getString("subway_line").equals("의정부경전철")){
                    Log.i("subway_Tb",obj.getString("subway_name"));
                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_uijeongbugyeong),obj.getString("subway_name"));
                }
            }
            for(int j=0; j<subway_line_uijeongbugyeong.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_uijeongbugyeong),subway_line_uijeongbugyeong[j]);
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
                if(obj.getString("subway_line").equals("의정부경전철")){


                    Log.i("subway_code",""+obj.getInt("subway_code"));
                    if (obj.getInt("subway_code")%58 == position){
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