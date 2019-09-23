package kr.co.uclick.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
	
	public void deleteOne(Long userId) {
		userRepo.deleteById(userId);
	}
	
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Page<User> findAll(int page, int size) {
		return userRepo.findAll(PageRequest.of(page, size));
	}
	
	@Transactional(readOnly = true)
	public Page<User> findUserByNameContaining(String name, int page, int size) {
		return userRepo.findUserByNameContaining(name, PageRequest.of(page, size));
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public Optional<User> findById(Long userId) {
		return userRepo.findById(userId);
	}
	
	public boolean existsById(Long userId) {
		return userRepo.existsById(userId);
	}
	
	
	public void deleteUserByIds(String[] userIds) {
		Long[] long_userIds = new Long[userIds.length];
		for (int i=0; i<userIds.length; i++) {
			long_userIds[i] = Long.parseLong(userIds[i]);
		}
		for(int i=0;i<userIds.length; i++) {
			userRepo.deleteById(long_userIds[i]);
		}
	}
	
	public List<String> findAllName() {
		return userRepo.findAllName();
	}
	

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
