package com.jigangseon.psg.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;
import com.jigangseon.psg.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

// 채팅 액티비티
public class ChatActivity extends AppCompatActivity {
    // 서버 접속 여부를 판별하기 위한 변수
    boolean isConnect = false;
    EditText editMessage;   // 메시지 입력 EditText
    //    Button btn1;
    ImageButton enterMessageButton; // 메시지 전송 ImageButton
    LinearLayout container; // ScrollView에 추가되는 레이아웃
    LinearLayout messageBoxLayout;
    ScrollView scroll;  // 채팅이 표시되는 ScrollView
    ProgressDialog pro;
    // 어플 종료시 스레드 중지를 위해...
    boolean isRunning=false;
    // 서버와 연결되어있는 소켓 객체
    Socket member_socket;
    // 사용자 닉네임( 내 닉넴과 일치하면 내가보낸 말풍선으로 설정 아니면 반대설정)
    String myNickname;
    // 채팅 닉네임 변수
    String chatNickName;
    // 채팅 내용 변수
    String chatMsg;
    private DrawerLayout mDrawerLayout;

    ArrayList<String> chatList = new ArrayList<String>(); // 채팅 참가자 리스트
    RecyclerView recyclerView;  // 리사이클러뷰
    SimpleTextAdapter adapter;  // 리사이클러뷰 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        editMessage = findViewById(R.id.edit_chat);
        container=findViewById(R.id.container);
        scroll=findViewById(R.id.scroll);
        messageBoxLayout = findViewById(R.id.chat_box_layout);
        enterMessageButton = findViewById(R.id.enter_chat);

        // 소프트 키보드 설정
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
//        actionBar.setHomeAsUpIndicator(R.drawable.view_menu_icon); //뒤로가기 버튼 이미지 지정

        // 프로필 동그랗게
//        ImageView profileImage = findViewById(R.id.chat_toolbar_food_image);
//        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
//        profileImage.setClipToOutline(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        // 네비게이션 뷰 이벤트
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

//                int id = menuItem.getItemId();
//                String title = menuItem.getTitle().toString();

//                if(id == R.id.account){
//                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                } else if(id == R.id.setting){
//                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                } else if(id == R.id.logout){
//                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        }); // end setNavigationItemSelectedListener()

        // 소프트 키보드 식별 이벤트
        InputMethodManager controlManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        SoftKeyboard softKeyboard = new SoftKeyboard(mDrawerLayout, controlManager);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {

            // 키보드 숨겨짐
            @Override
            public void onSoftKeyboardHide() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // 원하는 동작
                        messageBoxLayout.setBackgroundResource(R.drawable.chat_box_style);
                    }
                });
            }

            // 키보드 보여짐
            @Override
            public void onSoftKeyboardShow() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // 원하는 동작
                        messageBoxLayout.setBackgroundResource(R.drawable.chat_box_edit_style);
                    }
                });
            }
        });

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
//        ArrayList<String> list = new ArrayList<>();
//        for (int i=0; i<100; i++) {
//            list.add(String.format("TEXT %d", i));
//        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
//        adapter = new SimpleTextAdapter(chatList);
//        recyclerView.setAdapter(adapter);

    }   // end onCreate()

    /* 툴바 메뉴 메소드 */
    // 툴바 메뉴 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
