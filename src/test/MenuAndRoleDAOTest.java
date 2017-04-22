package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onlymvp.dao.MenuAndRoleDAO;
import com.onlymvp.entity.MenuAndRoleEntity;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MenuAndRoleDAOTest extends AbstractJUnit4SpringContextTests {
	
	
	@Resource
	private MenuAndRoleDAO menuAndRoleDAO;
	
	
	@Test
	public void testFindAllByAdminId() throws Exception{
		this.menuAndRoleDAO.findAllByAdminId("1");
	}

	@Test
	public void testInsert() throws Exception{
		
		MenuAndRoleEntity entity = new MenuAndRoleEntity();
		
		entity.setMenuId(100);
		entity.setAdminId(1);
		
		this.menuAndRoleDAO.insert(entity);
	}
	
	@Test
	public void testDeleteByAdminId() throws Exception{
		
		this.menuAndRoleDAO.deleteByAdminId(100);
		
	}
	
}
