package model;

/**
 * Created by user on 2017/2/5.
 */

public class AnimalPic {

    /**
     * animalPicID : 3
     * animalPic_animalID : 1
     * animalPicAddress : http://i.imgur.com/QhV5fZa.jpg
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
