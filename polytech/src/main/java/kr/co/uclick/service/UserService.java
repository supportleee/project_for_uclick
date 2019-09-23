package kr.co.uclick.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QUser;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;

	/******* Create / Update *******/
	// 사용자 추가 및 수정
	public void save(User user) {
		userRepo.save(user);
	}

	/******* Read *******/
	// 사용자 id로 사용자 조회
	public Optional<User> findById(Long userId) {
		return userRepo.findById(userId);
	}

	// 사용자 전체 목록 조회
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepo.findAll();
	}

	// 페이징 처리된 사용자 목록 조회
	@Transactional(readOnly = true)
	public Page<User> findAll(int page, int size) {
		// 파라미터로 페이지번호와 목록 크기 받아서 PageRequest를 이용해 값 전달
		return userRepo.findAll(PageRequest.of(page, size));
	}

	// 페이징 처리된 이름 검색
	@Transactional(readOnly = true)
	public Page<User> findUserByNameContaining(String name, int page, int size) {
		// 파라미터로 페이지번호와 목록 크기 받아서 PageRequest를 이용해 값 전달
		return userRepo.findUserByNameContaining(name, PageRequest.of(page, size));
	}

	// 비동기 검색어 자동완성을 위한 이름 전체 조회
	public List<String> findAllName() {
		return userRepo.findAllName();
	}

	/******* Delete *******/
	// 사용자 id를 이용한 사용자 삭제
	public void deleteOne(Long userId) {
		userRepo.deleteById(userId);
	}

	// 선택 삭제에서 넘어온 id값들로 선택된 사용자 전체 삭제
	public void deleteUserByIds(String[] userIds) {
		// string 형식으로 들어온 값들을 Long타입으로 형변환하기
		Long[] long_userIds = new Long[userIds.length];
		for (int i = 0; i < userIds.length; i++) {
			long_userIds[i] = Long.parseLong(userIds[i]);
		}
		// 변환된 id 배열로 순차적으로 하나씩 삭제하기
		for (int i = 0; i < userIds.length; i++) {
			userRepo.deleteById(long_userIds[i]);
		}
	}

// 받아온 id값들로 in 조건을 걸어 일괄 삭제
//	public void deleteUserByIdIn(String[] userIds) {
//		Long[] long_userIds = new Long[userIds.length];
//		for (int i=0; i<userIds.length; i++) {
//			long_userIds[i] = Long.parseLong(userIds[i]);
//		}
//		List<Long> list_userIds = new ArrayList<Long>();
//		for (Long temp : list_userIds) {
//			list_userIds.add(temp);
//		}
//		userRepo.deleteUserByIdIn(list_userIds);
//	}
}
