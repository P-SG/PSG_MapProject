package com.jigangseon.psg.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.jigangseon.psg.R;
import com.jigangseon.psg.subway_line.*;

import java.util.ArrayList;

public class Subway_fragment extends Fragment {


  int i=0;
  ViewPager viewPager;
  Button left_arrow, right_arrow;

  public Subway_fragment(){

  }




  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_subway, container, false);
    viewPager=(ViewPager)view.findViewById(R.id.viewPager);
    Subway_ViewPagerAdapter viewPagerAdapter = new Subway_ViewPagerAdapter(getContext());
    viewPager.setAdapter(viewPagerAdapter);
    left_arrow= view.findViewById(R.id.left_arrow);
    right_arrow= view.findViewById(R.id.right_arrow);

    left_arrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int currentPage = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentPage-1, true);
        if(currentPage ==0){
          currentPage = 3;
          viewPager.setCurrentItem(currentPage,false);

        }

      }
    });
    right_arrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int currentPage = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentPage+1, true);

        if(currentPage==2){
          currentPage = 0;
          viewPager.setCurrentItem(currentPage, false);
        }

      }
    });

    return view;
  }



}

