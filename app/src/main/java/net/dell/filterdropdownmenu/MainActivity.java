package net.dell.filterdropdownmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import net.dell.filterdropdownmenu.bean.DropdownItem;
import net.dell.filterdropdownmenu.bean.TopicLabelObject;
import net.dell.filterdropdownmenu.view.DropdownButton;
import net.dell.filterdropdownmenu.view.DropdownListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ID_TYPE_ALL = 0;
    private static final int ID_TYPE_MY = 1;
    private static final int ID_LABEL_ALL = -1;
    private static final int ID_ORDER_REPLY_TIME = 51;
    private static final int ID_ORDER_PUBLISH_TIME = 49;
    private static final int ID_ORDER_HOT = 53;

    private ListView listView;
    private View maskTop;
    /**
     * 点击的tab与之对应的下拉布局
     */
    private DropdownButton chooseType, chooseLabel, chooseOrder;
    private DropdownListView dropdownType, dropdownLabel, dropdownOrder;
    private Animation dropdown_in, dropdown_out, dropdown_mask_out;
    //标签集合
    private List<TopicLabelObject> labels = new ArrayList<>();
    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        //显示下拉的布局
        maskTop = findViewById(R.id.mask);
        //三个选项卡
        chooseType = (DropdownButton) findViewById(R.id.chooseType);
        chooseLabel = (DropdownButton) findViewById(R.id.chooseLabel);
        chooseOrder = (DropdownButton) findViewById(R.id.chooseOrder);
        //三个listView布局
        dropdownType = (DropdownListView) findViewById(R.id.dropdownType);
        dropdownLabel = (DropdownListView) findViewById(R.id.dropdownLabel);
        dropdownOrder = (DropdownListView) findViewById(R.id.dropdownOrder);

        //设置下拉动画
        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_mask_out);

        dropdownButtonsController.init();
        //添加标签数据
        TopicLabelObject topicLabelObject1 =  new TopicLabelObject(1,1,"Fragment");
        labels.add(topicLabelObject1);
        TopicLabelObject topicLabelObject2 =new TopicLabelObject(2,1,"CustomView");
        labels.add(topicLabelObject2);
        TopicLabelObject topicLabelObject3 =new TopicLabelObject(2,1,"Service");
        labels.add(topicLabelObject3);
        TopicLabelObject topicLabelObject4 =new TopicLabelObject(2,1,"BroadcastReceiver");
        labels.add(topicLabelObject4);
        TopicLabelObject topicLabelObject5 =new TopicLabelObject(2,1,"Activity");
        labels.add(topicLabelObject5);
        //添加点击事件，恢复初始状态
        maskTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });
        dropdownButtonsController.flushCounts();
        dropdownButtonsController.flushAllLabels();
        dropdownButtonsController.flushMyLabels();
    }


    /**
     * 内部类实现接口：
     */
    public class DropdownButtonsController implements DropdownListView.Container{

        private DropdownListView currentDropdownList;
        //全部讨论
        private List<DropdownItem> datasetType=new ArrayList<>();
        //全部标签
        private List<DropdownItem> datasetAllLabel=new ArrayList<>();
        //评论排序
        private List<DropdownItem> datasetOrder=new ArrayList<>();
        //我的标签
        private List<DropdownItem> datasetMyLabel=new ArrayList<>();
        //标签集合-（默认是全部标签）
        private List<DropdownItem> datasetLabel = datasetAllLabel;

        @Override
        public void show(DropdownListView listView) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
            }
            //布局为空就显示列表菜单
            currentDropdownList = listView;
            maskTop.clearAnimation();
            maskTop.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                maskTop.clearAnimation();
                maskTop.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            if (view == dropdownType) {
                updateLabels(getCurrentLabels());
            }
        }

        private void updateLabels(List<DropdownItem> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetLabel = targetList;
                dropdownLabel.bind(datasetLabel, chooseLabel, this, dropdownLabel.current.id);
            }
        }

        private List<DropdownItem> getCurrentLabels() {
            return dropdownType.current != null && dropdownType.current.id == ID_TYPE_MY ? datasetMyLabel : datasetAllLabel;
        }

        private void reset() {
            chooseType.setChecked(false);
            chooseLabel.setChecked(false);
            chooseOrder.setChecked(false);

            dropdownType.setVisibility(View.GONE);
            dropdownLabel.setVisibility(View.GONE);
            dropdownOrder.setVisibility(View.GONE);
            maskTop.setVisibility(View.GONE);

            dropdownType.clearAnimation();
            dropdownLabel.clearAnimation();
            dropdownOrder.clearAnimation();
            maskTop.clearAnimation();
        }

        private void init() {
            reset();
            datasetType.add(new DropdownItem("全部讨论", ID_TYPE_ALL, "all"));
            datasetType.add(new DropdownItem("我的讨论", ID_TYPE_MY, "my"));
            dropdownType.bind(datasetType, chooseType, this, ID_TYPE_ALL);

            datasetAllLabel.add(new DropdownItem("全部标签", ID_LABEL_ALL, null) {
                @Override
                public String getSuffix() {
                    return dropdownType.current == null ? "" : dropdownType.current.getSuffix();
                }
            });
            datasetMyLabel.add(new DropdownItem("全部标签", ID_LABEL_ALL, null));
            datasetLabel = datasetAllLabel;
            dropdownLabel.bind(datasetLabel, chooseLabel, this, ID_LABEL_ALL);
            datasetOrder.add(new DropdownItem("最后评论排序", ID_ORDER_REPLY_TIME, "51"));
            datasetOrder.add(new DropdownItem("发布时间排序", ID_ORDER_PUBLISH_TIME, "49"));
            datasetOrder.add(new DropdownItem("热门排序", ID_ORDER_HOT, "53"));
            dropdownOrder.bind(datasetOrder, chooseOrder, this, ID_ORDER_REPLY_TIME);

            dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentDropdownList == null) {
                        reset();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        /**
         * 设置全部讨论选项
         */
        public void flushCounts() {
            datasetType.get(ID_TYPE_ALL).setSuffix(" (" + "5" + ")");
            datasetType.get(ID_TYPE_MY).setSuffix(" (" + "3" + ")");
            dropdownType.flush();
            dropdownLabel.flush();
        }

        /**
         * 设置全部标签选项
         */
        public void flushAllLabels() {
            flushLabels(datasetAllLabel);
        }

        public void flushMyLabels() {
            flushLabels(datasetMyLabel);
        }

        private void flushLabels(List<DropdownItem> targetList) {
            while (targetList.size() > 1) targetList.remove(targetList.size() - 1);
            for (int i = 0, n = 5; i < n; i++) {
                int id = labels.get(i).getId();
                String name = labels.get(i).getName();
                if (TextUtils.isEmpty(name)) continue;
                int topicsCount = labels.get(i).getCount();
                // 只有all才做0数量过滤，因为my的返回数据总是0
                if (topicsCount == 0 && targetList == datasetAllLabel) continue;
                DropdownItem item = new DropdownItem(name, id, String.valueOf(id));
                if (targetList == datasetAllLabel){
                    item.setSuffix("(4)");
                }
                targetList.add(item);
            }
            updateLabels(targetList);
        }

    }//end class


}
