package com.jigangseon.psg.chat;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinuxTask extends AsyncTask<String, Void, JSONArray> {
    @Override
    public JSONArray doInBackground(String... params) {
        JSONArray memberJsonArr = null;
        try {
            //HttpURLConnection을 이용해 url에 연결하기 위한 설정
//            String url = "http://15.164.67.3:8080/seonrae/test3";
            String url = "http://15.164.67.3:8080/seonrae/selectChatUser";
//            String url = "http://15.164.67.3:8080/seonrae/android5";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            //커넥션에 각종 정보 설정
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            //응답 http코드를 가져옴
            int responseCode = conn.getResponseCode();

            ByteArrayOutputStream baos = null;
            InputStream is = null;
            String responseStr = null;

            //응답이 성공적으로 완료되었을 때
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.i("if", "enter");

                is = conn.getInputStream();
                Log.i("is", "conn.getInputStream()");
                if (is == null){
                    Log.i("is == null", "true");
                }

                baos = new ByteArrayOutputStream();
                Log.i("baos", "new ByteArrayOutputStream()");
                if (baos == null){
                    Log.i("baos == null", "true");
                }

                byte[] byteBuffer = new byte[1024];
                Log.v("byteBuffer.length", byteBuffer.length + "");
                byte[] byteData = null;
                int nLength = 0;
//                nLength = is.read(byteBuffer, 0, byteBuffer.length);
//                Log.v("nLength", nLength + "");

                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
//                while (nLength != -1) {
                    baos.write(byteBuffer, 0, nLength);
                    Log.i("while", "loop");
                }

                //Log.v("byteData", byteData + "");
                byteData = baos.toByteArray();
                //Log.v("byteData = baos.toByteArray", byteData + "");
                Log.v("byteData.length", byteData.length + "");
                for (int i = 0 ; i < byteData.length ; i++) {
                    //Log.v("byteData", byteData[i] + "");
                }
                //Log.v("end for byteData", byteData + "");
                responseStr = new String(byteData);

                JSONObject responseJSON = new JSONObject(responseStr);
                //json데이터가 하나의 값일 때
                String result = (String) responseJSON.get("result");
                Log.v("result", result + "");
                //json데이터가 Map같은 형식일 때
//                memberJsonArr = responseJSON.getJSONArray("memberData");
                memberJsonArr = responseJSON.getJSONArray("chatUserData");

                Log.i("info", "DATA response = " + responseStr);

            } else {
                is = conn.getErrorStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                responseStr = new String(byteData);
                Log.i("info", "DATA response error msg = " + responseStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("errorInfo", "error occured!" + e.getMessage());
        }

        return memberJsonArr;
    }
}