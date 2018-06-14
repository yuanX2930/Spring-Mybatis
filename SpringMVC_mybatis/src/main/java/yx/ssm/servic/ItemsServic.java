package yx.ssm.servic;

import java.util.List;

import yx.ssm.po.ItemsCustom;
import yx.ssm.po.ItemsQueryVo;

public interface ItemsServic {
    //商品列表查询
    public List<ItemsCustom> findItemslist(ItemsQueryVo itemsQueryVo) throws Exception;

    //根据id查询商品信息
    public ItemsCustom findItemsById(Integer id)throws Exception;

    //修改商品信息
    public void updateItems(Integer id,ItemsCustom itemsCustom)throws Exception;
}
