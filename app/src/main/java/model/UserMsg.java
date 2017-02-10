package model;

/**
 * Created by user on 2017/2/5.
 */

public class UserMsg {

    /**
     * msgID : 1
     * msgTime : 2017/02/02
     * msgFrom_userID : 01d7f07d-9676-4de8-bc5f-44faf3ab0551
     * msgFrom_userName : bbbb@gmail.com
     * msgTo_userID : 0cee9178-0b2d-42e9-859d-cf061e4750f3
     * msgType : 留言板通知
     * msgFromURL : AAA
     * msgContent : 您有一則新留言
     * msgRead : 已讀
     */

    private int msgID;
    private String msgTime;
    private String msgFrom_userID;
    private String msgFrom_userName;
    private String msgTo_userID;
    private String msgType;
    private String msgFromURL;
    private String msgContent;
    private String msgRead;

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

    public String getMsgFrom_userName() {
        return msgFrom_userName;
    }

    public void setMsgFrom_userName(String msgFrom_userName) {
        this.msgFrom_userName = msgFrom_userName;
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

    public String getMsgRead() {
        return msgRead;
    }

    public void setMsgRead(String msgRead) {
        this.msgRead = msgRead;
    }
}
