package model;

import java.io.Serializable;

/**
 * Created by user on 2017/2/12.
 */

public class BoardBean implements Serializable{
    /**
     * boardID : 0
     * boardTime : string
     * board_userID : string
     * board_animalID : 0
     * boardContent : string
     */

    private int boardID;
    private String boardTime;
    private String board_userID;
    private int board_animalID;
    private String boardContent;

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public String getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(String boardTime) {
        this.boardTime = boardTime;
    }

    public String getBoard_userID() {
        return board_userID;
    }

    public void setBoard_userID(String board_userID) {
        this.board_userID = board_userID;
    }

    public int getBoard_animalID() {
        return board_animalID;
    }

    public void setBoard_animalID(int board_animalID) {
        this.board_animalID = board_animalID;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }
}
