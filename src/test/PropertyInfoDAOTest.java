package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onlymvp.dao.PropertyInfoDAO;
import com.onlymvp.entity.PropertyInfoEntity;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PropertyInfoDAOTest extends AbstractJUnit4SpringContextTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PropertyInfoDAO propertyInfoDAO;
	
	
	@Test
	public void testQueryById() throws Exception{
		
		this.propertyInfoDAO.queryById(1);
	}
	
	@Test
	public void testUpdate() throws Exception{

		PropertyInfoEntity entity = new PropertyInfoEntity();
		entity.setId(13);
		entity.setArea(66);
		entity.setLayout("三室");
		
		boolean res = this.propertyInfoDAO.update(entity);
		
		logger.info("TEST 更新房产信息 -> {}", res);
		
	}
	
	
	@Test
	public void testQueryCount() throws Exception{
		
		PropertyInfoEntity entity = new PropertyInfoEntity();

//		entity.setAddress("2单元");
		
		long count = this.propertyInfoDAO.queryCount(entity);
		
		
		logger.info("TEST 模糊查询房产总记录数 -> {}", count);
		
	}
	
	
	@Test
	public void testQueryPropertyList() throws Exception{
		PropertyInfoEntity entity = new PropertyInfoEntity();

//		entity.setAddress("2单元");
		
		int start = 0;
		
		int size = 20;
		
		List<PropertyInfoEntity> list = this.propertyInfoDAO.queryPropertyList(entity, start, size);
		
		logger.info("TEST 分页模糊查询房产列表 -> {}", list);
		
	}
	
}
