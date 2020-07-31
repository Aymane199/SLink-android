package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class LinksState extends BaseObjectState<List<LinkOfFolder>> {

    private int folderId;

    public LinksState(List<LinkOfFolder> linkOfFolders, RequestState state, int folderId) {
        super(linkOfFolders, state);
        this.folderId = folderId;
    }

    public LinksState(List<LinkOfFolder> linkOfFolders) {
        super(linkOfFolders);
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
