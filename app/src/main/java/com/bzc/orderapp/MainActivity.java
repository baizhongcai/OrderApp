package com.bzc.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzc.orderapp.Serializable.SerializableHashMap;
import com.bzc.orderapp.adapter.FoodCarInfoAdapter;
import com.bzc.orderapp.adapter.FoodInfoAdapter;
import com.bzc.orderapp.bean.FoodInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import animation.AnimationUtil;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mFoodListView;//食品列表
    private RecyclerView mFoodCarListView;//购物车列表
    private FrameLayout mFrameLayout;
    private LinearLayout mLinearLayout;

    private List<FoodInfoBean> mDataList;//食品数据
    private FoodInfoAdapter mAdapter;
    private TextView mCountMonery;//食品选择总数目
    private TextView mCarCountMonery;//食品选择总数目
    private ImageView mCloseCat;
    private ImageView mShopCat;

    private Button mAddcarBtn;

    private HashMap<String, Integer> mCountList;

    private List<FoodInfoBean> mCarDataList;//购物车数据

    private FoodCarInfoAdapter mCarAdapter;

    private double mNumTotal = 0;

    private TextView mMarkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mCountList = new HashMap<>();
        mCarDataList = new ArrayList<>();
        for (int index = 1; index <= 20; index++) {
            FoodInfoBean bean = new FoodInfoBean("衡阳海底捞", 200, R.mipmap.ic_food_img, index + "");
            mDataList.add(bean);
            mCountList.put(index + "", 0);
        }

    }

    private void initView() {
        mFoodListView = findViewById(R.id.foodlist);

        mFoodCarListView = findViewById(R.id.foodcarlist);
        mFrameLayout = findViewById(R.id.framlayout);
        mLinearLayout = findViewById(R.id.countInfoLayout);

        mCountMonery = findViewById(R.id.total);
        mCarCountMonery = findViewById(R.id.carTotal);
        mAdapter = new FoodInfoAdapter(mDataList, this, mCountList);

        mAddcarBtn = findViewById(R.id.addcar);

        mCarAdapter = new FoodCarInfoAdapter(mCarDataList, this, mCountList);

        mFoodListView.setLayoutManager(new LinearLayoutManager(this));
        mFoodCarListView.setLayoutManager(new LinearLayoutManager(this));

        mMarkTextView = findViewById(R.id.mark);

        mAddcarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                  序列化HasMap
                 */
                if (mCarDataList.size() == 0) {
                    Toast.makeText(MainActivity.this, "请先选择菜品", Toast.LENGTH_LONG).show();
                } else {
                    SerializableHashMap serializableHashMap = new SerializableHashMap();
                    serializableHashMap.setMap(mCountList);
                    Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                    intent.putExtra("carList", (Serializable) mCarDataList);
                    intent.putExtra("countList", serializableHashMap);
                    intent.putExtra("countMonery", mNumTotal);

                    startActivity(intent);
                }
            }
        });


        mAdapter.setNumberCallback(new FoodInfoAdapter.NumberCallback() {
            @Override
            public void numberaddLoad(int number, int price, FoodInfoBean bean, int count) {

                mNumTotal = mNumTotal + (price * number);
                mCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                mCarCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                if (mCarDataList.size() != 0) {
                    if (!getIsEqual(bean)) {
                        mCarDataList.add(bean);
                    }
                } else {
                    mCarDataList.add(bean);
                }
                mCountList.put(bean.getId(), count);
                mCarAdapter.setData(mCarDataList, mCountList);
                mMarkTextView.setText( mCarDataList.size() + "");
            }

            @Override
            public void numbersubLoad(int number, int price, FoodInfoBean bean, int count) {
                mNumTotal = mNumTotal - (price * number);
                mCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                mCarCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                if (mCarDataList.size() != 0) {
                    if (!getIsEqual(bean)) {
                        mCarDataList.add(bean);
                    }
                } else {
                    mCarDataList.add(bean);
                }
                mCountList.put(bean.getId(), count);
                mCarAdapter.setData(mCarDataList, mCountList);
                mMarkTextView.setText( mCarDataList.size() + "");
            }
        });

        mCarAdapter.setNumberCallback(new FoodCarInfoAdapter.NumberCallback() {
            @Override
            public void numberaddLoad(int number, int price, FoodInfoBean bean, int count) {
                mNumTotal = mNumTotal + (price * number);
                mCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                mCarCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                if (mCountList.size() > 0) {
                    mCountList.remove(bean.getId());
                    mCountList.put(bean.getId(), count);
                }
                mAdapter.setData(mCountList, mDataList);
                mMarkTextView.setText(mCarDataList.size() + "");
            }

            @Override
            public void numbersubLoad(int number, int price, FoodInfoBean bean, int count) {
                mNumTotal = mNumTotal - (price * number);
                mCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                mCarCountMonery.setText(Html.fromHtml("合计:" + getBlueText("" + mNumTotal)));
                if (mCountList.size() > 0) {
                    mCountList.remove(bean.getId());
                    mCountList.put(bean.getId(), count);
                }
                if (count == 0) {
                    delBeanAsList(bean);
                    mCarAdapter.setData(mCarDataList, mCountList);
                }

                if (mCarDataList.size() == 0) {
                    mFrameLayout.setAnimation(AnimationUtil.moveToViewBottom());
                    mFrameLayout.setVisibility(View.GONE);

                }
                mAdapter.setData(mCountList, mDataList);
                mMarkTextView.setText(mCarDataList.size() + "");
            }
        });
        mFoodListView.setAdapter(mAdapter);
        mFoodCarListView.setAdapter(mCarAdapter);

        mCloseCat = findViewById(R.id.closecat);
        mCloseCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFrameLayout.setAnimation(AnimationUtil.moveToViewBottom());
                mFrameLayout.setVisibility(View.GONE);
            }
        });

        mShopCat = findViewById(R.id.shopcat);
        mShopCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCarDataList.size() == 0) {
                    Toast.makeText(MainActivity.this, "购物车暂无菜品，请您选择菜品", Toast.LENGTH_LONG).show();
                } else {
                    mFrameLayout.setAnimation(AnimationUtil.moveToViewLocation());
                    mFrameLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 为字符串加红色 * * @param string * @return
     */
    private String getBlueText(String string) {
        return String.format("<font color=\"#ff5a5f\">%s</font>", string); // string 会替换 %s
    }

    /**
     * 判断存在相同的元素没？
     */
    private boolean getIsEqual(FoodInfoBean bean) {
        boolean status = false;
        for (int index = 0; index < mCarDataList.size(); index++) {
            if (bean.getId().equals(mCarDataList.get(index).getId())) {
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }


    /**
     * 购物车删除选择的数量为0的对象
     */

    private void delBeanAsList(FoodInfoBean bean) {
        for (int index = 0; index < mCarDataList.size(); index++) {
            if (bean.getId().equals(mCarDataList.get(index).getId())) {
                mCarDataList.remove(index);
                mCountList.remove(bean.getId());
                mCountList.put(bean.getId(), 0);
            }
        }
    }
}