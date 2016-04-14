package net.dell.filterdropdownmenu.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import net.dell.filterdropdownmenu.R;
import net.dell.filterdropdownmenu.bean.DropdownItem;

import java.util.LinkedList;
import java.util.List;

/**
 * 选中的下拉选项列表
 * Created by dell on 2016/4/14.
 */
public class DropdownListView extends ScrollView {

    private List<? extends DropdownItem> list;
    private LinearLayout linearLayout;//下拉布局
    public DropdownButton button;
    public DropdownItem current;

    public DropdownListView(Context context) {
        this(context, null);
    }

    public DropdownListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化布局参数
     */
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_list, this, true);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
    }

    /**
     * @param list
     * @param button
     * @param container
     * @param selectedId
     */
    public void bind(List<? extends DropdownItem> list, DropdownButton button,
                     final Container container, int selectedId) {
        current = null;
        this.list = list;
        this.button = button;
        LinkedList<View> cachedDividers = new LinkedList<>();
        LinkedList<DropdownListItemView> cachedViews = new LinkedList<>();

        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                cachedViews.add((DropdownListItemView) view);
            } else {
                cachedDividers.add(view);
            }
        }
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        boolean isFirst = true;
        for (DropdownItem item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                View divider = cachedDividers.poll();
                if (divider == null) {
                    divider = inflater.inflate(R.layout.dropdown_tab_list_divider, linearLayout, false);
                }
                linearLayout.addView(divider);
            }
            DropdownListItemView view = cachedViews.poll();
            if (view == null) {
                view = (DropdownListItemView) inflater.inflate(R.layout.dropdown_tab_list_item, linearLayout, false);
            }
            view.setTag(item);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropdownItem data = (DropdownItem) v.getTag();
                    if (data == null) return;
                    DropdownItem oldOne = current;
                    current = data;
                    flush();
                    container.hide();
                    if (oldOne != current) {
                        container.onSelectionChanged(DropdownListView.this);
                    }
                }
            });
            linearLayout.addView(view);
            if (item.id == selectedId && current == null) {
                current = item;
            }
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == VISIBLE) {
                    container.hide();
                } else {
                    container.show(DropdownListView.this);
                }
            }
        });

        if (current == null && list.size() > 0) {
            current = list.get(0);
        }
        flush();
    }

    /**
     *
     */
    public void flush() {
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                DropdownListItemView itemView = (DropdownListItemView) view;
                DropdownItem data = (DropdownItem) itemView.getTag();
                if (data == null) {
                    return;
                }
                boolean checked = data == current;
                String suffix = data.getSuffix();
                itemView.bind(TextUtils.isEmpty(suffix) ? data.text : data.text + suffix, checked);
                if (checked){
                    button.setText(data.text);
                }
            }
        }
    }

    /**
     * 接口
     */
    public static interface Container {
        void show(DropdownListView listView);

        void hide();

        void onSelectionChanged(DropdownListView view);
    }


}
