package model;

/**
 * Created by user on 2017/2/5.
 */

public class UserMsg {

    /**
     * msgID : 2
     * msgTime : 20170202
     * msgFrom_userID : 0cee9178-0b2d-42e9-859d-cf061e4750f3
     * msgTo_userID : aaa15863-b4c7-43ee-a73b-2cdc74b7f6a5
     * msgType : 認養通知
     * msgFromURL : AAAA
     * msgContent : 用戶AAA 想要認養您的寵物ww
     * msgFrom_userName : blueness927@gmail.com
     */

    private int msgID;
    private String msgTime;
    private String msgFrom_userID;
    private String msgTo_userID;
    private String msgType;
    private String msgFromURL;
    private String msgContent;
    private String msgFrom_userName;

    public int getMsgID() {
        return msgID;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgFrom_userID() {
        return msgFrom_userID;
    }

    public void setMsgFrom_userID(String msgFrom_userID) {
        this.msgFrom_userID = msgFrom_userID;
    }

    public String getMsgTo_userID() {
        return msgTo_userID;
    }

    public void setMsgTo_userID(String msgTo_userID) {
        this.msgTo_userID = msgTo_userID;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgFromURL() {
        return msgFromURL;
    }

    public void setMsgFromURL(String msgFromURL) {
        this.msgFromURL = msgFromURL;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgFrom_userName() {
        return msgFrom_userName;
    }

    public void setMsgFrom_userName(String msgFrom_userName) {
        this.msgFrom_userName = msgFrom_userName;
    }
}
