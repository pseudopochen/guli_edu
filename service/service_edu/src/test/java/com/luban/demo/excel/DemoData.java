package com.luban.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    @ExcelProperty(value = "student number", index = 0)
    private Integer sno;

    @ExcelProperty(value = "student name", index = 1)
    private String sname;
}
