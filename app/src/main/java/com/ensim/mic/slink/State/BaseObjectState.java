package com.ensim.mic.slink.State;

import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class BaseObjectState<T> {

    private T content;
    private RequestState state;
    private List<OnChangeObject> onChangeObjectListeners;


    public BaseObjectState(T t, RequestState state) {
        this.content = t;
        this.state = state;
        onChangeObjectListeners = new ArrayList<>();
        notifyListeners();
    }

    public BaseObjectState(T t) {
        this.content = t;
        state = RequestState.SUCCESSFUL;
        onChangeObjectListeners = new ArrayList<>();
        notifyListeners();
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
        notifyListeners();

    }

    public void addOnChangeObjectListener(OnChangeObject onChangeObjectListners) {
        this.onChangeObjectListeners.add(onChangeObjectListners);
    }

    private void notifyListeners(){
        switch (state){
            case LOADING:
                for (OnChangeObject listner : onChangeObjectListeners) {
                    listner.onLoading();
                }
                break;
            case SUCCESSFUL:
                for (OnChangeObject listner : onChangeObjectListeners) {
                    listner.onDataReady();
                }
                break;
            case FAILED:
                for (OnChangeObject listner : onChangeObjectListeners) {
                    listner.onFailed();
                }
                break;
        }


    }
}
