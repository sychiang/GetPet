package model;

/**
 * Created by iii on 2017/2/13.
 */

public class Board {


    /**
     * boardID : 1
     * boardTime : 2017/02/12
     * board_userID : 00c66755-d48c-46e0-9c89-d73e88847b69
     * UserName : KKK
     * Email : KK@gmail.com
     * board_animalID : 1
     * boardContent : 請問動物幾歲
     */

    private int boardID;
    private String boardTime;
    private String board_userID;
    private String UserName;
    private String Email;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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
