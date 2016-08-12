package com.utils.javenruan.tilerecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.utils.javenruan.tilerecyclerview.adapter.TileRecyclerViewAdapter;

/**
 * Created by javenruan on 2016/8/11
 */
public class TileRecyclerView extends RecyclerView {

    private int numColumns = 1;
    private int verticalSpacing = 1;
    private int horizontalSpacing = 1;

    public TileRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override public void setAdapter(@NonNull Adapter adapter) {
        if (!(adapter instanceof TileRecyclerViewAdapter)) {
            throw new UnsupportedOperationException(
                    "Adapter must be an instance of AsymmetricRecyclerViewAdapter");
        }

        TileRecyclerViewAdapter<?> adapter1 = (TileRecyclerViewAdapter<?>) adapter;
        super.setAdapter(adapter);

        adapter1.recalculateItemsPerRow();
    }

    public void onItemClick(int index, View v) {
        Toast.makeText(getContext(), "Item#" + index + "has been clicked!", Toast.LENGTH_SHORT).show();
    }

    public boolean onItemLongClick(int index, View v) {
        Toast.makeText(getContext(), "Item#" + index + "has been long clicked!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        addItemDecoration(new SpacesItemDecoration(verticalSpacing));
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int padding;

        public SpacesItemDecoration(int padding) {
            this.padding = padding;
        }

        @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = padding;
        }
    }

    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public int getColumnWidth() {
        return (getAvailableSpace() - ((numColumns - 1) * horizontalSpacing)) / numColumns;
    }

    private int getAvailableSpace() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }


}
