package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class SendMessagReqAcion extends Action{

    public SendMessagReqAcion() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_SEND_MESSAGE_REQ.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //目标用户
    private Long toUserId;

    //消息内容格式
    private String messageType;
    //消息内容
    private String message;


}
