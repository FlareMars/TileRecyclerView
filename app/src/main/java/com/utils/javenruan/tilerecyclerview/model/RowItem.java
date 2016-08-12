package com.utils.javenruan.tilerecyclerview.model;

public final class RowItem {
        private final AbstractDataItem item;
        private final int index;

        public RowItem(int index, AbstractDataItem item) {
            this.item = item;
            this.index = index;
        }

        public AbstractDataItem getItem() {
            return item;
        }

        public int getIndex() {
            return index;
        }
}
