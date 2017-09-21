package com.example.dell.lianxiyk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.lianxiyk.Utils.OkHttpUtils;
import com.example.dell.lianxiyk.adapter.MyAdapter;
import com.example.dell.lianxiyk.bean.Bean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //获取的json数据中的数据集合
    private List<Bean.DataBean> list = new ArrayList<>();
    //声明recyclerview引用
    private RecyclerView mRecyclerView;

    //声明自定义请求类
    private MyAdapter adapter;
    private RecyclerView recyclerview;
    private String url = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=";
    private int page = 1;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //开启网络下载数据的方法
        startTask();
        //recyclerview滚动监听
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    page++;
                    startTask();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void startTask() {
        //通过类名直接调用静态方法获取单例对象再调用getBeanOfOK()方法传入参数通过接口回调获取数据
        OkHttpUtils.getInstance().getBeanOfOk(this, url + page, Bean.class,
                new OkHttpUtils.CallBack<Bean>() {
                    @Override
                    public void getData(Bean bean) {
                        if (bean != null) {

                            //如果不为空用本地list接收
                            list.addAll(bean.getData());
                            //刷新适配器
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        //创建自定义适配器对象
        adapter = new MyAdapter(this, list);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, list.get(position).getIntroduction().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //设置recyclerview适配器
        recyclerview.setAdapter(adapter);
    }

    public void baocuo(View view) {
        Object o = null;
        o.toString();
        System.out.print(o);
    }
}
