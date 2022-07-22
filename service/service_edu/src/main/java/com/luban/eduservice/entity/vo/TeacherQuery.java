package com.luban.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty(value = "teach name")
    private String name;

    @ApiModelProperty(value = "title")
    private Integer level;

    @ApiModelProperty(value = "start time", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "end time", example = "2019-12-01 10:10:10")
    private String end;
}
