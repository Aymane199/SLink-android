package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListSharePersonnes {

    private List<SharePersonne> personnes;
    private RequestState state;

    public StateListSharePersonnes() {
        personnes = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListSharePersonnes(List<SharePersonne> personnes, RequestState state) {
        this.personnes = personnes;
        this.state = state;
    }

    public List<SharePersonne> getListPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<SharePersonne> personnes) {
        this.personnes = personnes;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

}


