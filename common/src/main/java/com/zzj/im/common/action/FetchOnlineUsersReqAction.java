package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class FetchOnlineUsersReqAction extends Action{

    public FetchOnlineUsersReqAction() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_FETCH_ONLINE_USERS_REQ.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //页号
    private int page;
    //返回数量
    private int count;

}
