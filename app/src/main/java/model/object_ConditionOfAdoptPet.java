package model;

import java.io.Serializable;

/**
 * Created by iii on 2017/2/9.
 */

public class object_ConditionOfAdoptPet implements Serializable {
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

    public void createAdefault_object_ConditionOfAdoptPet() {
        this.conditionAge = "不限";
        this.conditionEconomy = "不限";
        this.conditionHome = "不限";
        this.conditionFamily = "不限";
        this.conditionReply = "不限";
        this.conditionPaper = "不限";
        this.conditionFee = "不限";
        this.conditionOther = "不限";
    }

    public object_ConditionOfAdoptPet() {
        this.conditionID = 1;
        this.condition_animalID = 1;
    }

    @Override
    public String toString() {
        String l_s = conditionID + " " + condition_animalID + " " + conditionAge + " " + conditionEconomy + " " + conditionHome
                + " " + conditionFamily + " " + conditionReply + " " + conditionPaper + " " + conditionFee + " " + conditionOther;
        return l_s;
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
        this.conditionAge = (conditionAge.isEmpty() ? "18" : conditionAge);
    }

    public String getConditionEconomy() {
        return conditionEconomy;
    }

    public void setConditionEconomy(String conditionEconomy) {
        this.conditionEconomy = conditionEconomy.isEmpty() ? "不限" : conditionEconomy;
    }

    public String getConditionFamily() {
        return conditionFamily;
    }

    public void setConditionFamily(String conditionFamily) {
        this.conditionFamily = conditionFamily.isEmpty() ? "不限" : conditionFamily;
    }

    public String getConditionFee() {
        return conditionFee;
    }

    public void setConditionFee(String conditionFee) {
        this.conditionFee = conditionFee.isEmpty() ? "不限" : conditionFee;
    }

    public String getConditionHome() {
        return conditionHome;
    }

    public void setConditionHome(String conditionHome) {
        this.conditionHome = conditionHome.isEmpty() ? "不限" : conditionHome;
    }

    public int getConditionID() {
        return conditionID;
    }

    public void setConditionID(int conditionID) {
        this.conditionID = 0;
    }

    public String getConditionOther() {
        return conditionOther;
    }

    public void setConditionOther(String conditionOther) {
        this.conditionOther = conditionOther.isEmpty() ? "不限" : conditionOther;
    }

    public String getConditionPaper() {
        return conditionPaper;
    }

    public void setConditionPaper(String conditionPaper) {
        this.conditionPaper = conditionPaper.isEmpty() ? "不限" : conditionPaper;
    }

    public String getConditionReply() {
        return conditionReply;
    }

    public void setConditionReply(String conditionReply) {
        this.conditionReply = conditionReply.isEmpty() ? "不限" : conditionReply;
    }

}
