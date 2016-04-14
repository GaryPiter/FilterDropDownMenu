package net.dell.filterdropdownmenu.bean;

/**
 * Created by dell on 2016/4/14.
 */
public class DropdownItem {

    public int id;
    public String text;
    public String value;
    public String suffix;

    public DropdownItem(String text,int id,  String value) {
        this.text = text;
        this.id = id;
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


}
