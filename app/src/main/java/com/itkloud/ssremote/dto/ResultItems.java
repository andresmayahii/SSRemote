package com.itkloud.ssremote.dto;

import java.util.List;

/**
 * Created by andressh on 20/12/14.
 */
public class ResultItems {

    private String name;
    private String count;
    private List<ItemData> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }
}
