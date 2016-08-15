package com.utils.javenruan.tilerecyclerview.library;

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
            Log.d(TAG,  "hits = " + hits + " misses = " + misses + " size = " + stack.size());
            return stack.pop();
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
