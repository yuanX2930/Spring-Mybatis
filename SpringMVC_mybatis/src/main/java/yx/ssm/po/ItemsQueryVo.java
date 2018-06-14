package yx.ssm.po;

import java.util.List;

/**
 * 商品包装对象
 * @author Administrator
 *
 */
public class ItemsQueryVo {
    //商品信息
    private Items item;

    //为了系统的可扩展性，对原始生成的po进行扩展
    private ItemsCustom itemsCustom;

    //批量商品修改的接受
    private List<ItemsCustom> itemsList;


    public List<ItemsCustom> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemsCustom> itemsList) {
        this.itemsList = itemsList;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public ItemsCustom getItemsCustom() {
        return itemsCustom;
    }

    public void setItemsCustom(ItemsCustom itemsCustom) {
        this.itemsCustom = itemsCustom;
    }

}

