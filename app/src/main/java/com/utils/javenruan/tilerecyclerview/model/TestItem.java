package com.utils.javenruan.tilerecyclerview.model;

import com.utils.javenruan.tilerecyclerview.library.AbstractDataItem;

/**
 * Created by javenruan on 2016/8/11
 */
public class TestItem implements AbstractDataItem {

    private int rowSpan;
    private int colSpan;

    private int level;
    private int index;
    private String name;
    private String avatarUrl;

    public TestItem(String name, String avatarUrl, int level, int index) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.level = level;
        this.index = index;
        calculateSpan();
    }

    private void calculateSpan() {
        switch (level) {
            case 0:
                rowSpan = 1;
                colSpan = 1;
                break;
            case 1:
                rowSpan = 2;
                colSpan = 2;
                break;
            case 2:
                rowSpan = 2;
                colSpan = 3;
                break;
            default:
                rowSpan = 1;
                colSpan = 1;
                break;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        calculateSpan();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getColumnSpan() {
        return colSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }
}
