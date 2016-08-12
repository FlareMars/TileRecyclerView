package com.utils.javenruan.tilerecyclerview;

import android.util.Log;

import java.util.Stack;

/**
 * Created by javenruan on 2016/8/12
 */
public class ObjectPool<T> {

    private static final String TAG = "ObjectPool";

    private Stack<T> stack = new Stack<>();
    private int hits = 0;
    private int misses = 0;

    public T get() {
        if (!stack.isEmpty()) {
            hits++;
            T obj = stack.pop();
            Log.d(TAG, "ObjectPool[" + obj.getClass().getSimpleName() + "]" + " : hits = " + hits + " size = " + stack.size());
            return obj;
        }

        misses++;
        return null;
    }

    public void clear() {
        stack.clear();
    }

    public void put(T obj) {
        stack.push(obj);
    }
}
