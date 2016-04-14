package net.dell.filterdropdownmenu.bean;

/**
 * 标签实体类
 * Created by dell on 2016/4/14.
 */
public class TopicLabelObject {

    public int id;
    public int count;
    public String name;

    public TopicLabelObject(int id, int count, String name) {
        this.id = id;
        this.count = count;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
