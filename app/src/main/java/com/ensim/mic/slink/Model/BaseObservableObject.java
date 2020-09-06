package com.ensim.mic.slink.Model;

import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class BaseObservableObject<T> {

    private T content;
    private RequestState state;
    private List<OnChangeObject> onChangeObjectListeners;


    public BaseObservableObject(T t, RequestState state) {
        this.content = t;
        this.state = state;
        onChangeObjectListeners = new ArrayList<>();
        notifyListeners();
    }

    public BaseObservableObject(T t) {
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

    public void addOnChangeObjectListener(OnChangeObject onChangeObjectListener) {
        this.onChangeObjectListeners.add(onChangeObjectListener);
    }

    private void notifyListeners(){
        switch (state){
            case LOADING:
                for (OnChangeObject listener : onChangeObjectListeners) {
                    listener.onLoading();
                }
                break;
            case SUCCESSFUL:
                for (OnChangeObject listener : onChangeObjectListeners) {
                    listener.onDataReady();
                }
                break;
            case FAILED:
                for (OnChangeObject listener : onChangeObjectListeners) {
                    listener.onFailed();
                }
                break;
        }


    }
}
