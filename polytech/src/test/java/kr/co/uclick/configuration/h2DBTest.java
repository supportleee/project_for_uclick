package kr.co.uclick.configuration;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
//@Transactional
public class h2DBTest {
	
	@Resource
	private UserRepository userRepository;
	
	@Ignore
	public void testSaveforInsert() {
		//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user = new User();
		user.setName("이지원");
		user.setDepartment("aaa");
		user.setTeam("sss");
		user.setRank("A");
		user.setAge(24);
		user.setEmail("aa");
		//user.setReg_date(null);
		userRepository.save(user);
	}
	
	@Test
	public void testFindAll() {
		assertEquals(userRepository.findAll().size(), 2);
	}
	
	@Test
	public void testFindById() {
		assertEquals(userRepository.findById((long)2).get().getName(), "ddd");
	}
	
	@Ignore
	public void testSaveforUpdate() {
		User user = userRepository.findById((long)2).get();
		
		user.setName("ddd");
		userRepository.save(user);
	}
	
	@Test
	public void testDelete() {
		User user = new User();
		user.setId((long) 3);
		userRepository.delete(user);
	}
	

}
