package com.utils.javenruan.tilerecyclerview.library;

import java.util.List;

/**
 * Created by javenruan on 2016/8/11
 */
public class RowInfo {

    private final List<RowItem> items;
    private final int rowHeight;

    public RowInfo(int rowHeight, List<RowItem> items) {
        this.rowHeight = rowHeight;
        this.items = items;
    }

    public List<RowItem> getItems() {
        return items;
    }

    public int getRowHeight() {
        return rowHeight;
    }

}
