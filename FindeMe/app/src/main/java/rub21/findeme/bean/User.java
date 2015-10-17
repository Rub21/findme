package rub21.findeme.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ruben on 10/2/15.
 */
public class User {

    private String user;
    private String email;
    private String idphone;
    private String chanel;
    private boolean status;
    private Coordinates coordinates = new Coordinates();


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public  String print(){
        String text = getUser() +"\n"+
                getEmail()+"\n"+
                getChanel()+"\n"+
                getIdphone()+"\n"+
                getCoordinates().getLng()+"\n"+
                getCoordinates().getLat()+"\n"+
                getCoordinates().isStatus()+"\n"+
                isStatus();
        System.out.print(text);
        return text;
    }
}
