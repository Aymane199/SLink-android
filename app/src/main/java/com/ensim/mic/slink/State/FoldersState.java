package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class FoldersState extends BaseObjectState<List<FolderOfUser>> {


    public FoldersState(List<FolderOfUser> folderOfUsers, RequestState state) {
        super(folderOfUsers, state);
    }

    public FoldersState(List<FolderOfUser> folderOfUsers) {
        super(folderOfUsers);
    }

    public int findIndexFolderById(int id) {
        for (int i = 0; i < getObject().size(); i++) {
            if (Integer.parseInt(getObject().get(i).getId())==id) return i;
        }
        return -1;
    }

    public void setNbLikes(int id, int likes) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderOfUser folder = getObject().get(index);
            int nbLikes;
            if(folder.getLikes() == null)
                nbLikes = 0;
            else
                nbLikes = Integer.parseInt(folder.getLikes());
            nbLikes += likes;
            folder.setLikes(String.format("%d", nbLikes));
            if(nbLikes>=0)
                getObject().set(index, folder);
        }
    }

    public void setNbLink(int id, int link) {
        int index = findIndexFolderById(id);
        if (index != -1) {
            FolderOfUser folder = getObject().get(index);
            int nbLink = Integer.parseInt(folder.getLinks());
            nbLink += link;
            folder.setLinks(String.format("%d", nbLink));
            if(nbLink>=0)
                getObject().set(index, folder);
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
