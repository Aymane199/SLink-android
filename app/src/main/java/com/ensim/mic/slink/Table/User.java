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
    @SerializedName("folders")
    @Expose
    private Folders folders;
    @SerializedName("subscribes")
    @Expose
    private Subscribes subscribes;
    @SerializedName("shares")
    @Expose
    private Shares shares;


    public User() {
    }

    /**
     *
     * @param gmail
     * @param date
     * @param shares
     * @param subscribes
     * @param folders
     * @param id
     * @param userName
     */
    public User(Integer id, String userName, String gmail, String date, Folders folders, Subscribes subscribes, Shares shares) {
        super();
        this.id = id;
        this.userName = userName;
        this.gmail = gmail;
        this.date = date;
        this.folders = folders;
        this.subscribes = subscribes;
        this.shares = shares;
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

    public Folders getFolders() {
        return folders;
    }

    public void setFolders(Folders folders) {
        this.folders = folders;
    }

    public Subscribes getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Subscribes subscribes) {
        this.subscribes = subscribes;
    }

    public Shares getShares() {
        return shares;
    }

    public void setShares(Shares shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gmail='" + gmail + '\'' +
                ", date='" + date + '\'' +
                ", folders=" + folders +
                ", subscribes=" + subscribes +
                ", shares=" + shares +
                '}';
    }
}
class Folders {
}

class Subscribes {
}

class Shares {
}