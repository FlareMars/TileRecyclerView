package com.utils.javenruan.tilerecyclerview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.utils.javenruan.tilerecyclerview.R;
import com.utils.javenruan.tilerecyclerview.TileRecyclerView;
import com.utils.javenruan.tilerecyclerview.Utils;
import com.utils.javenruan.tilerecyclerview.model.AbstractDataItem;
import com.utils.javenruan.tilerecyclerview.model.RowInfo;
import com.utils.javenruan.tilerecyclerview.model.RowItem;
import com.utils.javenruan.tilerecyclerview.protocal.TileDataSourceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by javenruan on 2016/8/11
 */
public final class TileRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<TileRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "TileRecyclerViewAdapter";

    private Context context;
    private final TileRecyclerView recyclerView;
    private final TileDataSourceAdapter<VH> innerAdapter;

    private int screenWidth;
    private GradientDrawable verticalDividerDrawable;
    private GradientDrawable horizontalDividerDrawable;

    private final Map<Integer, RowInfo> itemsPerRow = new HashMap<>();

    public TileRecyclerViewAdapter(Context context, TileRecyclerView recyclerView, TileDataSourceAdapter<VH> dataSourceAdapter) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.innerAdapter = dataSourceAdapter;
        screenWidth = Utils.getScreenWidth(context);
        verticalDividerDrawable = new GradientDrawable();
        verticalDividerDrawable.setSize(-1, recyclerView.getVerticalSpacing());
        horizontalDividerDrawable = new GradientDrawable();
        horizontalDividerDrawable.setSize(recyclerView.getHorizontalSpacing(), -1);
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override public void onChanged() { // 当数据集出现变化时重新安排行列数据，例如调用notifyDataSetChanged
                recalculateItemsPerRow();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = new LinearLayout(context, null); // 横向的父亲Layout
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        // 设置横向spacing，注意与recyclerView的horizontalSpacing相同大小
        layout.setDividerDrawable(horizontalDividerDrawable);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(TileRecyclerViewAdapter.ViewHolder holder, int position) {
        RowInfo rowInfo = itemsPerRow.get(position); // 每行的数据
        if (rowInfo == null) {
            return;
        }

        List<RowItem> itemsInRow = new ArrayList<>(rowInfo.getItems()); // 获取一份拷贝，防止item被移除
        LinearLayout layout = resetLayout(holder.itemView()); // 重置行布局
        int columnIndex = 0; // 列索引
        int currentIndex = 0; // item索引
        int spaceLeftInColumn = rowInfo.getRowHeight(); // 每一列剩余的空间

        while (!itemsInRow.isEmpty() && columnIndex < recyclerView.getNumColumns()) {
            RowItem currentItem = itemsInRow.get(currentIndex);

            if (spaceLeftInColumn == 0) {
                columnIndex++;
                currentIndex = 0;
                spaceLeftInColumn = rowInfo.getRowHeight();
                continue;
            }

            if (spaceLeftInColumn >= currentItem.getItem().getRowSpan()) { // 竖直方向空间足够
                itemsInRow.remove(currentItem);

                // 处理业务界面
                int actualIndex = currentItem.getIndex();
                int viewType = innerAdapter.getItemViewType(actualIndex);
                VH viewHolder = innerAdapter.onCreateViewHolder(recyclerView, viewType);
                innerAdapter.onBindViewHolder(viewHolder, actualIndex);

                View view = viewHolder.itemView;
                view.setTag(new ViewState(viewType, currentItem, viewHolder));
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);

                spaceLeftInColumn -= currentItem.getItem().getRowSpan();
                currentIndex = 0;

                view.setLayoutParams(new LinearLayout.LayoutParams(getRowWidth(currentItem.getItem().getColumnSpan()),
                        getRowHeight(currentItem.getItem().getRowSpan())));

                LinearLayout childLayout = findOrCreateChildLayout(layout, columnIndex);
                childLayout.addView(view);
            } else if (currentIndex < itemsInRow.size() - 1) {
                currentIndex++; // 尝试下一个item是否合适
            } else {
                break;
            }
        }
    }

    private int getRowWidth(int columnSpan) {
        int rowWidth = recyclerView.getColumnWidth() * columnSpan;
        int horizontalSpacing = (columnSpan - 1) * recyclerView.getHorizontalSpacing(); // 由于跨列越过的水平间隔空间
        return Math.min(rowWidth + horizontalSpacing, screenWidth);
    }

    private int getRowHeight(int rowSpan) {
        int rowHeight = recyclerView.getColumnWidth() * rowSpan;
        int verticalSpacing = (rowSpan - 1) * recyclerView.getVerticalSpacing(); // 由于跨行越过的竖直间隔空间
        return Math.min(rowHeight + verticalSpacing, screenWidth);
    }

    private LinearLayout resetLayout(LinearLayout layout) {
        layout.removeAllViews();
        return layout;
    }

    // 获取或创建一个竖直方向的LinearLayout
    private LinearLayout findOrCreateChildLayout(LinearLayout parent, int columnIndex) {
        LinearLayout childLayout = (LinearLayout) parent.getChildAt(columnIndex);

        if (childLayout == null) {
            childLayout = new LinearLayout(context, null);
            childLayout.setOrientation(LinearLayout.VERTICAL);
            childLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            // 设置竖向spacing，注意与recyclerView的verticalSpacing相同大小
            childLayout.setDividerDrawable(verticalDividerDrawable);
            childLayout.setLayoutParams(new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.MATCH_PARENT));

            parent.addView(childLayout);
        }

        return childLayout;
    }

    private static class ViewState {
        private final int viewType;
        private final RowItem rowItem;
        private final RecyclerView.ViewHolder viewHolder;

        private ViewState(int viewType, RowItem rowItem, RecyclerView.ViewHolder viewHolder) {
            this.viewType = viewType;
            this.rowItem = rowItem;
            this.viewHolder = viewHolder;
        }
    }

    @Override
    public int getItemCount() {
        return itemsPerRow.size();
    }

    // 设置每行的items
    public void recalculateItemsPerRow() {
        long startTime = System.currentTimeMillis();
        itemsPerRow.clear();

        List<RowItem> itemsWrapper = new ArrayList<>(getActualItemCount());
        for (int i = 0, size = getActualItemCount();i < size;i++) {
            itemsWrapper.add(new RowItem(i, getItem(i)));
        }

        List<RowInfo> rowInfos = new ArrayList<>();
        while (!itemsWrapper.isEmpty()) {
            RowInfo rowInfo = calculateItemsForRow(itemsWrapper);
            List<RowItem> itemsThatFit = rowInfo.getItems();

            if (itemsThatFit.isEmpty()) { // protect
                break;
            }

            for (RowItem entry : itemsThatFit) {
                itemsWrapper.remove(entry);
            }

            rowInfos.add(rowInfo);
        }

        for (RowInfo row : rowInfos) {
//            Log.e(TAG, "row position = " +  getItemCount() + " size = " + row.getItems().size());
            itemsPerRow.put(getItemCount(), row);
        }

//        Log.e(TAG, "rowCount = " + getItemCount());
        Log.d(TAG, "recalculateItemsPerRow cost time = " + (System.currentTimeMillis() - startTime));
    }

    private RowInfo calculateItemsForRow(List<RowItem> items) {
        final List<RowItem> itemsThatFit = new ArrayList<>();
        int currentItem = 0;
        int rowHeight = 1;
        float initialSpaceLeft = recyclerView.getNumColumns();
        float areaLeft = initialSpaceLeft;

        while (areaLeft > 0 && currentItem < items.size()) {
            final RowItem item = items.get(currentItem++);
            float itemArea = item.getItem().getRowSpan() * item.getItem().getColumnSpan();


            if (rowHeight < item.getItem().getRowSpan()) { // 某一个item的高度大于行高，重新计算
                itemsThatFit.clear();
                rowHeight = item.getItem().getRowSpan();
                currentItem = 0;
                areaLeft = initialSpaceLeft * item.getItem().getRowSpan();
            } else if (areaLeft >= itemArea) { // 剩余面积足够容纳item
                areaLeft -= itemArea;
                itemsThatFit.add(item);
            } else { // todo 如果允许继续查找，数据量大的时候，表现会很差??
//                break; 为尽可能减少空白，允许继续向下查找合适大小的item
            }
        }

        return new RowInfo(rowHeight, itemsThatFit);
    }

    public int getActualItemCount() {
        return innerAdapter.getItemCount();
    }

    public AbstractDataItem getItem(int position) {
        return innerAdapter.getItem(position);
    }



    @Override
    public void onClick(View v) {
        ViewState rowItem = (ViewState) v.getTag();
        recyclerView.onItemClick(rowItem.rowItem.getIndex(), v);
    }

    @Override
    public boolean onLongClick(View v) {
        ViewState rowItem = (ViewState) v.getTag();
        return recyclerView.onItemLongClick(rowItem.rowItem.getIndex(), v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(LinearLayout itemView) {
            super(itemView);
        }

        LinearLayout itemView() {
            return (LinearLayout) itemView;
        }
    }
}
