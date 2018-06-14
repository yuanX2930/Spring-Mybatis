package yx.ssm.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yx.ssm.exception.CustomException;
import yx.ssm.mapper.ItemsMapper;
import yx.ssm.mapper.ItemsMapperCustom;
import yx.ssm.po.Items;
import yx.ssm.po.ItemsCustom;
import yx.ssm.po.ItemsQueryVo;
import yx.ssm.servic.ItemsServic;

import java.util.List;

public class ItemsServiceImpl implements ItemsServic {
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<ItemsCustom> findItemslist(ItemsQueryVo itemsQueryVo) throws Exception {
        //通过ItemsMapperCustom查询数据库
        System.out.println("ItemsMapperCustom");
        return itemsMapperCustom.findItemslist(itemsQueryVo);
    }

    @Override
    public ItemsCustom findItemsById(Integer id) throws Exception {
        System.out.println(id);

        Items items=itemsMapper.selectByPrimaryKey(id);
        //中间进行其他的业务处理包装，最后返回包装类ItemsCustom

        if(items==null){
            throw new CustomException("修改的商品信息不存在!");
        }

        ItemsCustom itemsCustom=new ItemsCustom();
        //将items的属性值拷贝到itemsCustom中
        BeanUtils.copyProperties(items, itemsCustom);

        return itemsCustom;
    }

    @Override
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
        //添加业务校验，通常在service中对关键参数进行校验
        //校验id是否为空，如果为空抛出异常


        //跟新商品信息使用updateByPrimaryKeyWithBLOBs根据id跟新items表中所有字段
        //要求必须传入id
        itemsCustom.setId(id);
        itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);


    }


}