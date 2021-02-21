package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class SendMessagRespAcion extends Action{

    public SendMessagRespAcion() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_SEND_MESSAGE_RESP.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //发送的消息id
    private Long messageId;

    //发送的结果
    private Boolean result;


}
