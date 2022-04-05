package com.thequizapp.quizalong.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.thequizapp.quizalong.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class CheckableCardView extends CardView
        implements Checkable {

    public CheckableCardView(@NonNull Context context) {
        super(context);
        //init();
    }

    private void init() {
        setCardBackgroundColor(
                ContextCompat.getColorStateList(
                        getContext(),
                        R.color.selector_card_view_colors
                )
        );
    }

    public CheckableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isChecked = false;
    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }



    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked,
    };

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState =
                super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
