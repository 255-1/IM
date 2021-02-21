package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class ReceiveMessageNotifyAction extends Action{

    public ReceiveMessageNotifyAction() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_RECEIVE_MESSAGE_NOTIFY.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //发送消息的用户id
    private Long fromUserId;

    //发送消息的用户的手机号
    private String mobile;

    //消息id
    private Long messageId;

    //消息内容格式
    private String messageType;
    //消息内容
    private String message;


}
