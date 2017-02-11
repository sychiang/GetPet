package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by iii on 2017/2/9.
 */

public class object_petDataForSelfDB implements Serializable {
    private int animalID;
    private String animalName;
    private String animalAddress;
    private String animalDate;
    private String animalGender;
    private String animalAge;
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
    //**後來新增的欄位
    private String animalKind;
    private String animalType;
    private ArrayList<object_OfPictureImgurSite> animalData_Pic;
    private ArrayList<object_ConditionOfAdoptPet> animalData_Condition;

    //**
    public object_petDataForSelfDB(){
        this.animalID = 0;
    }


    public ArrayList<object_ConditionOfAdoptPet> getAnimalData_Condition() {
        return animalData_Condition;
    }

    public void setAnimalData_Condition(ArrayList<object_ConditionOfAdoptPet> animalData_Condition) {
        this.animalData_Condition = animalData_Condition;
    }

    public ArrayList<object_OfPictureImgurSite> getAnimalData_Pic() {
        return animalData_Pic;
    }

    public void setAnimalData_Pic(ArrayList<object_OfPictureImgurSite> animalData_Pic) {
        this.animalData_Pic = animalData_Pic;
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

    //**

    public String getAnimalAddress() {
        return animalAddress;
    }

    public void setAnimalAddress(String animalAddress) {
        this.animalAddress = animalAddress;
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

    public String getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(String animalAge) {
        this.animalAge = animalAge;
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

    public String getAnimalColor() {
        return animalColor;
    }

    public void setAnimalColor(String animalColor) {
        this.animalColor = animalColor;
    }



    public String getAnimalDate() {
        return animalDate;
    }

    public void setAnimalDate(String animalDate) {
        this.animalDate = animalDate;
    }

    public String getAnimalDisease_Other() {
        return animalDisease_Other;
    }

    public void setAnimalDisease_Other(String animalDisease_Other) {
        this.animalDisease_Other = animalDisease_Other;
    }

    public String getAnimalGender() {
        return animalGender;
    }

    public void setAnimalGender(String animalGender) {
        this.animalGender = animalGender;
    }

    public String getAnimalGetter_userID() {
        return animalGetter_userID;
    }

    public void setAnimalGetter_userID(String animalGetter_userID) {
        this.animalGetter_userID = animalGetter_userID;
    }

    public String getAnimalHealthy() {
        return animalHealthy;
    }

    public void setAnimalHealthy(String animalHealthy) {
        this.animalHealthy = animalHealthy;
    }

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

    public String getAnimalNote() {
        return animalNote;
    }

    public void setAnimalNote(String animalNote) {
        this.animalNote = animalNote;
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
}
