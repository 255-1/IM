package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class LoginRespAction extends Action{

    public LoginRespAction() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_LOGIN_RESP.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //用户id
    private Long userId;
    //登录结果
    private Boolean result;



}
