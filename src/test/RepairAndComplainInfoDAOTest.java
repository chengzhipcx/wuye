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

import com.onlymvp.dao.RepairAndComplainInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.tool.Const;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RepairAndComplainInfoDAOTest extends AbstractJUnit4SpringContextTests {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RepairAndComplainInfoDAO repairAndComplainInfoDAO;

	@Test
	public void testQueryPageList() throws Exception {

		UserAndPropertyAndReapirAndComplainInfoEntity uprc = new UserAndPropertyAndReapirAndComplainInfoEntity();

		
		uprc.setInfoType(Const.REPAIR_AND_COMPLAIN_INFO_TYPE_REPAIR);
		
		int start = 0;

		int size = 20;

		List<UserAndPropertyAndReapirAndComplainInfoEntity> list = this.repairAndComplainInfoDAO.queryPageList(uprc,
				start, size);
		
		logger.info("TEST 查询到的数据 -> {}", list);

	}
	
	@Test
	public void testQueryCount() throws Exception{

		UserAndPropertyAndReapirAndComplainInfoEntity uprc = new UserAndPropertyAndReapirAndComplainInfoEntity();
		
		uprc.setInfoType(Const.REPAIR_AND_COMPLAIN_INFO_TYPE_REPAIR);
		
		long count = this.repairAndComplainInfoDAO.queryCount(uprc);
		
		logger.info("TEST 查询到的记录条数 -> {}", count);
	}

}
