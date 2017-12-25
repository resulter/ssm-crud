package com.atguigu.crud.service;

import com.atguigu.crud.bean.BaseData;
import com.atguigu.crud.bean.BaseDataExample;
import com.atguigu.crud.bean.BaseDataSearchMap;
import com.atguigu.crud.dao.BaseDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDataService {
	
	@Autowired
	BaseDataMapper  baseDataMapper;

	/**
	 * 查询所有员工
	 * @return
	 */
	public List<BaseData> getAll() {
		// TODO Auto-generated method stub
		return baseDataMapper.selectByExample(null);
	}
/**
	 * 条件查询所有员工
	 * @return
	 */
	public List<BaseData> getConditionAll(String search_campus,String search_address,String search_ip) {
		// TODO Auto-generated method stub
		return baseDataMapper.selectConditionSearchMap(new BaseDataSearchMap(search_campus,search_address,search_ip));
	}

	/**
	 * 员工保存
	 * @param baseData
	 */
	public void saveData(BaseData baseData) {
		// TODO Auto-generated method stub
		baseDataMapper.insertSelective(baseData);
	}

	/**
	 * 检验用户名是否可用
	 * 
	 * @param empName
	 * @return  true：代表当前姓名可用   fasle：不可用
	 */
	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		criteria.andCampusNameEqualTo(empName);
		long count = baseDataMapper.countByExample(example);
		return count == 0;
	}

	/**
	 * 按照员工id查询员工
	 * @param id
	 * @return
	 */
	public BaseData getData(Integer id) {
		// TODO Auto-generated method stub
		BaseData employee = baseDataMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param baseData
	 */
	public void updateData(BaseData baseData) {
		// TODO Auto-generated method stub
		baseDataMapper.updateByPrimaryKeySelective(baseData);
	}

	/**
	 * 员工删除
	 * @param id
	 */
	public void deleteData(Integer id) {
		// TODO Auto-generated method stub
		baseDataMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3)
		criteria.andIdIn(ids);
		baseDataMapper.deleteByExample(example);
	}

}
