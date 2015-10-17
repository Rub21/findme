package rub21.findeme.bean;

/**
 * Created by ruben on 10/3/15.
 */
public class Coordinates {
    private Double lat;
    private Double lng;
    private boolean status;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
