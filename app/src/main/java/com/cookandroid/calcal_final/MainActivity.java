package com.cookandroid.calcal_final;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity {
    EditText edtDiary;
    Button btnWrite;
    CalendarView calendar;
    ImageView calender, user;
    String fileName; //파일이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = findViewById(R.id.calendar);
        edtDiary = findViewById(R.id.edtDiary);
        btnWrite = findViewById(R.id.btnWrite);
        calender = findViewById(R.id.calender);
        user = findViewById(R.id.user);

        //EditText 선 하얗게 만듬
        edtDiary.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        //bar 색 변경
        calender.setColorFilter(Color.parseColor("#D7BDF6"), PorterDuff.Mode.SRC_IN);
        user.setColorFilter(Color.parseColor("#6E7070"), PorterDuff.Mode.SRC_IN);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); //시스템 달력에서 년도 가져오기
        int month = cal.get(Calendar.MONTH); //시스템 달력에서 월(0부터 시작) 가져오기
        int day = cal.get(Calendar.DAY_OF_MONTH); //시스템 달력에서 일 가져오기
        fileName = year + "_" + (month + 1) + "_" + day + ".txt"; //파일이름 만들기 "년_월_일.txt"
        //오늘날짜의 일기가 있으면 읽어서 edtDiary에 setText()하기
        String str = readDiary(fileName); //일기 읽기 완성
        edtDiary.setText(str);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE); //MODE_PRIVATE : 파일 쓰기용으로 open
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName + "파일 저장됨", Toast.LENGTH_SHORT).show();
                } catch (Exception e){}
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int d) {
//            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                fileName = y + "_" + (m + 1) + "_" + d + ".txt";
                String str = readDiary(fileName); //일기 있으면 읽어오기
                edtDiary.setText(str);
            }
        });

        user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });
    }
    //파일 읽기 함수 따로 정의
    String readDiary(String fname){
        String diaryData = null; //일기 내용 저장하는 변수
        try {
            FileInputStream infs = openFileInput(fname); //년_월_일.txt 파일 읽기
            byte[] in = new byte[500];
            infs.read(in);
            diaryData = (new String(in)).trim(); //양끝에 공백제거함수 trim()
            btnWrite.setText("수정하기");
            infs.close();
        } catch (Exception e) {
            btnWrite.setText("새로저장");
        }
        return diaryData;
    }

}