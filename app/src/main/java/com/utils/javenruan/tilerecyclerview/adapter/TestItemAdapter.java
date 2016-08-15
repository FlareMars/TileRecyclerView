package com.utils.javenruan.tilerecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utils.javenruan.tilerecyclerview.R;
import com.utils.javenruan.tilerecyclerview.library.AbstractDataItem;
import com.utils.javenruan.tilerecyclerview.model.TestItem;
import com.utils.javenruan.tilerecyclerview.library.TileDataSourceAdapter;

import java.util.List;

/**
 * Created by javenruan on 2016/8/11
 */
public class TestItemAdapter extends TileDataSourceAdapter<TestItemAdapter.ViewHolder> {

    private static final String TAG = "TestItemAdapter";

    private List<TestItem> itemList;

    public TestItemAdapter(List<TestItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public AbstractDataItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void bind(TestItem item) {
            textView.setText(item.getName());
        }
    }
}
