package net.dell.filterdropdownmenu.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dell.filterdropdownmenu.R;

/**
 * 标题选项分类卡
 * Created by dell on 2016/4/14.
 */
public class DropdownButton extends RelativeLayout {

    private TextView tabTitle;//tab标题
    private View bottomLine;//下划线标识是否选择

    public void setText(CharSequence text) {
        tabTitle.setText(text);
    }

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化布局参数
     */
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_button, this, true);
        tabTitle=(TextView)view.findViewById(R.id.tv_tab_title);
        bottomLine=view.findViewById(R.id.bottomLine);
    }

    /**
     * 是否选中的状态显示
     */
    public void setChecked(boolean checked){
        Drawable icon;
        if (checked){
            icon=getResources().getDrawable(R.mipmap.ic_dropdown_actived);
            tabTitle.setTextColor(Color.GREEN);
            bottomLine.setVisibility(View.VISIBLE);
        }else{
            icon=getResources().getDrawable(R.mipmap.ic_dropdown_normal);
            tabTitle.setTextColor(Color.BLACK);
            bottomLine.setVisibility(View.GONE);
        }
        tabTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }
}
