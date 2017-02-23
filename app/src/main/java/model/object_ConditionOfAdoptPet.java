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
        try {
            this.conditionAge = conditionAge;
        } catch (Exception e) {
            this.conditionAge = "18";
        }
        //this.conditionAge = (conditionAge.isEmpty() || conditionAge == null) ? "18" : conditionAge;
    }

    public String getConditionEconomy() {
        return conditionEconomy;
    }

    public void setConditionEconomy(String conditionEconomy) {
        try{
            this.conditionEconomy = conditionEconomy;
        } catch (Exception e){
            this.conditionEconomy = "不限";
        }
        //this.conditionEconomy = (conditionEconomy.isEmpty() || conditionEconomy == null) ? "不限" : conditionEconomy;
    }

    public String getConditionFamily() {
        return conditionFamily;
    }

    public void setConditionFamily(String conditionFamily) {
        try{
            this.conditionFamily = conditionFamily;
        } catch (Exception e){
            this.conditionFamily = "不限";
        }
        //this.conditionFamily = (conditionFamily.isEmpty() || conditionFamily == null) ? "不限" : conditionFamily;
    }

    public String getConditionFee() {
        return conditionFee;
    }

    public void setConditionFee(String conditionFee) {
        try{
            this.conditionFee = conditionFee;
        } catch (Exception e){
            this.conditionFee = "不限";
        }
        //this.conditionFee = (conditionFee.isEmpty() || conditionFee == null) ? "不限" : conditionFee;
    }

    public String getConditionHome() {
        return conditionHome;
    }

    public void setConditionHome(String conditionHome) {
        try {
            this.conditionHome = conditionHome;
        } catch (Exception e){
            this.conditionHome = "不限";
        }
        //this.conditionHome = (conditionHome.isEmpty() || conditionHome == null) ? "不限" : conditionHome;
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
        try {
            this.conditionOther = conditionOther;
        } catch (Exception e) {
            this.conditionOther = "不限";
        }
        //this.conditionOther = (conditionOther.isEmpty() || conditionOther == null) ? "不限" : conditionOther;
    }

    public String getConditionPaper() {
        return conditionPaper;
    }

    public void setConditionPaper(String conditionPaper) {
        try {
            this.conditionPaper = conditionPaper;
        } catch (Exception e) {
            this.conditionPaper = "不限";
        }
        //this.conditionPaper = (conditionPaper.isEmpty() || conditionPaper == null) ? "不限" : conditionPaper;
    }

    public String getConditionReply() {
        return conditionReply;
    }

    public void setConditionReply(String conditionReply) {
        try {
            this.conditionReply = conditionReply;
        }catch (Exception e){
            this.conditionReply = "不限";
        }
        //this.conditionReply = (conditionReply.isEmpty() || conditionReply == null) ? "不限" : conditionReply;
    }

}
