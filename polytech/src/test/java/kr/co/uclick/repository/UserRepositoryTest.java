package kr.co.uclick.repository;

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

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.*;
import kr.co.uclick.repository.PhoneRepository;
import kr.co.uclick.repository.UserRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
//@Transactional
public class UserRepositoryTest {
	
	@Resource
	private UserRepository userRepo;
	
	@Resource
	private PhoneRepository phoneRepo;
	
	// 사용자 추가 테스트
	@Ignore
	public void testSaveforInsert() {
		User user = new User();
		user.setName("이지원");
		user.setDepartment("aaa");
		user.setTeam("sss");
		user.setRank("A");
		user.setAge(24);
		user.setEmail("aa");
		userRepo.save(user);
	}
	
	// 사용자 리스트 전체 조회 테스트
	@Ignore
	public void testFindAll() {
		assertEquals(userRepo.findAll().size(), 2);
	}
	
	// id값으로 사용자 조회 테스트
	@Ignore
	public void testFindById() {
		assertEquals(userRepo.findById((long)2).get().getName(), "ddd");
	}
	
	// 사용자 정보 수정 테스트
	@Ignore
	public void testSaveforUpdate() {
		User user = userRepo.findById((long)2).get();
		user.setName("ddd");
		userRepo.save(user);
	}
	
	// 사용자 삭제 테스트
	@Test
	public void testDelete() {
		User user = new User();
		user.setId((long) 2);
		userRepo.delete(user);
	}

}
