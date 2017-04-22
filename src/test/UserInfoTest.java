package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onlymvp.dao.UserInfoDAO;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.tool.MyHibernateDaoSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserInfoTest extends MyHibernateDaoSupport {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserInfoDAO userInfoDAO;

	@Test
	public void testQueryByUserName() throws Exception {

		this.userInfoDAO.queryByUserName("zhangsanfeng");

	}

	@Test
	public void testQueryByUserNameAndUserPwd() throws Exception {

		this.userInfoDAO.queryByUserNameAndUserPwd("zhangsanfeng", "111");
	}

	@Test
	public void testDeleteByPropertyId() throws Exception {
		this.userInfoDAO.deleteByPropertyId(100);
	}

	@Test
	public void testQueryCount() throws Exception {

		UserInfoEntity entity = new UserInfoEntity();

		long count = this.userInfoDAO.queryCount(entity);

		logger.info("TEST 查询到的业主列表长度 -> {}", count);
	}

	@Test
	public void testQueryUserInfoList() throws Exception {

		UserInfoEntity entity = new UserInfoEntity();

		int start = 0;

		int size = 20;

		List<UserInfoEntity> list = this.userInfoDAO.queryUserInfoList(entity, start, size);

		logger.info("TEST 查询到的业主列表 -> {}", list);

	}

}
