package kr.co.uclick.repository;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class PhoneRepositoryTest {
	@Resource
	private PhoneRepository phoneRepo;
	
	@Resource
	private UserRepository userRepo;
	
	@Ignore
	public void testSaveforInsert() {
		Phone phone = new Phone();
		User user = userRepo.findById((long)2).get();
		phone.setUser(user);
		phone.setType("사무실");
		phone.setTel("010-0000-0000");
		phoneRepo.save(phone);
	}
	
	@Test
	public void testFindByNumber() {
		
	}
}
