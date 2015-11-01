package rub21.findeme.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ruben on 10/2/15.
 */
public class User {

    private String user;
    private String idphone;
    private String chanel;
    private boolean status;
    private Double lat;
    private Double lng;
    private boolean loc_status;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIdphone() {
        return idphone;
    }

    public void setIdphone(String idphone) {
        this.idphone = idphone;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public boolean isLoc_status() {
        return loc_status;
    }

    public void setLoc_status(boolean loc_status) {
        this.loc_status = loc_status;
    }
}
