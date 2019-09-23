package kr.co.uclick.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringConfiguration.class })
public class UserServiceTest {
	@Resource
	private UserService userService;

	// 사용자 추가 테스트
	// @Ignore
	// @Test
	public void testSaveforInsert() {
		User user = new User();
		user.setName("이");
		user.setDepartment("aaa");
		user.setTeam("sss");
		user.setRank("A");
		user.setAge(24);
		user.setEmail("aa");
		userService.save(user);
	}

	// 사용자 수정 테스트
	@Ignore
	public void testSaveforUpdate() {
		User user = userService.findById(2L).get();
		user.setName("ddd");
		userService.save(user);
	}

	// 사용자 삭제 테스트
	@Ignore
	// @Test
	public void testDelete() {
		userService.deleteOne(1L);
	}

	// 이름 검색 테스트
	@Ignore
	// @Test
	public void testFindByNameContaining() {
		assertEquals(userService.findUserByNameContaining("지", 1, 5).getSize(), 3);
	}

	// 사용자 전체 조회 테스트
	@Ignore
	// @Test
	public void testFindAll() {
		assertEquals(userService.findAll(1, 5).getSize(), 4);
	}

	// 사용자 id로 사용자 조회 테스트
	// @Ignore
	// @Test
	public void testFindById() {
		assertEquals(userService.findById(1L).get().getName(), "이");
	}

}
