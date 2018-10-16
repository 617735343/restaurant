package com.imooc.imooc_restaurant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.imooc_restaurant.R;
import com.imooc.imooc_restaurant.bean.Order;
import com.imooc.imooc_restaurant.config.Config;
import com.imooc.imooc_restaurant.ui.activity.OrderDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder>{

    private List<Order> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public OrderAdapter(Context context,List<Order> datas){
        this.mContext=context;
        this.mDatas=datas;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.item_order_list, parent, false);

        return new OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        Order order = mDatas.get(position);
        Picasso.get()
                .load(Config.baseUrl+order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.mIvImage);
        if (order.getPs().size() > 0){
            holder.mTvLabel.setText(order.getPs().get(0).product.getName()+"等"+order.getCount()+"件商品");
        }else {
            holder.mTvLabel.setText("无消费");
        }

        holder.mTvName.setText(order.getRestaurant().getName());
        holder.mTvPrice.setText("共消费："+order.getPrice() + "元");

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailActivity.launch(mContext,mDatas.get(getLayoutPosition()));
                }
            });

            mIvImage = (ImageView) itemView.findViewById(R.id.id_iv_image);
            mTvName = (TextView) itemView.findViewById(R.id.id_tv_name);
            mTvLabel = (TextView) itemView.findViewById(R.id.id_tv_label);
            mTvPrice = (TextView) itemView.findViewById(R.id.id_tv_price);
        }
    }
}
