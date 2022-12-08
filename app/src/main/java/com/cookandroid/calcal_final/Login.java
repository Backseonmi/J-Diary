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

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText edt_ID, edt_PASSWORD;
    private Button btn_LOGIN, btn_Register;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            //이미 로그인 되었다면 이 액티비티를 종료함
//            finish();
//            //그리고 profile 액티비티를 연다.
//            startActivity(new Intent(getApplicationContext(), UserFragment.class)); //추가해 줄 ProfileActivity
//        }
        //initializing views
        edt_ID = findViewById(R.id.edt_ID);
        edt_PASSWORD = findViewById(R.id.edt_PASSWORD);
        btn_LOGIN = findViewById(R.id.btn_LOGIN);
        btn_Register = findViewById(R.id.btn_Register);
        progressDialog = new ProgressDialog(this);
        //EditText 선 하얗게 만듬
        edt_ID.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        edt_PASSWORD.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        //button click event
        btn_LOGIN.setOnClickListener(this);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Register.class);
                startActivity(in);
            }
        });
    }

    //firebase userLogin method
    private void userLogin(){
        String email = edt_ID.getText().toString().trim();
        String password = edt_PASSWORD.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "ID을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패(id, pw확인)", Toast.LENGTH_SHORT).show();
//                            textviewMessage.setText("로그인 실패 유형\n - password가 맞지 않습니다.\n -서버에러");
                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {
        if(view == btn_LOGIN) {
            userLogin();
        }
//        if(view == textviewSingin) {
//            finish();
//            startActivity(new Intent(this, MainActivity.class));
//        }
//        if(view == textviewFindPassword) {
//            finish();
//            startActivity(new Intent(this, FindActivity.class));
//        }
    }
}
//        edt_ID = findViewById(R.id.edt_ID);
//        edt_PASSWORD = findViewById(R.id.edt_PASSWORD);
//        btn_LOGIN = findViewById(R.id.btn_LOGIN);
//        btn_Register = findViewById(R.id.btn_Register);
////        myHelper = new MyDBHelper(this);
//        btn_Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(Login1.this, RegisterActivity.class);
//                startActivity(in);
//            }
//        });
//        btn_LOGIN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userID = edt_ID.getText().toString().trim();
//                String userPass = edt_PASSWORD.getText().toString().trim();
//                sqlDB = myHelper.getReadableDatabase();
//                Cursor cursor;
//                cursor = sqlDB.rawQuery("SELECT userID, userPass FROM user where userID = '" + userID + "' " + "and userPass = '" + userPass + "';", null);
//                if (cursor.moveToFirst()) {
//
//                    Intent in = new Intent(Login1.this, MainActivity.class);
//                    in.putExtra("id", edt_ID.getText().toString());
//                    startActivity(in);
//                    Toast.makeText(getApplicationContext(), "로그인 확인", Toast.LENGTH_SHORT).show();
//                    finish();
//
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "로그인 실패(id, pw확인)", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//    }
//}