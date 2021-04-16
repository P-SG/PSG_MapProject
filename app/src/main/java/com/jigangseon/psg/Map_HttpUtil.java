package com.jigangseon.psg;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Map_HttpUtil extends AsyncTask<String, Void, JSONArray> {
    @Override
    public JSONArray doInBackground(String... params) {
        JSONArray memberJsonArr = null; // Json 배열 객체 생성
        try {
            //HttpURLConnection을 이용해 url에 연결하기 위한 설정
            String url = "http://15.164.67.3:8080/seonrae/selectMarkerInfo";
            URL obj = new URL(url); // URL 객체 생성
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); // 커넥션 객체 생성
            // openConnection 메소드는 url객체를 다른 클래스의 객체로 변환시켜주는 메소드

            //커넥션에 각종 정보 설정
            conn.setReadTimeout(15000); // Read시 연결 시간
            conn.setConnectTimeout(15000); // 서버 접속시 연결 시간
            conn.setRequestMethod("POST"); // 요청 방식 선택 (POST)
            conn.setDoInput(true); // url 접속을 할때에 입력용으로 사용할때에 true, 디폴트 true
            conn.setDoOutput(true); // url 접속을 할때에 출력용으로 사용할때에 true, 디폴트 true
            conn.setRequestProperty("Content-Type", "application/json"); // setRequestProperty(key, value); 헤더값을 설정하거나 파라미터로 값을 보낼때 사용

            //실제 서버로 Request요청 (응답 http코드를 가져옴, 200은 성공)
            int responseCode = conn.getResponseCode();

            ByteArrayOutputStream baos = null;
            InputStream is = null;
            String responseStr = null;

            //응답이 성공적으로 완료되었을 때
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                responseStr = new String(byteData);

                JSONObject responseJSON = new JSONObject(responseStr);
                //json데이터가 하나의 값일 때
                String result = (String) responseJSON.get("result");
                //json데이터가 Map같은 형식일 때
                memberJsonArr =  responseJSON.getJSONArray("storeData");

                //Log.i("info", "DATA response = " + responseStr);

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

        return memberJsonArr; // Json 배열 객체를 리턴

    }
}

