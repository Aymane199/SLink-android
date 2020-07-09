package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class State {

    static private State INSTATNCE = new State();

    static public State getInstance(){
        return INSTATNCE;
    }

    static private StateListFolders userFolders;
    private List<State.OnChangeUserFolders> onChangeUserFoldersListner;

    private State(){
        userFolders = new StateListFolders();
        onChangeUserFoldersListner = new ArrayList<>();
    }

    /*
    * State of the list of the folders
     */
    public StateListFolders getUserFolders() {
        return userFolders;
    }

    public void setUserFolders(StateListFolders userFolders) {
        for (FolderOfUser folderOfUser :
             userFolders.getListFolder()) {
            System.out.println(folderOfUser.toString());
        }
        State.userFolders = userFolders;
        for (State.OnChangeUserFolders listner : onChangeUserFoldersListner) {
            listner.onChange();
        }
    }

    public void setUserFoldersState(RequestState state) {
        userFolders.setState(state) ;
        for (State.OnChangeUserFolders listner : onChangeUserFoldersListner) {
            listner.onChange();
        }
    }

    public void setUserFoldersList(List<FolderOfUser> foldersOfUser) {
        this.userFolders.setFoldersOfUser(foldersOfUser); ;
        for (State.OnChangeUserFolders listner : onChangeUserFoldersListner) {
            listner.onChange();
        }
    }

    public void setOnChangeUserFoldersListner(OnChangeUserFolders onChangeUserFoldersListner) {
        this.onChangeUserFoldersListner.add(onChangeUserFoldersListner);
    }

    public interface OnChangeUserFolders{
        void onChange();
    }

    /*
    * State of the list of the links
     */


    /*
    * State of the list of saved links
     */

    /*
    * State of members of a folder
     */


}

