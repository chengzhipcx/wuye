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

import com.onlymvp.dao.ChargeInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ChargeInfoDAOTest extends AbstractJUnit4SpringContextTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ChargeInfoDAO chargeInfoDAO;

	@Test
	public void testQueryPageList() throws Exception {

		int start = 0;
		int size = 20;

		UserAndPropertyAndChargeInfoEntity upc = new UserAndPropertyAndChargeInfoEntity();

		List<UserAndPropertyAndChargeInfoEntity> list = this.chargeInfoDAO.queryPageList(upc, start, size);

		logger.info("TEST 查询到的缴费信息列表 -> {}", list);

	}
	
	@Test
	public void testQueryCount() throws Exception{
		
		UserAndPropertyAndChargeInfoEntity upc = new UserAndPropertyAndChargeInfoEntity();
		
		long count = this.chargeInfoDAO.queryCount(upc);
		
		logger.info("TEST 查询到的记录条数 -> {}", count);
		
	}
	
	@Test
	public void testUpdateChargeInfo() throws Exception{
		
		UserAndPropertyAndChargeInfoEntity entity = new UserAndPropertyAndChargeInfoEntity();
		
		entity.setChargeInfoId(1);
		entity.setPayment(1000);
		entity.setRemark("备注测试");
		
		this.chargeInfoDAO.updateChargeInfo(entity);
	}

}
