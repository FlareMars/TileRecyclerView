package com.utils.javenruan.tilerecyclerview.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by javenruan on 2016/8/11
 */
public class ModelUtils {

    private List<TestItem> items = new ArrayList<>();
    int currentOffset = 0;
    private Random random = new Random();

    public void moreItems(int size) {
        List<TestItem> result = new ArrayList<>(size);
        for (int i = 0;i < size;i++) {
            result.add(new TestItem("Item#" + currentOffset, "", random.nextInt(3), currentOffset));
            currentOffset++;
        }
        items.addAll(result);
    }

    public void clearItems() {
        items.clear();
        currentOffset = 0;
    }

    public List<TestItem> dataSource() {
        return items;
    }
}
