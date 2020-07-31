package com.ensim.mic.slink.State;

import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class BaseObjectState<T> {

    private T object;
    private RequestState state;
    private List<OnChangeObject> onChangeObjectListeners;


    public BaseObjectState(T t, RequestState state) {
        this.object = t;
        this.state = state;
        onChangeObjectListeners = new ArrayList<>();
        notifyListeners();
    }

    public BaseObjectState(T t) {
        this.object = t;
        state = RequestState.SUCCESSFUL;
        onChangeObjectListeners = new ArrayList<>();
        notifyListeners();
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
        notifyListeners();

    }

    public void setOnChangeObjectListeners(OnChangeObject onChangeObjectListners) {
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
                    System.out.println(object);
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
