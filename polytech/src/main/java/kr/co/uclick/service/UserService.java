package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void delete(User user) {
		userRepo.delete(user);
	}
	
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<User> findUserByNameLike(String name) {
		userRepo.findAll(QUser.user.name.eq(name));
		userRepo.logFindByName(name);
		return userRepo.findUserByNameLike(name);
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public Optional<User> findById(Long userId) {
		return userRepo.findById(userId);
	}
}
