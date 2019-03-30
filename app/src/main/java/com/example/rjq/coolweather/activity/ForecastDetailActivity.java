package com.example.rjq.coolweather.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rjq.coolweather.R;
import com.example.rjq.coolweather.gson.ClothBean;
import com.example.rjq.coolweather.util.FormatterUtil;
import com.example.rjq.coolweather.util.GlideUtil;
import com.example.rjq.coolweather.util.ThemeUtil;
import com.example.rjq.coolweather.view.RoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDetailActivity extends AppCompatActivity {
    public static final String PARAM_DATA = "data";
    public static final String PARAM_INFO = "info";
    public static final String PARAM_MAX = "max";
    public static final String PARAM_MIN = "min";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_tem)
    TextView tvTem;
    @BindView(R.id.tv_cloth)
    TextView tvCloth;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String data, info, max, min;
    private int minTem, maxTem;
    private List<ClothBean> clothBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setStatusBarColor(this, getResources().getColor(R.color.color_f2f6fa));
        setContentView(R.layout.activity_forecast_detail);
        initData();
        initView();
    }

    private void initData() {
        ButterKnife.bind(this);
        data = getIntent().getStringExtra(PARAM_DATA);
        info = getIntent().getStringExtra(PARAM_INFO);
        max = getIntent().getStringExtra(PARAM_MAX);
        min = getIntent().getStringExtra(PARAM_MIN);
        try {
            minTem = Integer.parseInt(min);
            maxTem = Integer.parseInt(max);
        } catch (Exception e) {
            minTem = 15;
            maxTem = 25;
        }
        //构造衣物假数据
        ClothBean clothBean1 = new ClothBean(R.mipmap.cloth16, "男士牛津纺纯棉青年纯色韩版牛仔衬衣色", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean2 = new ClothBean(R.mipmap.cloth15, "春季商务休闲青年韩版潮流薄款衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean3 = new ClothBean(R.mipmap.cloth2, "春季男士商务韩版修身黑色潮流衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean4 = new ClothBean(R.mipmap.cloth14, "罗蒙男士纯棉牛津纺休闲免烫商务衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean5 = new ClothBean(R.mipmap.cloth12, "男士牛津纺纯棉青年纯色韩版牛仔衬衣色", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean6 = new ClothBean(R.mipmap.cloth11, "恒源祥纯棉格子宽松韩版衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean7 = new ClothBean(R.mipmap.cloth10, "2019夏季新款小领子衬衣正装女装", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean8 = new ClothBean(R.mipmap.cloth5, "百褶方领雪纺白色衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean9 = new ClothBean(R.mipmap.cloth1, "经典女士自留白色宽松白衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean10 = new ClothBean(R.mipmap.cloth3, "尚都比拉立领韩版修身衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean11 = new ClothBean(R.mipmap.cloth8, "格子男士文艺元素休闲衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean12 = new ClothBean(R.mipmap.cloth7, "青少年修身商务休闲黑白蓝色男士衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        ClothBean clothBean13 = new ClothBean(R.mipmap.cloth6, "偶保罗男士牛津纺格子休闲修身商务黑白蓝纯棉舒适性宽松型衬衣", FormatterUtil.formatCurrencyNoSignMaxTwoDecimal(Math.random()*150 + 50));
        clothBeans.add(clothBean1);clothBeans.add(clothBean2);clothBeans.add(clothBean3);clothBeans.add(clothBean4);clothBeans.add(clothBean5);
        clothBeans.add(clothBean6);clothBeans.add(clothBean7);clothBeans.add(clothBean8);clothBeans.add(clothBean9);clothBeans.add(clothBean10);
        clothBeans.add(clothBean11);clothBeans.add(clothBean12);clothBeans.add(clothBean13);
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(data);
        tvWeather.setText(info);
        tvTem.setText(getString(R.string.tem_duration, min, max));
        //根据天气显示icon
        if (info.contains("晴")) {
            ivWeather.setImageResource(R.mipmap.fine_weather);
        } else if (info.contains("阴") || info.contains("云")) {
            ivWeather.setImageResource(R.mipmap.yintian);
        } else if (info.contains("雨")) {
            ivWeather.setImageResource(R.mipmap.yu);
        } else if (info.contains("雪")){
            ivWeather.setImageResource(R.mipmap.xue);
        } else if (info.contains("霾") || info.contains("雾")){
            ivWeather.setImageResource(R.mipmap.wumai);
        }
        //根据温度显示衣物
        if (maxTem <= 0) {
            tvCloth.setText(getString(R.string.clothing, "羽绒服"));
        } else if (maxTem <= 10) {
            tvCloth.setText(getString(R.string.clothing, "棉衣"));
        } else if (maxTem <= 20) {
            tvCloth.setText(getString(R.string.clothing, "薄外套"));
        } else if (maxTem < 28) {
            tvCloth.setText(getString(R.string.clothing, "衬衣"));
        } else if (maxTem >= 28) {
            tvCloth.setText(getString(R.string.clothing, "T恤衫"));
        }
        //初始化推荐衣服列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        public Adapter() {

        }

        @Override
        public int getItemCount() {
            return clothBeans.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ClothBean clothBean = clothBeans.get(position);
            holder.tvName.setText(clothBean.clothName);
            holder.tvPrice.setText("￥" + clothBean.clothPrice);
            GlideUtil.load(ForecastDetailActivity.this, clothBean.clothUrl, holder.imageView, GlideUtil.REQUEST_OPTIONS);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ForecastDetailActivity.this).inflate(R.layout.item_cloth, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("http://www.taobao.com");
                        intent.setData(content_url);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (Exception e) {

                    }

                }
            });
            return viewHolder;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RoundAngleImageView imageView;
            TextView tvName, tvPrice, tvOpen;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_cloth);
                tvName = itemView.findViewById(R.id.tv_name);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvOpen = itemView.findViewById(R.id.tv_open);
            }
        }
    }

}
