package com.jigangseon.psg.subway_line;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import com.jigangseon.psg.R;
import com.jigangseon.psg.search.Subway_ListViewAdapter;

public class Subway_fragment_list_Click extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_1),"F");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
