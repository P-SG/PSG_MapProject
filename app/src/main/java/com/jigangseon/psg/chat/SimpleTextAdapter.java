package com.jigangseon.psg.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jigangseon.psg.R;

import java.util.ArrayList;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private ArrayList<String> mData = null;
//    private String nickName = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatListUser;  // 채팅 참가 유저 닉네임
        ImageView chatListProfile;  // 채팅 참가 유저 프로필

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            chatListUser = itemView.findViewById(R.id.chat_list_user) ;
            chatListProfile = itemView.findViewById(R.id.chat_list_profile) ;
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SimpleTextAdapter(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
//    @Override
//    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext() ;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
//
//        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
//        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;
//
//        return vh ;
//    }

    // 재활용 할 레이아웃 객체화
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.chat_user, parent, false) ;
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.chatListUser.setText(text) ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
//    @Override
//    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
//        String text = mData.get(position) ;
//        holder.chatListUser.setText(text) ;
//    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}