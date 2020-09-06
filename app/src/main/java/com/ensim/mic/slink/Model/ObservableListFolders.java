package com.ensim.mic.slink.Model;

import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class ObservableListFolders extends BaseObservableObject<List<FolderWithoutUser>> {


    public ObservableListFolders(List<FolderWithoutUser> folders, RequestState state) {
        super(folders, state);
    }

    public ObservableListFolders(List<FolderWithoutUser> folders) {
        super(folders);
    }

    public int findIndexFolderById(int id) {
        for (int i = 0; i < getContent().size(); i++) {
            if (Integer.parseInt(getContent().get(i).getId()) == id) return i;
        }
        return -1;
    }

    public void setNbLikes(int id, int likes) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderWithoutUser folder = getContent().get(index);
            int nbLikes;
            if(folder.getLikes() == null)
                nbLikes = 0;
            else
                nbLikes = Integer.parseInt(folder.getLikes());
            nbLikes += likes;
            folder.setLikes(String.format("%d", nbLikes));
            if(nbLikes>=0)
                getContent().set(index, folder);
        }
    }

    public void setNbLink(int id, int link) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderWithoutUser folder = getContent().get(index);
            int nbLink;
            if(folder.getLinks() == null) {
                nbLink = 0;
            }
            else {
                nbLink = Integer.parseInt(folder.getLinks());
            }
            nbLink += link;
            folder.setLinks(String.format("%d", nbLink));
            if(nbLink>=0)
                getContent().set(index, folder);
        }
    }

    public void addLike(int id) {
        setNbLikes(id, 1);
    }

    public void deleteLike(int id) {
        setNbLikes(id, -1);
    }

    public void addLink(int id) {
        setNbLink(id, 1);
    }

    public void deleteLink(int id) {
        setNbLink(id, -1);
    }

}
