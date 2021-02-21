package com.zzj.im.common.action;

import lombok.Data;
import lombok.ToString;

/*
* 基类
* */
@Data
@ToString
public class Action {
    /*
    * 行为
    * */
    private String action;
    /*
    * 行为类型，系统类，用户类
    * */
    private String actionType;

    private String requestId;

    /*
    * json格式
    */
    private String payload;
}
