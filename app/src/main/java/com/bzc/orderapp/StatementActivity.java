package com.bzc.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.TextView;
import android.widget.Toast;

import com.bzc.orderapp.Serializable.SerializableHashMap;
import com.bzc.orderapp.adapter.StatementAdapter;
import com.bzc.orderapp.bean.FoodInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StatementActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<FoodInfoBean> mCarDataList;
    private HashMap<String,Integer> mCountList;
    private TextView mCountMonery;
    private TextView mTime;
    private double mNumTotal;
    private int mShowTime = 60;
    private TextView mPlayBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        initData();
        initView();
        showTime();
    }

    private void showTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mShowTime--;
                if(mShowTime == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StatementActivity.this, "支付时间超时，请您重新支付", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }
                mTime.setText("请你在" + mShowTime + "S内完成支付");
            }
        }, 1000, 1000);


    }

    private void initView() {
        mRecyclerView = findViewById(R.id.paymentlist);
        mCountMonery = findViewById(R.id.countMonery);
        mTime = findViewById(R.id.time);
        mCountMonery.setText("总价：" + mNumTotal);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StatementAdapter adapter = new StatementAdapter(mCarDataList, this, mCountList);
        mRecyclerView.setAdapter(adapter);

        mPlayBtn = findViewById(R.id.playBtn);
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SerializableHashMap serializableHashMap = new SerializableHashMap();
                serializableHashMap.setMap(mCountList);
                Intent intent = new Intent(StatementActivity.this, ShowOrderInfoActivity.class);
                intent.putExtra("carList", (Serializable) mCarDataList);
                intent.putExtra("countList", serializableHashMap);
                intent.putExtra("countMonery", mNumTotal);

                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {

        mCarDataList = new ArrayList<>();
        mCountList = new HashMap<>();

        Intent intent = this.getIntent();
        mCarDataList  = (List<FoodInfoBean>)intent.getSerializableExtra("carList");
        mNumTotal =  intent.getDoubleExtra("countMonery", 0);
        mCountList = ((SerializableHashMap)intent.getSerializableExtra("countList")).getMap();
    }
}