package com.example.air.wf.tools;

import com.example.air.wf.cotroller.ServiceRequest;
import com.example.air.wf.model.WeatherModel;

import java.io.IOException;

public class test {
    public static void main(String[] args) {
        ServiceRequest s = new ServiceRequest();
        WeatherModel w = null;
        try {
            w = s.getModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(w.getDate());
    }
}
