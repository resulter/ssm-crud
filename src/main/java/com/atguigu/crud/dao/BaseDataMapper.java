package com.atguigu.crud.dao;

import com.atguigu.crud.bean.BaseData;
import com.atguigu.crud.bean.BaseDataExample;
import java.util.List;

import com.atguigu.crud.bean.BaseDataSearchMap;
import org.apache.ibatis.annotations.Param;

public interface BaseDataMapper {
    int countByExample(BaseDataExample example);

    int deleteByExample(BaseDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BaseData record);

    int insertSelective(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    List<BaseData> selectConditionSearchMap(BaseDataSearchMap searchMap);

    BaseData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BaseData record, @Param("example") BaseDataExample example);

    int updateByExample(@Param("record") BaseData record, @Param("example") BaseDataExample example);

    int updateByPrimaryKeySelective(BaseData record);

    int updateByPrimaryKey(BaseData record);
}