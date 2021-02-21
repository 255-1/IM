package com.zzj.im.common.action;

/*
* 登录请求的Action
* */

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class ReceiveMessageNotifyAckAction extends Action{

    public ReceiveMessageNotifyAckAction() {
        this.setActionType("");
        this.setAction(ActionIdEnum.ACTION_RECEIVE_MESSAGE_NOTIFY_ACK.getAction());
        this.setRequestId(UUID.randomUUID().toString());

    }
    //消息id
    private Long messageId;


}
