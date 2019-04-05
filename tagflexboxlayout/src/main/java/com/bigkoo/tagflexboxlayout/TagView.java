package com.bigkoo.tagflexboxlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * @Description：描述信息
 * @Author：Sai
 * @Date：2019/3/25 19:15
 */
public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked = false;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return this.getChildAt(0);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (this.isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }

        return states;
    }

    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            this.refreshDrawableState();
        }
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void toggle() {
        this.setChecked(!this.isChecked);
    }
}
