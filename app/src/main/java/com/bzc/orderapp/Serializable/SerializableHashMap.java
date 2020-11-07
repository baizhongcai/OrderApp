package com.bzc.orderapp.Serializable;

import java.io.Serializable;
import java.util.HashMap;

public class SerializableHashMap implements Serializable {

    private HashMap<String,Integer> mHashMap;

    public HashMap<String, Integer> getMap() {
        return mHashMap;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.mHashMap = map;
    }
}
