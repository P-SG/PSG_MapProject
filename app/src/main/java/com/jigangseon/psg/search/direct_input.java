package com.jigangseon.psg.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jigangseon.psg.MapActivity;
import com.jigangseon.psg.Map_HttpUtil;
import com.jigangseon.psg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class direct_input extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private direct_input_SearchAdapter direct_input_searchAdapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private JSONObject obj;
    private TextView direct_input_add_button;
    private TextView direct_input_add_text;
    private ImageView direct_input_add_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_input_search);

        editSearch = (EditText) findViewById(R.id.direct_input_edit_text);
        listView = (ListView) findViewById(R.id.direct_input_list_view);
        direct_input_add_button = (TextView)findViewById(R.id.direct_input_add_button);
        direct_input_add_text = (TextView)findViewById(R.id.direct_input_add_text);
        direct_input_add_icon = (ImageView)findViewById(R.id.direct_input_add_icon);
        // 리스트를 생성한다.
        list = new ArrayList<String>();

        listView.setVisibility(View.GONE);
        direct_input_add_text.setVisibility(View.GONE);
        direct_input_add_button.setVisibility(View.GONE);
        direct_input_add_icon.setVisibility(View.GONE);

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        direct_input_searchAdapter = new direct_input_SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(direct_input_searchAdapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });



    }

    // 검색을 수행하는 메소드
    public void search(String charText) {
        listView = (ListView) findViewById(R.id.direct_input_list_view);

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
           listView.setVisibility(View.GONE);
            direct_input_add_text.setVisibility(View.GONE);
            direct_input_add_button.setVisibility(View.GONE);
            direct_input_add_icon.setVisibility(View.GONE);

        }
        // 문자 입력을 할때..
        else
        {
            listView.setVisibility(View.VISIBLE);
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
                else{
                    direct_input_add_button.setVisibility(View.VISIBLE);
                    direct_input_add_text.setVisibility(View.VISIBLE);
                    direct_input_add_icon.setVisibility(View.VISIBLE);
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        direct_input_searchAdapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
//        list.add("호떡");
//        list.add("붕어빵");
//        list.add("전");
//        list.add("계란빵");
//        list.add("떡볶이");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> map = new HashMap<>();

            String json = objectMapper.writeValueAsString(map);

            Search_HttpUtil util = new Search_HttpUtil();
            util.execute(json);

            JSONArray jsonArray = util.get();
            for (int i=0; i<jsonArray.length();i++){
                obj= jsonArray.getJSONObject(i);
                Log.e("메뉴 카테고리",""+obj.getString("subway_name"));
                list.add(obj.getString("subway_name"));

            }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String item = list.get(position);
                Log.e("item",""+item);
                JSONArray jsonArray = util.get();

            for(int i=0;i<jsonArray.length(); i++){

                    obj = jsonArray.getJSONObject(i);
                    String check_item = obj.getString("subway_name");
//                    Log.e("check_itme",check_item);
                    if(item.equals(check_item)){
                        Intent intent =new Intent(getApplicationContext(),MapActivity.class);
                        intent.putExtra("subway_latitude",Double.parseDouble(obj.getString("subway_latitude")));
                        intent.putExtra("subway_longitude",Double.parseDouble(obj.getString("subway_longitude")));
                        //위도와 경도의 값을 인텐트에 넣어줌
                        Log.i("알려줘",obj.getString("subway_latitude"));
                        //맵 액티비티로 이동
                        startActivity(intent);
                    }
            }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }



            }
        });

        }
        catch (ExecutionException | InterruptedException | JsonProcessingException | JSONException e){

        }
    }
}