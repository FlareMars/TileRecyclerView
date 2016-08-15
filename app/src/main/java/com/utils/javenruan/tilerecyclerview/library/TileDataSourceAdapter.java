package com.utils.javenruan.tilerecyclerview.library;

import android.support.v7.widget.RecyclerView;

/**
 * Created by javenruan on 2016/8/11
 */
public abstract class TileDataSourceAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public abstract AbstractDataItem getItem(int position);
}
