package com.example.rjq.coolweather.gson;

public class ClothBean {
    public int clothUrl;
    public String clothName;
    public String clothPrice;

    public ClothBean(int url, String name, String price) {
        this.clothName = name;
        this.clothPrice = price;
        this.clothUrl = url;
    }
}
