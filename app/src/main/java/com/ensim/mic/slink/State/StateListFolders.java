package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListFolders {

    private List<FolderOfUser> folders;
    private RequestState state;

    public StateListFolders() {
        folders = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListFolders(List<FolderOfUser> foldersOfUser, RequestState state) {
        this.folders = foldersOfUser;
        this.state = state;
    }

    public List<FolderOfUser> getListFolder() {
        return folders;
    }

    public void setFolders(List<FolderOfUser> folders) {
        this.folders = folders;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public int findIndexFolderById(int id) {
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getId().equals(id)) return i;
        }
        return -1;
    }

    public void setNbLikes(int id, int likes) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderOfUser folder = folders.get(index);
            int nbLikes = Integer.parseInt(folder.getLikes());
            nbLikes += likes;
            folder.setLikes(String.format("%d", nbLikes));
            if(nbLikes>=0)
            folders.set(index, folder);
        }
    }

    public void setNbLink(int id, int link) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderOfUser folder = folders.get(index);
            int nbLink = Integer.parseInt(folder.getLinks());
            nbLink += link;
            folder.setLinks(String.format("%d", nbLink));
            if(nbLink>=0)
                folders.set(index, folder);
        }
    }

    public void addlike(int id) {
        setNbLikes(id, 1);
    }

    public void deletelike(int id) {
        setNbLikes(id, -1);
    }

    public void addlink(int id) {
        setNbLink(id, 1);
    }

    public void deletelink(int id) {
        setNbLink(id, -1);
    }
}


