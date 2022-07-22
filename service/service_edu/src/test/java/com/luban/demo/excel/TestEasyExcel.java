package com.luban.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String fnam = "/home/pochen/Desktop/write.xlsx";
        //EasyExcel.write(fnam, DemoData.class).sheet("Student List").doWrite(getData());
        EasyExcel.read(fnam, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }
}
