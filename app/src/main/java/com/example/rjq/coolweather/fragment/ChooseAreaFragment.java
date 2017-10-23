package com.example.rjq.coolweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rjq.coolweather.R;
import com.example.rjq.coolweather.activity.MainActivity;
import com.example.rjq.coolweather.activity.WeatherActivity;
import com.example.rjq.coolweather.db.City;
import com.example.rjq.coolweather.db.County;
import com.example.rjq.coolweather.db.Province;
import com.example.rjq.coolweather.util.HttpUtil;
import com.example.rjq.coolweather.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    public static final String BASE_URL = "http://guolin.tech/api/china";

    private ProgressDialog progressDialog;
    @BindView(R.id.choose_area_title_tv)
    TextView chooseAreaTitleTv;
    @BindView(R.id.choose_area_back_btn)
    Button chooseAreaBackBtn;
    @BindView(R.id.choose_area_list_view)
    ListView chooseAreaListView;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<County> countyList = new ArrayList<>();

    private Province selectedProvince;
    private City selectedCity;

    public int currrentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        ButterKnife.bind(this,view);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        chooseAreaListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chooseAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currrentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }else if (currrentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    queryCounties();
                }else if (currrentLevel == LEVEL_COUNTY){
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if (getActivity() instanceof WeatherActivity){
                        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.swipeRefreshLayout.setRefreshing(true);
                        weatherActivity.requestWeather(weatherId);
                    }

                }
            }
        });
        chooseAreaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currrentLevel == LEVEL_COUNTY){
                    //Toast.makeText(getContext(), "LEVEL_COUNTY", Toast.LENGTH_SHORT).show();
                    queryCities();
                }else if (currrentLevel == LEVEL_CITY){
                    //Toast.makeText(getContext(), "LEVEL_CITY", Toast.LENGTH_SHORT).show();
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    public void backLevel(){
        if (currrentLevel == LEVEL_COUNTY){
            //Toast.makeText(getContext(), "LEVEL_COUNTY", Toast.LENGTH_SHORT).show();
            queryCities();
        }else if (currrentLevel == LEVEL_CITY){
            //Toast.makeText(getContext(), "LEVEL_CITY", Toast.LENGTH_SHORT).show();
            queryProvinces();
        }
    }

    private void queryProvinces(){
        chooseAreaTitleTv.setText("中国");
        chooseAreaBackBtn.setVisibility(View.GONE);

        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            chooseAreaListView.setSelection(0);
            currrentLevel = LEVEL_PROVINCE;
        }else{
            queryFromServer(BASE_URL,"province");
        }
    }

    private void queryCities(){
        chooseAreaTitleTv.setText(selectedProvince.getProvinceName());
        chooseAreaBackBtn.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            chooseAreaListView.setSelection(0);
            currrentLevel = LEVEL_CITY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            queryFromServer(BASE_URL+"/"+provinceCode,"city");
        }
    }

    private void queryCounties(){
        //StringBuffer s = new StringBuffer();
        chooseAreaTitleTv.setText(selectedCity.getCityName());
        chooseAreaBackBtn.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
                //s.append(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            chooseAreaListView.setSelection(0);
            currrentLevel = LEVEL_COUNTY;
            //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(),BASE_URL+"/"+selectedProvince.getProvinceCode()+"/"+selectedCity.getCityCode() , Toast.LENGTH_SHORT).show();
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            queryFromServer(BASE_URL+"/"+provinceCode+"/"+cityCode,"county");
        }

    }

    private void queryFromServer(String address, final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                                //Toast.makeText(getActivity(), responseText+"", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
