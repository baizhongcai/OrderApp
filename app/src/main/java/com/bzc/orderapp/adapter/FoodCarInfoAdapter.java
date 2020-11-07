package com.bzc.orderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bzc.orderapp.R;
import com.bzc.orderapp.bean.FoodInfoBean;

import java.util.List;
import java.util.Map;

public class FoodCarInfoAdapter extends RecyclerView.Adapter<FoodCarInfoAdapter.ViewHolder>{

    private List<FoodInfoBean> mDataList;
    private Context mContext;
    private NumberCallback mNumberCallback;
    private Map<String,Integer>  mCountList;


    public FoodCarInfoAdapter(List<FoodInfoBean> data, Context context, Map<String,Integer> countList){
        this.mDataList = data;
        this.mContext = context;
        this.mCountList = countList;
    }

    public void setData(List<FoodInfoBean> data, Map<String,Integer> countList){
        this.mDataList = data;
        this.mCountList = countList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_list_item,parent
                ,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddition(viewHolder);
            }
        });
        viewHolder.subtractBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onSubtraction(viewHolder);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodInfoBean bean = mDataList.get(position);
        holder.foodImage.setImageResource(bean.getImgId());
        holder.foodName.setText(bean.getName());
        holder.foodMonery.setText(bean.getMonery() + "");
        holder.number.setText(mCountList.get(bean.getId()) + "");
    }

    /** * 减数量 */
    private synchronized void onSubtraction(FoodCarInfoAdapter.ViewHolder holder) {
        //判断当前的数量，如果是不大于0则不做任何处理
        int position = holder.getPosition();
        FoodInfoBean bean = mDataList.get(position);
        if (toInt(holder.number.getText().toString()) > 0) {
            int i = toInt(holder.number.getText().toString());
            holder.number.setText("" + (i - 1));
            if (mNumberCallback != null) {
                mNumberCallback.numbersubLoad(i - toInt(holder.number.getText().toString()), toInt(holder.foodMonery.getText().toString()), bean, i - 1);
            }
        }
    }

    /** * 添加数量 */
    private synchronized void onAddition(FoodCarInfoAdapter.ViewHolder holder) {
    //得到当前购物车数量，然后加1
        int position = holder.getPosition();
        FoodInfoBean bean = mDataList.get(position);
        int i = toInt(holder.number.getText().toString());
        holder.number.setText("" + (i + 1));
        if (mNumberCallback != null){
            mNumberCallback.numberaddLoad(toInt(holder.number.getText().toString()) - i, toInt(holder.foodMonery.getText().toString()), bean, i + 1);
        }
    }

    public void setNumberCallback(NumberCallback numberCallback){
        this.mNumberCallback = numberCallback;
    }

    /** * String 转换int */
    public int toInt(String tostring) {
        return Integer.parseInt(tostring);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public interface NumberCallback{

        //@param  number 数量
        // @param  price 单价
        //点击加时候监听
        void numberaddLoad(int number, int price, FoodInfoBean bean, int count);
        //点击减时候监听
        void numbersubLoad(int number, int price, FoodInfoBean bean, int count);

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName;
        TextView foodMonery;
        TextView addBtn;
        TextView subtractBtn;
        TextView number;
        public ViewHolder(@NonNull View view) {
            super(view);
            foodImage = view.findViewById(R.id.foodImg);
            foodName = view.findViewById(R.id.name);
            number = view.findViewById(R.id.number);
            addBtn = view.findViewById(R.id.add);
            subtractBtn = view.findViewById(R.id.subtract);
            foodMonery = view.findViewById(R.id.monery);
        }
    }
}
