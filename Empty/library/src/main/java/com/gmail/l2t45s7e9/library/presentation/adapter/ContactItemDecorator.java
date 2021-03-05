package com.gmail.l2t45s7e9.library.presentation.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.library.R;
public class ContactItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;
    private int childCount;
    private int left;
    private int top;
    private int bottom;
    private int right;
    private Drawable divider;

    public ContactItemDecorator(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight * 2;
        }
        outRect.bottom = verticalSpaceHeight * 2;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        divider = ContextCompat.getDrawable(parent.getContext(), R.drawable.end_line);
        childCount = parent.getChildCount();
        left = parent.getPaddingLeft() + parent.getWidth() / 6;
        right = parent.getWidth() - left;
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            top = child.getBottom() + verticalSpaceHeight;
            bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

}
