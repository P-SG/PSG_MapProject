package com.jigangseon.psg.chat;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// DB 연결을 위한 서버와 HTTP 통신
public class ChatTask extends AsyncTask<String, Void, String> {
    //    public static String ip ="localhost:8082"; //자신의 IP번호
    String sendMsg, receiveMsg;
    String serverip = "http://172.18.160.1:9099/androidServer/connection.jsp"; // 연결할 jsp주소

    ChatTask(String sendmsg){
        this.sendMsg = sendmsg;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
//            MessageItem messageItem = new MessageItem();
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            if(sendMsg.equals("vision_write")){
                sendMsg = "nickname_write="+strings[0]+"&msg_write="+strings[1]+"&type="+strings[2];
            }

            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // 서버로부터 전달받은 값
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                    Log.v("서버로부터 전달받은 값", str+"");
                }
                receiveMsg = buffer.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;  // 서버로부터 전달받은 값 반환
    }
}