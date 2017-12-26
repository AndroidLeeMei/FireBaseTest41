package com.example.kevin.firebasetest4.FireBase;

import android.util.Log;

import java.util.Date;

/**
 * Created by kevin on 2017/12/2.
 */

public class Tenant {
        String createID;
        String houseID;
        String name;
        String id;
        String addr;
        String tel;
        String phone;
        String signDate;
    String startDate;
    String endDate;
        Integer rent;
        Integer period;
        Integer payDay;

    public Tenant() {
    }

    public Tenant(String createID, String houseID, String name, String id, String addr, String tel, String phone, String signDate, String startDate, String endDate, Integer rent, Integer period, Integer payDay) {
        this.createID = createID;
        this.houseID = houseID;
        this.name = name;
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.phone = phone;
        this.signDate = signDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rent = rent;
        this.period = period;
        this.payDay = payDay;
    }

    public String getCreateID() {
        return createID;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddr() {
        return addr;
    }

    public String getTel() {
        return tel;
    }

    public String getPhone() {
        return phone;
    }

    public String getSignDate() {
        return signDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getRent() {
        return rent;
    }

    public Integer getPeriod() {
        return period;
    }

    public Integer getPayDay() {
        return payDay;
    }
}
