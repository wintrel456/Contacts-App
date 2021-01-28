package com.gmail.l2t45s7e9.empty.presentation.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
public class ContactItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

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
        Drawable divider = ContextCompat.getDrawable(parent.getContext(), R.drawable.end_line);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + parent.getWidth() / 6;
        int right = parent.getWidth() - left;
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom() + verticalSpaceHeight;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }

    }

}
