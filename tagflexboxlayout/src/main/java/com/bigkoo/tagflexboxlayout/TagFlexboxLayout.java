package com.bigkoo.tagflexboxlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @Description：流式布局
 * @Author：Sai
 * @Date：2019/3/25 18:30
 */
public class TagFlexboxLayout extends FlexboxLayout implements TagFlexboxAdapter.OnDataChangedListener {
    public static final int MODE_SINGLE = 0;
    public static final int MODE_MULTIPLE = 1;
    private int mode = MODE_SINGLE;
    private TagFlexboxAdapter adapter;
    private OnCheckChangeListener onCheckChangeListener;
    public TagFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagFlexboxLayout);
        mode =typedArray.getColor(R.styleable.TagFlexboxLayout_TagFlexboxLayoutMode, mode);

    }

    public void setAdapter(TagFlexboxAdapter adapter){
        this.adapter = adapter;
        adapter.setOnDataChangedListener(this);
        onAdapterChange();
    }

    private void onAdapterChange() {
        removeAllViews();
        TagView tagViewContainer;
        for(int i =0; i < adapter.getCount(); i++){
            tagViewContainer = new TagView(this.getContext());

            View view = adapter.getView(tagViewContainer,adapter.getItem(i),i);
            //包装一层状态控制ViewGroup
            view.setDuplicateParentStateEnabled(true);
            tagViewContainer.addView(view);

            tagViewContainer.setChecked(adapter.isChecked(i));

            tagViewContainer.setOnClickListener(new TagClickListener(i));
            addView(tagViewContainer);
        }

    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    @Override
    public void onChanged() {
        onAdapterChange();
    }

    public interface OnCheckChangeListener{
        void onCheckChange(boolean isCheck, int position);
    }

    class TagClickListener implements View.OnClickListener {
        private int position;
        public TagClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            TagView tagViewContainer = (TagView) v;
            if(mode == MODE_SINGLE){
                if(tagViewContainer.isChecked())//如果是单选则直接返回了，不需要改变状态
                    return;
                else {
                    adapter.setChecked(position);
                    clearOtherCheckedState(tagViewContainer);
                }
            }
            else if(mode == MODE_MULTIPLE){
                if(tagViewContainer.isChecked())
                    adapter.removeChecked(position);
                else
                    adapter.setChecked(position);
            }
            tagViewContainer.toggle();
            if(onCheckChangeListener != null)
                onCheckChangeListener.onCheckChange(tagViewContainer.isChecked(),position);
        }
    }

    /**
     * 单选模式清空其他View状态
     * @param tagViewContainer
     */
    private void clearOtherCheckedState(TagView tagViewContainer){
        if(mode == MODE_SINGLE){
            Iterator<Integer> it = ((HashSet)adapter.getCheckedList().clone()).iterator();//迭代已经选择的view
            while (it.hasNext()) {
                int position = it.next().intValue();
                TagView otherTagViewContainer = (TagView)getChildAt(position);
                if(tagViewContainer != otherTagViewContainer) {
                    otherTagViewContainer.toggle();
                    adapter.removeChecked(position);
                }
            }
        }
    }

    public void setChecked(Integer ...pos){
        adapter.setChecked(pos);
    }

    public List<Integer> getCheckedPositionList(){
        ArrayList<Integer> positionList = new ArrayList(adapter.getCheckedList());
        Collections.sort(positionList);
        return positionList;
    }

    public List getCheckedItems(){
        ArrayList items = new ArrayList();
        for(Integer p:getCheckedPositionList()){
            items.add(adapter.getItem(p));
        }
        return items;
    }

    public TagFlexboxAdapter getAdapter() {
        return adapter;
    }

    public Object getItem(int position){
        return adapter.getItem(position);
    }
}
