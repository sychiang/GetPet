package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/2/1.
 */

public class AdoptPair implements Serializable {

    /**
     * animalID : 1
     * animalName : 小黃
     * animalAddress : 高雄
     * animalDate : 20170103
     * animalGender : 母
     * animalAge : 3
     * animalColor : 黃色
     * animalBirth : 否、沒有生產過
     * animalChip : 否
     * animalHealthy : 健康
     * animalDisease_Other : 已做體內外驅蟲、打過第一劑
     * animalOwner_userID : 1
     * animalReason : 愛媽救援的浪浪
     * animalGetter_userID : 1
     * animalAdopted : no
     * animalAdoptedDate : null
     * animalNote : 需要先收取結紮保證金1000元、結紮後退還。活潑健康、但不喜歡被關籠（關籠會叫）、不挑食、聰明乖巧、學習快。請大家多多幫忙分享，年前送不出去就要原放，希望她別再流浪
     * animalKind : 狗
     * animalType : 米克斯
     * animalData_Pic : [{"animalPicID":1,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/8tnbGMR.jpg"},{"animalPicID":2,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/lmtEwvp.jpg"},{"animalPicID":3,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/QhV5fZa.jpg"},{"animalPicID":9,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/8tnbGMR.jpg"},{"animalPicID":10,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/lmtEwvp.jpg"},{"animalPicID":11,"animalPic_animalID":1,"animalPicAddress":"http://i.imgur.com/QhV5fZa.jpg"}]
     * animalData_Condition : [{"conditionID":1,"condition_animalID":1,"conditionAge":"不限","conditionEconomy":"需有穩定收入","conditionHome":"不限","conditionFamily":"不限","conditionReply":"不限","conditionPaper":"不限","conditionFee":"不限","conditionOther":"不限"}]
     */

    private int animalID;
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
    private Object animalAdoptedDate;
    private String animalNote;
    private String animalKind;
    private String animalType;
    private List<AnimalDataPicBean> animalData_Pic;
    private List<AnimalDataConditionBean> animalData_Condition;

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
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

    public Object getAnimalAdoptedDate() {
        return animalAdoptedDate;
    }

    public void setAnimalAdoptedDate(Object animalAdoptedDate) {
        this.animalAdoptedDate = animalAdoptedDate;
    }

    public String getAnimalNote() {
        return animalNote;
    }

    public void setAnimalNote(String animalNote) {
        this.animalNote = animalNote;
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

    public static class AnimalDataPicBean {
        /**
         * animalPicID : 1
         * animalPic_animalID : 1
         * animalPicAddress : http://i.imgur.com/8tnbGMR.jpg
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
         * conditionID : 1
         * condition_animalID : 1
         * conditionAge : 不限
         * conditionEconomy : 需有穩定收入
         * conditionHome : 不限
         * conditionFamily : 不限
         * conditionReply : 不限
         * conditionPaper : 不限
         * conditionFee : 不限
         * conditionOther : 不限
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
}
