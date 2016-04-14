package net.dell.filterdropdownmenu.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import net.dell.filterdropdownmenu.R;

/**
 * 下拉列表的被选中的item
 * Created by dell on 2016/4/14.
 */
public class DropdownListItemView extends TextView {

    public DropdownListItemView(Context context) {
        this(context, null);
    }

    public DropdownListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(CharSequence text, boolean checked) {
        setText(text);
        if (checked) {
            Drawable icon = getResources().getDrawable(R.mipmap.ic_task_status_list_check);
            setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }
}
