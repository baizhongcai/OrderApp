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

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.ViewHolder>{
    private List<FoodInfoBean> mDataList;
    private Context mContext;
    private FoodCarInfoAdapter.NumberCallback mNumberCallback;
    private Map<String,Integer> mCountList;


    public StatementAdapter(List<FoodInfoBean> data, Context context, Map<String,Integer> countList){
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
    public StatementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_statement_item,parent
                ,false);

        StatementAdapter.ViewHolder viewHolder = new StatementAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatementAdapter.ViewHolder holder, int position) {
        FoodInfoBean bean = mDataList.get(position);
        holder.foodImage.setImageResource(bean.getImgId());
        holder.foodName.setText(bean.getName());
        holder.foodMonery.setText("单价：" +  bean.getMonery() + "");
        holder.foodCount.setText("数量：" + mCountList.get(bean.getId()) + "");
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName;
        TextView foodMonery;
        TextView foodCount;

        public ViewHolder(@NonNull View view) {
            super(view);
            foodImage = view.findViewById(R.id.foodImg);
            foodName = view.findViewById(R.id.name);
            foodCount = view.findViewById(R.id.count);
            foodMonery = view.findViewById(R.id.monery);
        }
    }

}
