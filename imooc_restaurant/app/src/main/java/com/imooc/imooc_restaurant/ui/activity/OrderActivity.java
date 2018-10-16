package com.imooc.imooc_restaurant.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.imooc_restaurant.R;
import com.imooc.imooc_restaurant.UserInfoHolder;
import com.imooc.imooc_restaurant.bean.Order;
import com.imooc.imooc_restaurant.bean.User;
import com.imooc.imooc_restaurant.biz.OrderBiz;
import com.imooc.imooc_restaurant.net.CommonCallback;
import com.imooc.imooc_restaurant.ui.adapter.OrderAdapter;
import com.imooc.imooc_restaurant.ui.view.CircleTransform;
import com.imooc.imooc_restaurant.ui.view.refresh.SwipeRefresh;
import com.imooc.imooc_restaurant.ui.view.refresh.SwipeRefreshLayout;
import com.imooc.imooc_restaurant.utils.T;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToLongBiFunction;

/**
 * 订单页
 */
public class OrderActivity extends BaseActivity {

    private Button mBtnOrder;
    private TextView mTvUsername;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mIvIcon;
    private OrderAdapter mAdapter;
    private List<Order> mDatas = new ArrayList<>();

    private OrderBiz mOrderBiz = new OrderBiz();

    private int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

        initEvent();

        loadDatas();
    }

    private void initEvent() {

        //下拉
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });

        //上拉
        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OrderActivity.this,ProductListActivity.class);
                startActivityForResult(intent,1001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK){
            loadDatas();
        }
    }

    private void loadMore() {
        startLoadingProgress();
        mOrderBiz.listByPage(++mCurrentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mCurrentPage--;
                mSwipeRefreshLayout.setPullUpRefreshing(false);
                if ("用户未登录".equals(e.getMessage())){
                    toLonginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                if (response.size()==0){
                    T.showToast("木有订单了...");
                    mSwipeRefreshLayout.setPullUpRefreshing(false);
                    return;
                }
                T.showToast("订单加载成功");
                mDatas.addAll(response);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }

    private void loadDatas() {

        startLoadingProgress();
        mOrderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);

                if ("用户未登录".equals(e.getMessage())){
                    toLonginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                mCurrentPage = 0;
                T.showToast("订单更新成功");
                mDatas.clear();
                mDatas.addAll(response);
                mAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    //防止手机按home键结束程序运行(最好要try、catch一下防止出错)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            try {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            }catch (Exception e) {
                //ignore
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        mBtnOrder=(Button)findViewById(R.id.id_btn_order);
        mTvUsername=(TextView)findViewById(R.id.id_tv_username);
        mRecyclerView=(RecyclerView)findViewById(R.id.id_recyclerview);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.id_swiperefresh);
        mIvIcon=(ImageView)findViewById(R.id.id_iv_icon);

        User user = UserInfoHolder.getInstance().getUser();
        if (user != null) {
            mTvUsername.setText(user.getUsername());
        }else {
            toLonginActivity();
            finish();
            return;
        }

        mSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW);

        mAdapter = new OrderAdapter(this,mDatas);

        //recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        //加载图片
        Picasso.get()
                .load(R.drawable.icon)
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(mIvIcon);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderBiz.onDestroy();
    }
}
