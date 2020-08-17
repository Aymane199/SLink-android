package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("Gmail")
    @Expose
    private String gmail;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("token")
    @Expose
    private String token;


    public User() {
    }

    /**
     *
     * @param gmail
     * @param date
     * @param id
     * @param userName
     */
    public User(Integer id, String userName, String gmail, String date,String token) {
        super();
        this.id = id;
        this.userName = userName;
        this.gmail = gmail;
        this.date = date;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gmail='" + gmail + '\'' +
                ", date='" + date + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
