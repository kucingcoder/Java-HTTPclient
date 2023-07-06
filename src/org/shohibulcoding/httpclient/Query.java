package org.shohibulcoding.httpclient;

public class Query {
    String key, value;

    public Query(){}

    public Query(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
