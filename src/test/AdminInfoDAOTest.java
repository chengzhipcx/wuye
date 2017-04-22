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

import com.onlymvp.dao.AdminInfoDAO;
import com.onlymvp.entity.AdminInfoEntity;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AdminInfoDAOTest extends AbstractJUnit4SpringContextTests{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private AdminInfoDAO adminInfoDAO;
	
	@Test
	public void testUpdateAdminPassword() throws Exception{
		Integer id = 42;
		
		String userPwd = "111";
		
		this.adminInfoDAO.updateAdminPassword(id, userPwd);
	}
	
	
	@Test
	public void testQueryByNameAndPwd() throws Exception{
		
		String userName = "admin";
		
		String userPwd = "admin";
		
		AdminInfoEntity entity = this.adminInfoDAO.queryByNameAndPwd(userName, userPwd);
		
		logger.info("测试登录 -> {}", entity);
		
	}
	
	@Test
	public void testQueryNormalAdminList() throws Exception{
		
		int start = 0;
		
		int size = 10;
		
		List<AdminInfoEntity> list = this.adminInfoDAO.queryNormalAdminList(start, size);
		
		logger.info("测试查询普通管理员列表 -> {}", list);
		
	}
	
	@Test
	public void testQueryById() throws Exception{
		
		int adminId = 1;
		
		AdminInfoEntity entity = this.adminInfoDAO.queryById(adminId);
		
		logger.info("通过ID查询到的管理员 -> {}", entity);
		
	}
	
	@Test
	public void testQueryNormalAdminCount() throws Exception{
		
		long count = this.adminInfoDAO.queryNormalAdminCount();
		
		logger.info("测试查询到普通管理员列表总条数 -> {}", count);
		
	}

}
