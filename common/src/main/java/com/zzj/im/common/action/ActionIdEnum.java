package com.zzj.im.common.action;

import lombok.Getter;

public enum ActionIdEnum {
    /**
     * 登录请求
     */
    ACTION_LOGIN_REQ("login_req","登录请求"),

    /**
     * 登录响应
     */
    ACTION_LOGIN_RESP("login_resp","登录响应"),

    /**
     *获取在线用户列表
     */
    ACTION_FETCH_ONLINE_USERS_REQ("fetch_online_users_req","获取在线用户列表"),

    /**
     * 响应在线用户列表
     */
    ACTION_FETCH_ONLINE_USERS_RESP("fetch_online_users_resp","响应在线用户列表"),

    /**
     * 发送消息请求
     */
    ACTION_SEND_MESSAGE_REQ("send_msg_req","发送消息请求"),

    /**
     * 发送消息响应
     */
    ACTION_SEND_MESSAGE_RESP("send_msg_resp","发送消息响应"),

    /**
     * 接受消息推送
     */
    ACTION_RECEIVE_MESSAGE_NOTIFY("receive_msg_notify","接受消息推送"),

    /**
     * 接受消息确认
     */
    ACTION_RECEIVE_MESSAGE_NOTIFY_ACK("receive_msg_ack","接受消息确认"),

    /**
     * 获取历史消息
     */
    ACTION_FETCH_HISTORY_MESSAGE_REQ("fetch_history_msg_req","请求历史消息"),

    /**
     * 响应历史消息
     */
    ACTION_FETCH_HISTORY_MESSAGE_RESP("fetch_history_msg_resp","响应历史消息")
    ;
    @Getter
    private String action;
    @Getter
    private String desc;

    ActionIdEnum(String action, String desc) {
        this.action = action;
        this.desc = desc;
    }
}
