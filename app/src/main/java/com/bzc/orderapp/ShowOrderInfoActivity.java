package com.bzc.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bzc.orderapp.Serializable.SerializableHashMap;
import com.bzc.orderapp.adapter.StatementAdapter;
import com.bzc.orderapp.bean.FoodInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowOrderInfoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<FoodInfoBean> mCarDataList;
    private HashMap<String,Integer> mCountList;
    private TextView mCountMonery;
    private double mNumTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_info);
        initData();
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.orderlist);
        mCountMonery = findViewById(R.id.ordermoey);
        mCountMonery.setText("总价：" + mNumTotal);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StatementAdapter adapter = new StatementAdapter(mCarDataList, this, mCountList);
        mRecyclerView.setAdapter(adapter);
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