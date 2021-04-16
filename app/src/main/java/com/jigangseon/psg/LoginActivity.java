package com.jigangseon.psg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class LoginActivity extends AppCompatActivity {


    //이메일 버튼
    Button embt;
    //회원가입 버튼
    Button sign_up_button;
    //카카오 버튼
    LoginButton kakao_button;
    //카카오버튼을 위한 페이크 이미지
    ImageView fakekakao;
    TextView no_login;

    private SessionCallback sessionCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionCallback = new SessionCallback();//SessionCallback 초기화
        //User객체를 구현해서 그 객체에 정보를 넣어두는 방법이 가장 좋긴 하지만
        //여기서는 일단 로그인 기능을 구현하는데 중점을 두고 있으므로 그냥 intent으로
        //넘겨주는 것 까지만 한다.
        //이걸 다 합치면 SessionCallback이 된다.
        //로그인 버튼을 눌렀을때 해야 할 일
        Session.getCurrentSession().addCallback(sessionCallback);// 현재 세션에 콜백 붙임
        /*Session.getCurrentSession().checkAndImplicitOpen();*/// 자동로그인인
        //checkAndImplicitOpen()이란 함수가 눈에 띄실 겁니다.
        // 이 함수는 현재 앱에 유효한 카카오 로그인 토큰이 있다면 바로 로그인 시켜주는 함수
        //즉 이전에 로그인 한 기록이 있다면, 다음 번 앱을 켯을 때 자동으로 로그인 시켜주는 것이다.
        //만약 저 함수를 주석처리하면 매번 로그인 버튼을 눌러줘야한다.
        //onCreate에서 현재 세션에 골백을 붙엿다면 onDestroy시에는 세션에서 콜백을 제거해야한다.
        //네이버, 구글 등의 다른 로그인 API를 같이 사용하는 경우, 이 콜백 제거를 안해주면 로그아웃 작업에서
        //문제가 생긴다. (*실제 경험함이래요.. 로그아웃부분에서 문제가 발생했는지 자동 로그인에서 문제가
        //발생했는지 정확히 기억은 안나지만 꽤 크리티컬한 오류가 발생한다)

       /* embt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmailLogin.class);
                startActivity(intent);
            }
        });
*/



    /*    fakekakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fake_kakao:
                        kakao_button.performClick();
                                break;
                }
            }
        });*/


        embt = (Button)findViewById(R.id.email_button);
        sign_up_button = findViewById(R.id.sign_up_button);
        kakao_button = findViewById(R.id.kakao_login_button);
        fakekakao = findViewById(R.id.fake_kakao);
        no_login = findViewById(R.id.no_login);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fake_kakao:
                        kakao_button.performClick();
                        break;
                    case R.id.email_button:
                        Intent intent = new Intent(getApplicationContext(), Email_login.class);
                        startActivity(intent);
                        break;
                    case R.id.sign_up_button:
                        Toast.makeText(LoginActivity.this,"회원가입버튼",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.no_login:
                        Toast.makeText(LoginActivity.this,"회원가입버튼 없이",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        fakekakao.setOnClickListener(onClickListener);
        embt.setOnClickListener(onClickListener);
        sign_up_button.setOnClickListener(onClickListener);
        no_login.setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            //카카오 로그인 액티비티에서 넘어온 경우일 때 실행
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
        //현재 액티비티 제거 시 콜백도 같이 제거
        //콜백 제거 부분이 이 코드
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            //로그인 버튼 클릭 시, 로그인 세션이 제대로 열렸을 경우 작동하는함수
            //onSessionOpenFailed는 그 반대이다. 참고로 인터넷이 아예 연결되어 있지 않아도,
            //onSessionOpenFailed가 작동한다. 반면 인터넷이 연결은 되어 있는데
            //불안정할 경우(와이파이 신호가 너무 약하거나 등등) onSessionOpened가 실행되죠
            //onSessionOpenFailed에서 해줄 것은 간단하다 통상적인 에러처리가 그것
            //여기서는 로그인 도중 오류가 발생했다는 토스트 하나를 띄워준다 필요하다면 앱 종료시키는 등의 동작도 할 수 있다.
            //onSessionOpened에서 할 것은 로그인 을 수행하고, 그 정보를 받아오는 것입니다. 또한 로그인 도중
            //오류가 발생하면 그 오류를 처리해줘야 한다 유저 정보를 받아오는 함수 이름은 me로,
            //MeV2ResponseCallback을 필요로 합니다.
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
/*
                    우선 onFailure는 로그인이 실패했을 때 호출되는 함수입니다.
                    어떤 오류인지를 나타내는 ErrorResult값을 받아오죠. 보통은 에러가 발생했다는 토스트 메세지를 띄웁니다.
                    다만, 다른 종류의 에러는 거의 발생하지 않지만, 인터넷 연결과 관련한 오류는 생각보다 자주 나타나죠.
                    따라서, 인터넷 연결이 불안정한 경우라면 따로 "인터넷 연결이 불안정합니다."하고 알려주는 게 좋습니다.
                    그래서, 여기서는 onFailure를 다음과 같이 구현했습니다
*/


                    int result = errorResult.getErrorCode();
                    /*onSessionClosed는 로그인 도중 세션이 비정상적인 이유로 닫혔을 때 작동하는 함수입니다*/
                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    //로그인에 성공하면 로그인 API로부터 MeV2Response라는 객체가 넘어온다.
                    //MeV2Response는 로그인한 유저의 정보를 담고 있는 아주 중요한 객체이다.

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", result.getNickname());
                    //로그인된 아이디의 이름을 갖고옴
                    intent.putExtra("profile", result.getProfileImagePath());
                    //로그인된 프로필을 갖고옴
                    startActivity(intent);
                    //MainActivity로 새로운 화면을 띄움
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            //로그인 세션이 정상적으로 열리지 않았을 때
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
