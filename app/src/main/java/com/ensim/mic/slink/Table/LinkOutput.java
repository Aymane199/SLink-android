package com.ensim.mic.slink.Table;

import java.util.Date;

public class LinkOutput {

    int id;
    int folder_id;
    String name;
    String url;
    String picture;
    String description;
    Date date;

    public LinkOutput(int id, int folder_id, String name, String url, String picture, String description, Date date) {
        this.id = id;
        this.folder_id = folder_id;
        this.name = name;
        this.setUrl(url);
        this.picture = picture;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", folder_id=" + folder_id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(int folder_id) {
        this.folder_id = folder_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            this.url = "http://" + url;
        else
            this.url = url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
