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

public class Subway_fragment_line_gyeongui extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    private JSONObject obj;
    private JSONArray jsonArray;

    String[] subway_line_gyeongui = new String[]{"구리","국수","금룽","금촌","능곡","덕소","도농","도심","문산","백마","서강대","서빙고","수색","신원","아신","야당","양수","양원","양정","양평","오빈","용문","운길산","운정","원덕","월롱","응봉","일산","임진강","탄현","파주","팔당","풍산","한남","행신","화전"
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
                if(obj.getString("subway_line").equals("경의중앙선")){
                    Log.i("subway_Tb",obj.getString("subway_name"));
                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_gyeongui),obj.getString("subway_name"));
                }
            }
            for(int j=0; j<subway_line_gyeongui.length;j++){
                adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.subway_line_gyeongui),subway_line_gyeongui[j]);
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
                if(obj.getString("subway_line").equals("경의중앙선")){


                    Log.i("subway_code",""+obj.getInt("subway_code"));
                    if (obj.getInt("subway_code")%31 == position){
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