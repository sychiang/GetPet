package model;

/**
 * Created by user on 2017/1/29.
 */

public class CMessage {
    private String subject;
    private String content;
    private String sender;
    private String receiver;


    public CMessage(String subject, String content, String sender, String receiver) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {return content;}

    public String getSender() {return sender;}

    public String getReceiver() {return receiver;}
}
