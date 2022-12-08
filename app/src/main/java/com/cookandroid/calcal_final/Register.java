package com.cookandroid.calcal_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText edt_ID_reg, edt_PASSWORD_reg;
    Button btn_Register_reg;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            //이미 로그인 되었다면 이 액티비티를 종료함
//            finish();
//            //그리고 profile 액티비티를 연다.
//            startActivity(new Intent(getApplicationContext(), UserFragment.class)); //추가해 줄 ProfileActivity
//        }
        //initializing views
        edt_ID_reg = findViewById(R.id.edt_ID_reg);
        edt_PASSWORD_reg = findViewById(R.id.edt_PASSWORD_reg);
        btn_Register_reg = findViewById(R.id.btn_Register_reg);
        progressDialog = new ProgressDialog(this);
        //EditText 선 하얗게 만듬
        edt_ID_reg.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        edt_PASSWORD_reg.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        //button click event
        btn_Register_reg.setOnClickListener(this);
//        textviewSingin.setOnClickListener(this);
    }

    //Firebse creating a new user
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = edt_ID_reg.getText().toString().trim();
        String password = edt_PASSWORD_reg.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //에러발생시
//                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(Register.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    //button click event
    @Override
    public void onClick(View view) {
        if(view == btn_Register_reg) {
            registerUser();
        }
    }
}