//                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            case R.id.chat_menu_more:{ // 오른쪽 상단 메뉴 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.END);

                // 채팅 접속자 설정
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    HashMap<String, String> map = new HashMap<>();

                    String json = objectMapper.writeValueAsString(map);

                    LinuxTask util =  new LinuxTask();
                    util.execute(json);

                    JSONArray jsonArray = util.get();

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Log.i("chatUserData", "["
                                + obj.getString("mem_nickname")
                                + "]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
                adapter = new SimpleTextAdapter(chatList);
                recyclerView.setAdapter(adapter);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 툴바에 메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu, menu);

        return true;
    }

    // 전송 버튼과 연결된 메소드
    public void btnMethod(View v) {
        if (isConnect == false) {   //접속전
            //사용자가 입력한 닉네임을 받는다.
            String nickName = editMessage.getText().toString();
            if (nickName.length() > 0 && nickName != null) {
                //서버에 접속한다.
                pro = ProgressDialog.show(this, null, "접속중입니다");
                // 접속 스레드 가동
                ConnectionThread thread = new ConnectionThread();
                thread.start();

            }
            // 닉네임이 입력되지않을경우 다이얼로그창 띄운다.
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("닉네임을 입력해주세요");
                builder.setPositiveButton("확인", null);
                builder.show();
            }
        } else {                  // 접속 후
            // 입력한 문자열을 가져온다.
            String msg = editMessage.getText().toString();

            // 송신 스레드 가동
            SendToServerThread thread = new SendToServerThread(member_socket,msg);
            thread.start();
        }
        // 소프트 키보드 표시 제어
        // 키보드 숨기기
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editMessage.getWindowToken(), 0);
    }

    // 서버접속 처리하는 스레드 클래스 - 안드로이드에서 네트워크 관련 동작은 항상
    // 메인스레드가 아닌 스레드에서 처리해야 한다.
    class ConnectionThread extends Thread {

        @Override
        public void run() {
            try {
                // 접속한다.
                final Socket socket = new Socket("15.164.67.3", 9898);
                member_socket=socket;
                // 미리 입력했던 닉네임을 서버로 전달한다.
                String nickName = editMessage.getText().toString();
                myNickname=nickName;     // 화자에 따라 말풍선을 바꿔주기위해
                // 스트림을 추출
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                // 닉네임을 송신한다.
                dos.writeUTF(nickName);
                // ProgressDialog 를 제거한다.

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();

                        editMessage.setText("");
                        editMessage.setHint("메세지 입력");

                        // 접속 상태를 true로 셋팅한다.
                        isConnect=true;
                        // 메세지 수신을 위한 스레드 가동
                        isRunning=true;
                        MessageThread thread=new MessageThread(socket);
                        thread.start();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MessageThread extends Thread {
        Socket socket;
        DataInputStream dis;

        public MessageThread(Socket socket) {
            try {
                this.socket = socket;
                InputStream is = socket.getInputStream();
                dis = new DataInputStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try{
                while (isRunning){
                    // 서버로부터 데이터를 수신받는다.
                    final String msg = dis.readUTF();
                    Log.v("final String msg = dis.readUTF();", msg + "");

                    // 닉네임 추출
                    String[] splitMsg = msg.split(",");
                    chatNickName = splitMsg[0];
                    chatMsg = splitMsg[1];
                    Log.v("chatNickName = splitMsg[0];", chatNickName + "");
                    Log.v("chatMsg = splitMsg[1];", chatMsg + "");

                    // 채팅 참가자가 처음 접속 시
                    if (!(chatList.contains(chatNickName))) {
                        chatList.add(chatNickName); // 채팅 참가자를 리스트에 추가
                    }

                    // 입장 후 채팅 참가자 로그
                    for (String s : chatList) {
                        Log.v("Enter chatList", s + "");
                    }

                    // 화면에 출력
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // 레이아웃 크기
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT ); //텍스트 뷰의 크기를 부모의 크기에 맞춘후

                            // 채팅 레이아웃 (상대방 프로필 사진 표시)
                            LinearLayout chat_layout = new LinearLayout(ChatActivity.this);
                            chat_layout.setOrientation(LinearLayout.HORIZONTAL);
                            chat_layout.setLayoutParams(layoutParams);

                            // 텍스트뷰의 객체를 생성
                            TextView tv=new TextView(ChatActivity.this);
                            tv.setTextColor(Color.BLACK);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                            if(chatMsg.equals("님이 입장했습니다")) {   // 입장 메시지일때 가운데 정렬
                                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;   // layout_gravity="center_horizontal"

                                // 입장 메시지
                                chatNickName += chatMsg;    // 닉네임에 환영 메시지 합치기
                                chatMsg = chatNickName; // 채팅 메시지 변수에 저장
                            }else if(chatMsg.equals("님이 퇴장했습니다")){  // 퇴장 메시지일때 가운데 정렬
                                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;   // layout_gravity="center_horizontal"

                                // 퇴장 메시지
                                chatNickName += chatMsg;    // 닉네임에 퇴장 메시지 합치기
                                chatMsg = chatNickName; // 채팅 메시지 변수에 저장

                                // 채팅 참가자를 리스트에서 제거
                                chatList.remove(chatList.indexOf(chatNickName));

                                // 퇴장 후 채팅 참가자 로그
                                for (String s : chatList) {
                                    Log.v("Exit chatList", s + "");
                                }

                            }else if(myNickname.equals(chatNickName)) {  // 내가 친 채팅일때 오른쪽 정렬
                                layoutParams.gravity = Gravity.RIGHT;   // layout_gravity="right"
                            }else{  // 상대방 채팅일때 왼쪽정렬
                                ImageView profileImage = new ImageView(ChatActivity.this);
                                profileImage.setImageResource(R.drawable.profile);
                                chat_layout.addView(profileImage);
                            }
                            tv.setLayoutParams(layoutParams);
                            tv.setText(chatMsg);
                            tv.setBackgroundResource(R.drawable.bubble);

                            // 채팅 레이아웃에 텍스트 뷰 추가
                            chat_layout.addView(tv);

                            // 컨테이너(부모 레이아웃)에 채팅 레이아웃 추가
                            container.addView(chat_layout);

                            // 제일 하단으로 스크롤 한다
                            scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 서버에 데이터를 전달하는 스레드
    class SendToServerThread extends Thread{
        Socket socket;
        String msg;
        DataOutputStream dos;

        public SendToServerThread(Socket socket, String msg){
            try{
                this.socket=socket;
                this.msg=msg;
                OutputStream os=socket.getOutputStream();
                dos=new DataOutputStream(os);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try{
                // 서버로 데이터를 보낸다.
                dos.writeUTF(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editMessage.setText("");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 채팅방 나가기
    public void exitChatRoom(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(member_socket != null) {
                member_socket.close();
            }
            isRunning = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}