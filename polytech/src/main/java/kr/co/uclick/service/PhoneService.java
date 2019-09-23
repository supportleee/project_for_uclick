package kr.co.uclick.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.PhoneRepository;

@Service
@Transactional
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepo;

	/******* Create / Update *******/
	// 전화기 추가/수정
	public void save(Phone phone) {
		phoneRepo.save(phone);
	}

	/******* Read *******/
	// id로 전화기 검색
	public Optional<Phone> findById(Long phoneId) {
		return phoneRepo.findById(phoneId);
	}

	// 전화번호를 포함하는 전화기 검색(like 검색)
	public List<Phone> findPhoneByTelContaining(String tel) {
		return phoneRepo.findPhoneByTelContaining(tel);
	}

	// 사용자 id로 전화기 검색
	public List<Phone> findAllByUserId(Long userId) {
		return phoneRepo.findAllByUserId(userId);
	}

	// 전화번호로 사용자 검색
	public Page<User> findUserByTel(String tel, int page, int size) {
		// 사용자를 담을 list 객체
		List<User> users = new ArrayList<User>();
		// 전화번호로 검색한 전화기 객체를 사용자 객체에 넣기
		for (Phone phone : findPhoneByTelContaining(tel)) {
			users.add(phone.getUser());
		}

		// 중복 add된 list 중복 제거
		HashSet<User> users_hash = new HashSet<User>(users);
		ArrayList<User> users_deduplication = new ArrayList<User>(users_hash);

		// 전화번호로 검색된 사용자를 Page객체로 생성
		Page<User> users_page = new PageImpl(users_deduplication, PageRequest.of(page, size),
				users_deduplication.size());

		return users_page;
	}

	// 전화기 전체 목록 조회
	@Transactional(readOnly = true)
	public List<Phone> findAll() {
		return phoneRepo.findAll();
	}

	/******* Delete *******/
	// 전화기 id를 이용한 전화기 삭제
	public void deleteOne(Long phoneId) {
		Phone phone = phoneRepo.findById(phoneId).get();
		phone.setUser(null); // 전화기의 User를 null로 세팅
		phoneRepo.deleteById(phone.getId());
	}

	/******* 전화번호 중복 검사 *******/
	public boolean authenticateTel(String tel, Long id) {
		boolean result = false;
		if (id == null) { // 넘어온 값에 id가 없을 경우
			// 전화기 추가
			if (phoneRepo.authenticateTelForInsert(tel) == 0) {
				result = true;
			} else {
				result = false;
			}
		} else { // 넘어온 값에 id가 있을 경우
			// 전화기 수정
			if (phoneRepo.authenticateTelForUpdate(tel, id) == 0) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
}
