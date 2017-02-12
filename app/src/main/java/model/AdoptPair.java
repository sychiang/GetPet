package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/2/1.
 */

public class AdoptPair implements Serializable {

    /**
     * animalID : 0
     * animalKind : string
     * animalType : string
     * animalName : string
     * animalAddress : string
     * animalDate : string
     * animalGender : string
     * animalAge : 0
     * animalColor : string
     * animalBirth : string
     * animalChip : string
     * animalHealthy : string
     * animalDisease_Other : string
     * animalOwner_userID : string
     * animalReason : string
     * animalGetter_userID : string
     * animalAdopted : string
     * animalAdoptedDate : string
     * animalNote : string
     * animalData_Pic : [{"animalPicID":0,"animalPic_animalID":0,"animalPicAddress":"string"}]
     * animalData_Condition : [{"conditionID":0,"condition_animalID":0,"conditionAge":"string","conditionEconomy":"string","conditionHome":"string","conditionFamily":"string","conditionReply":"string","conditionPaper":"string","conditionFee":"string","conditionOther":"string"}]
     * board : [{"boardID":0,"boardTime":"string","board_userID":"string","board_animalID":0,"boardContent":"string"}]
     */

    private int animalID;
    private String animalKind;
    private String animalType;
    private String animalName;
    private String animalAddress;
    private String animalDate;
    private String animalGender;
    private int animalAge;
    private String animalColor;
    private String animalBirth;
    private String animalChip;
    private String animalHealthy;
    private String animalDisease_Other;
    private String animalOwner_userID;
    private String animalReason;
    private String animalGetter_userID;
    private String animalAdopted;
    private String animalAdoptedDate;
    private String animalNote;
    private List<AnimalDataPicBean> animalData_Pic;
    private List<AnimalDataConditionBean> animalData_Condition;
    private List<BoardBean> board;

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public String getAnimalKind() {
        return animalKind;
    }

