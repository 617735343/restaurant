package com.imooc.imooc_restaurant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.imooc_restaurant.R;
import com.imooc.imooc_restaurant.bean.Product;
import com.imooc.imooc_restaurant.config.Config;
import com.imooc.imooc_restaurant.utils.T;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProductDetailActivity extends BaseActivity {

    private Product mProduct;

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvPrice;

    private static final String KEY_PRODUCT = "key_product";

    public static void launch(Context context,Product product){
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT,product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setUpToolbar();
        setTitle("详情");

        Intent intent = getIntent();
        if (intent != null){
            mProduct = (Product) intent.getSerializableExtra(KEY_PRODUCT);
        }

        if (mProduct == null){
            T.showToast("参数传递错误");
            return;
        }

        initView();
    }

    private void initView() {
        mIvImage=(ImageView)findViewById(R.id.id_iv_image);
        mTvTitle=(TextView)findViewById(R.id.id_tv_title);
        mTvDesc=(TextView)findViewById(R.id.id_tv_desc);
        mTvPrice=(TextView)findViewById(R.id.id_tv_price);

        Picasso.get()
                .load(Config.baseUrl+mProduct.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(mIvImage);
        mTvTitle.setText(mProduct.getName());
        mTvDesc.setText(mProduct.getDescription());
        mTvPrice.setText(mProduct.getPrice()+"元/份");
    }
}
