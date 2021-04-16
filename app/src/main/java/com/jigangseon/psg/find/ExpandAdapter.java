package com.jigangseon.psg.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jigangseon.psg.R;

import java.util.ArrayList;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int childLayout = 0;
    private ArrayList<myGroup> DataList;
    private ImageButton imageButton = null;
    private LayoutInflater myinf = null;
    private int i=0;

    public ExpandAdapter(Context context,int groupLay,int childLay,ArrayList<myGroup> DataList){

        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.childLayout = childLay;
        this.context = context;
        this.myinf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return DataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public myGroup getGroup(int groupPosition) {
        return DataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DataList.get(groupPosition).child.get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }




    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView)convertView.findViewById(R.id.groupName);
        groupName.setText(DataList.get(groupPosition).groupName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myinf.inflate(this.childLayout, parent, false);
        }




        TextView childName = (TextView)convertView.findViewById(R.id.childName);
        childName.setText(DataList.get(groupPosition).child.get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {


        return true;
    }
}