    public void setAnimalKind(String animalKind) {
        this.animalKind = animalKind;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalAddress() {
        return animalAddress;
    }

    public void setAnimalAddress(String animalAddress) {
        this.animalAddress = animalAddress;
    }

    public String getAnimalDate() {
        return animalDate;
    }

    public void setAnimalDate(String animalDate) {
        this.animalDate = animalDate;
    }

    public String getAnimalGender() {
        return animalGender;
    }

    public void setAnimalGender(String animalGender) {
        this.animalGender = animalGender;
    }

    public int getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalColor() {
        return animalColor;
    }

    public void setAnimalColor(String animalColor) {
        this.animalColor = animalColor;
    }

    public String getAnimalBirth() {
        return animalBirth;
    }

    public void setAnimalBirth(String animalBirth) {
        this.animalBirth = animalBirth;
    }

    public String getAnimalChip() {
        return animalChip;
    }

    public void setAnimalChip(String animalChip) {
        this.animalChip = animalChip;
    }

    public String getAnimalHealthy() {
        return animalHealthy;
    }

    public void setAnimalHealthy(String animalHealthy) {
        this.animalHealthy = animalHealthy;
    }

    public String getAnimalDisease_Other() {
        return animalDisease_Other;
    }

    public void setAnimalDisease_Other(String animalDisease_Other) {
        this.animalDisease_Other = animalDisease_Other;
    }

    public String getAnimalOwner_userID() {
        return animalOwner_userID;
    }

    public void setAnimalOwner_userID(String animalOwner_userID) {
        this.animalOwner_userID = animalOwner_userID;
    }

    public String getAnimalReason() {
        return animalReason;
    }

    public void setAnimalReason(String animalReason) {
        this.animalReason = animalReason;
    }

    public String getAnimalGetter_userID() {
        return animalGetter_userID;
    }

    public void setAnimalGetter_userID(String animalGetter_userID) {
        this.animalGetter_userID = animalGetter_userID;
    }

    public String getAnimalAdopted() {
        return animalAdopted;
    }

    public void setAnimalAdopted(String animalAdopted) {
        this.animalAdopted = animalAdopted;
    }

    public String getAnimalAdoptedDate() {
        return animalAdoptedDate;
    }

    public void setAnimalAdoptedDate(String animalAdoptedDate) {
        this.animalAdoptedDate = animalAdoptedDate;
    }

    public String getAnimalNote() {
        return animalNote;
    }

    public void setAnimalNote(String animalNote) {
        this.animalNote = animalNote;
    }

    public List<AnimalDataPicBean> getAnimalData_Pic() {
        return animalData_Pic;
    }

    public void setAnimalData_Pic(List<AnimalDataPicBean> animalData_Pic) {
        this.animalData_Pic = animalData_Pic;
    }

    public List<AnimalDataConditionBean> getAnimalData_Condition() {
        return animalData_Condition;
    }

    public void setAnimalData_Condition(List<AnimalDataConditionBean> animalData_Condition) {
        this.animalData_Condition = animalData_Condition;
    }

    public List<BoardBean> getBoard() {
        return board;
    }

    public void setBoard(List<BoardBean> board) {
        this.board = board;
    }

    public static class AnimalDataPicBean {
        /**
         * animalPicID : 0
         * animalPic_animalID : 0
         * animalPicAddress : string
         */

        private int animalPicID;
        private int animalPic_animalID;
        private String animalPicAddress;

        public int getAnimalPicID() {
            return animalPicID;
        }

        public void setAnimalPicID(int animalPicID) {
            this.animalPicID = animalPicID;
        }

        public int getAnimalPic_animalID() {
            return animalPic_animalID;
        }

        public void setAnimalPic_animalID(int animalPic_animalID) {
            this.animalPic_animalID = animalPic_animalID;
        }

        public String getAnimalPicAddress() {
            return animalPicAddress;
        }

        public void setAnimalPicAddress(String animalPicAddress) {
            this.animalPicAddress = animalPicAddress;
        }
    }

    public static class AnimalDataConditionBean {
        /**
         * conditionID : 0
         * condition_animalID : 0
         * conditionAge : string
         * conditionEconomy : string
         * conditionHome : string
         * conditionFamily : string
         * conditionReply : string
         * conditionPaper : string
         * conditionFee : string
         * conditionOther : string
         */

        private int conditionID;
        private int condition_animalID;
        private String conditionAge;
        private String conditionEconomy;
        private String conditionHome;
        private String conditionFamily;
        private String conditionReply;
        private String conditionPaper;
        private String conditionFee;
        private String conditionOther;

        public int getConditionID() {
            return conditionID;
        }

        public void setConditionID(int conditionID) {
            this.conditionID = conditionID;
        }

        public int getCondition_animalID() {
            return condition_animalID;
        }

        public void setCondition_animalID(int condition_animalID) {
            this.condition_animalID = condition_animalID;
        }

        public String getConditionAge() {
            return conditionAge;
        }

        public void setConditionAge(String conditionAge) {
            this.conditionAge = conditionAge;
        }

        public String getConditionEconomy() {
            return conditionEconomy;
        }

        public void setConditionEconomy(String conditionEconomy) {
            this.conditionEconomy = conditionEconomy;
        }

        public String getConditionHome() {
            return conditionHome;
        }

        public void setConditionHome(String conditionHome) {
            this.conditionHome = conditionHome;
        }

        public String getConditionFamily() {
            return conditionFamily;
        }

        public void setConditionFamily(String conditionFamily) {
            this.conditionFamily = conditionFamily;
        }

        public String getConditionReply() {
            return conditionReply;
        }

        public void setConditionReply(String conditionReply) {
            this.conditionReply = conditionReply;
        }

        public String getConditionPaper() {
            return conditionPaper;
        }

        public void setConditionPaper(String conditionPaper) {
            this.conditionPaper = conditionPaper;
        }

        public String getConditionFee() {
            return conditionFee;
        }

        public void setConditionFee(String conditionFee) {
            this.conditionFee = conditionFee;
        }

        public String getConditionOther() {
            return conditionOther;
        }

        public void setConditionOther(String conditionOther) {
            this.conditionOther = conditionOther;
        }
    }

//    public static class BoardBean {
//        /**
//         * boardID : 0
//         * boardTime : string
//         * board_userID : string
//         * board_animalID : 0
//         * boardContent : string
//         */
//
//        private int boardID;
//        private String boardTime;
//        private String board_userID;
//        private int board_animalID;
//        private String boardContent;
//
//        public int getBoardID() {
//            return boardID;
//        }
//
//        public void setBoardID(int boardID) {
//            this.boardID = boardID;
//        }
//
//        public String getBoardTime() {
//            return boardTime;
//        }
//
//        public void setBoardTime(String boardTime) {
//            this.boardTime = boardTime;
//        }
//
//        public String getBoard_userID() {
//            return board_userID;
//        }
//
//        public void setBoard_userID(String board_userID) {
//            this.board_userID = board_userID;
//        }
//
//        public int getBoard_animalID() {
//            return board_animalID;
//        }
//
//        public void setBoard_animalID(int board_animalID) {
//            this.board_animalID = board_animalID;
//        }
//
//        public String getBoardContent() {
//            return boardContent;
//        }
//
//        public void setBoardContent(String boardContent) {
//            this.boardContent = boardContent;
//        }
//    }
}
