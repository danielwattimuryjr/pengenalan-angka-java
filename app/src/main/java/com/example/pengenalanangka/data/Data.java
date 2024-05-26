package com.example.pengenalanangka.data;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<String> getAngkaList() {
        List<String> angkaList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            angkaList.add(String.valueOf(i));
        }
        return angkaList;
    }
}
