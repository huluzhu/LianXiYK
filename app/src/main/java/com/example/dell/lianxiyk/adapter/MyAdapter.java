package com.example.dell.lianxiyk.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.lianxiyk.R;
import com.example.dell.lianxiyk.bean.Bean;

import java.util.List;

/**
 * Created by ${hujiqiang} on 2017/09/21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //声明两个集合用于接受构造方法传来的参数在本地使用
    private List<Bean.DataBean> list;
    //声明上下文引用，用于加载布局文件
    private Context context;


    //用构造方法传入需要的参数，
    public MyAdapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //返回MyViewHolder对象，通过构造方法传入加载布局文件得到的view对象
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.age.setText(list.get(position).getUserAge() + "岁");
        holder.zhiye.setText(list.get(position).getOccupation());
        holder.title.setText(list.get(position).getIntroduction());
        Glide.with(context)
                //load()下载图片
                .load(list.get(position).getUserImg())
                //init()显示到指定控件
                .into(holder.iv);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(holder.iv, "alpha", 0f, 1f);
        alpha.setDuration(2000);//设置动画时间
        alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
        alpha.start();//启动动画。
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        //返回数据源大小
        return list.size();
    }

    //自定义MyViewHolder类用于复用
    class MyViewHolder extends RecyclerView.ViewHolder {
        //声明imageview对象
        private ImageView iv;
        private TextView age;
        private TextView zhiye;
        private TextView title;

        //构造方法中初始化imageview对象
        public MyViewHolder(final View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.image);
            age = (TextView) itemView.findViewById(R.id.age);
            zhiye = (TextView) itemView.findViewById(R.id.zhiye);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}