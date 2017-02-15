package model;

/**
 * Created by user on 2017/2/15.
 */
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class object_MapMarkData {
    @SerializedName("mapID")
    @Expose
    private Integer mapID;
    @SerializedName("mapType")
    @Expose
    private String mapType;
    @SerializedName("mapName")
    @Expose
    private String mapName;
    @SerializedName("maplatitude")
    @Expose
    private String maplatitude;
    @SerializedName("maplongitude")
    @Expose
    private String maplongitude;
    @SerializedName("mapTitle")
    @Expose
    private String mapTitle;
    @SerializedName("mapContent")
    @Expose
    private String mapContent;
    @SerializedName("mapAddressCity")
    @Expose
    private String mapAddressCity;
    @SerializedName("mapAddressTown")
    @Expose
    private String mapAddressTown;
    @SerializedName("mapAddressDetail")
    @Expose
    private String mapAddressDetail;
    @SerializedName("mapPic")
    @Expose
    private Object mapPic;

    public Integer getMapID() {
        return mapID;
    }

    public void setMapID(Integer mapID) {
        this.mapID = mapID;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMaplatitude() {
        return maplatitude;
    }

    public void setMaplatitude(String maplatitude) {
        this.maplatitude = maplatitude;
    }

    public String getMaplongitude() {
        return maplongitude;
    }

    public void setMaplongitude(String maplongitude) {
        this.maplongitude = maplongitude;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public String getMapContent() {
        return mapContent;
    }

    public void setMapContent(String mapContent) {
        this.mapContent = mapContent;
    }

    public String getMapAddressCity() {
        return mapAddressCity;
    }

    public void setMapAddressCity(String mapAddressCity) {
        this.mapAddressCity = mapAddressCity;
    }

    public String getMapAddressTown() {
        return mapAddressTown;
    }

    public void setMapAddressTown(String mapAddressTown) {
        this.mapAddressTown = mapAddressTown;
    }

    public String getMapAddressDetail() {
        return mapAddressDetail;
    }

    public void setMapAddressDetail(String mapAddressDetail) {
        this.mapAddressDetail = mapAddressDetail;
    }

    public Object getMapPic() {
        return mapPic;
    }

    public void setMapPic(Object mapPic) {
        this.mapPic = mapPic;
    }
}
