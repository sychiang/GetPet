package model;

/**
 * Created by user on 2017/1/29.
 */

public class Message {
    private String title;
    private String content;
    private String sender;
    private String receiver;


    public Message(String title, String content, String sender, String receiver) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {return content;}

    public String getSender() {return sender;}

    public String getReceiver() {return receiver;}
}
