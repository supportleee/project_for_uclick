package kr.co.uclick.service;

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
	public Page<User> findAll(int first, int size) {
		return userRepo.findAll(PageRequest.of(first, size));
	}
	
	@Transactional(readOnly = true)
	public Page<User> findUserByNameContaining(String name, int first, int size) {
		return userRepo.findUserByNameContaining(name, PageRequest.of(first, size));
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
	
}
