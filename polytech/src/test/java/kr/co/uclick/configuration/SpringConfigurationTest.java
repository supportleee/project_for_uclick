package kr.co.uclick.configuration;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.SampleService;
import kr.co.uclick.service.UserService;

@RunWith(SpringRunner.class) // JUnit 테스트 실행방법 확장. 지정한 Runner 클래스 실행
@ContextConfiguration(classes = SpringConfiguration.class) // 자동으로 만들어 줄 application context 설정 파일 위치 지정
public class SpringConfigurationTest {

	@Autowired  // service bean을 찾아가도록 의존성 주입
	//SampleService sampleService;
	UserService userService;

	@Test
	public void testSaveforInsert() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user = new User();
		user.setName("이지원");
		//user.setReg_date(null);
		userService.save(user);
	}

	@Test
	public void testFindAll() {
		assertEquals(userService.findAll().size(), 1);
	}
	
	@Ignore
	public void testFindById() {
		assertEquals(userService.findById((long)4).get().getName(), "지원2");
	}
	
	@Ignore
	public void testFindByName() {
		assertEquals(userService.findUserByNameLike("%지원%").size(),4);
	}
	
	@Ignore
	public void testSaveforUpdate() {
		User user = userService.findById((long)4).get();
		
		user.setName("지원이");
		userService.save(user);
	}
	
	@Ignore
	public void testDelete() {
		User user = new User();
		user.setId((long) 6);
		userService.delete(user);
	}
	
	
}
