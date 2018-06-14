package yx.ssm.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import yx.ssm.po.Uer;
import yx.ssm.po.UerExample;

public interface UerMapper {
    int countByExample(UerExample example);

    int deleteByExample(UerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Uer record);

    int insertSelective(Uer record);

    List<Uer> selectByExample(UerExample example);

    Uer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Uer record, @Param("example") UerExample example);

    int updateByExample(@Param("record") Uer record, @Param("example") UerExample example);

    int updateByPrimaryKeySelective(Uer record);

    int updateByPrimaryKey(Uer record);
}