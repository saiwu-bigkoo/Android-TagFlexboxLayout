package com.bigkoo.tagflexboxlayout;

import android.view.View;

import java.util.HashSet;
import java.util.List;

/**
 * @Description：描述信息
 * @Author：Sai
 * @Date：2019/3/25 18:44
 */
public abstract class TagFlexboxAdapter <T> {
    private List<T> datas;
    private TagFlexboxAdapter.OnDataChangedListener onDataChangedListener;
    private HashSet<Integer> checkedList = new HashSet();

    public TagFlexboxAdapter(List<T> datas) {
        this.datas = datas;
    }

    /**
     * 初始化adapter
     * @param datas 内容
     * @param pos 已选中的position
     */
    public TagFlexboxAdapter(List<T> datas, Integer... pos) {
        this.datas = datas;
        setChecked(pos);
    }

    void setOnDataChangedListener(TagFlexboxAdapter.OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public void setChecked(Integer... pos) {
        for (Integer p : pos){
            checkedList.add(p);
        }

    }

    public boolean isChecked(int position){
        return checkedList.contains(position);
    }

    public int getCount() {
        return this.datas == null ? 0 : this.datas.size();
    }

    public void notifyDataChanged() {
        if (this.onDataChangedListener != null) {
            this.onDataChangedListener.onChanged();
        }

    }

    public T getItem(int position) {
        return this.datas.get(position);
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    public HashSet<Integer> getCheckedList() {
        return checkedList;
    }

    public abstract View getView(TagView parent,T item, int position);

    public void setCheckedList(HashSet<Integer> checkedList) {
        this.checkedList = checkedList;
    }


    public boolean removeChecked(Integer pos){
        boolean b = checkedList.remove(pos);
        return b;
    }

}
