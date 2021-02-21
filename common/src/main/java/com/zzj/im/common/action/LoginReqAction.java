package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class LoginReqAction extends Action{

    public LoginReqAction() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_LOGIN_REQ.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }

    private String mobile;
    private String password;

}
