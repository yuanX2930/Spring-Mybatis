package yx.ssm.mapper;

import java.util.List;
import yx.ssm.po.ItemsQueryVo;
import  yx.ssm.po.ItemsCustom;

public interface ItemsMapperCustom {
	//商品列表查询
	public List<ItemsCustom> findItemslist(ItemsQueryVo itemsQueryVo) throws Exception;
  
}