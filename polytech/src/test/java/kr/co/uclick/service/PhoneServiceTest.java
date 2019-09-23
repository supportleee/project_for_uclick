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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringConfiguration.class })
public class PhoneServiceTest {

	@Resource
	private UserService userService;

	@Resource
	private PhoneService phoneService;

	// 전화기 추가 테스트
	@Ignore
	public void testSaveforInsert() {
		Phone phone = new Phone();
		User user = userService.findById((long) 1).get();
		phone.setUser(user);
		phone.setType("사무실");
		phone.setTel("01000000001");
		phoneService.save(phone);
	}

	// 전화기 수정 테스트
	// @Test
	public void testSaveforUpdate() {
		Phone phone = phoneService.findById(1L).get();
		phone.setTel("01022222222");
		phoneService.save(phone);
	}

	// 전화기 삭제 테스트
	// @Test
	public void testDelete() {
		phoneService.deleteOne(1L);
	}

	// 전화기 정보로 사용자 정보 조회 테스트
	// @Test
	public void testFindByNumber() {
		// assertEquals(phoneService.findByTel("0000").size(),2);
		assertEquals(phoneService.findPhoneByTelContaining("0000").get(0).getUser().getName(), "ㅇ");
	}

	// 전화기 전체 목록 조회 테스트
	// @Ignore
	// @Test
	public void testFindAll() {
		assertEquals(phoneService.findAll().size(), 1);
	}

	// 전화기 id로 전화기 조회 테스트
	// @Test
	public void testFindById() {
		assertEquals(phoneService.findById(1L).get().getType(), "사무실");
	}

	// 사용자 id로 전화기목록 조회 테스트
	@Test
	public void testFindByUser() {
		assertEquals(phoneService.findAllByUserId(1L).size(), 1);
	}
}
