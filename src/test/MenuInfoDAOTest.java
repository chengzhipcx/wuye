package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onlymvp.dao.MenuInfoDAO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MenuInfoDAOTest extends AbstractJUnit4SpringContextTests {
	
	
	@Resource
	private MenuInfoDAO menuInfoDAO;
	
	@Test
	public void testFindParentById() throws Exception{
		
		this.menuInfoDAO.findParentById("1");
	}
	
	@Test
	public void testFindChildByParentId() throws Exception{
		
		this.menuInfoDAO.findChildByParentId("1");
	}
	
	@Test
	public void testFindAllNormalParent() throws Exception{
		
		this.menuInfoDAO.findAllNormalParent();
	}
	
}
