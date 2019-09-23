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
	
	public void deleteOne(Long phoneId) {
		Phone phone = phoneRepo.findById(phoneId).get();
		phone.setUser(null);
		phoneRepo.deleteById(phone.getId());
	}
	
	@Transactional(readOnly=true)
	public List<Phone> findAll() {
		return phoneRepo.findAll();
	}
	
	public void save(Phone phone) {
		phoneRepo.save(phone);
	}
	
	public Optional<Phone> findById(Long phoneId) {
		return phoneRepo.findById(phoneId);
	}

	public List<Phone> findPhoneByTelContaining(String tel) {
		return phoneRepo.findPhoneByTelContaining(tel);
	}
	
	public List<Phone> findAllByUserId(Long userId) {
		return phoneRepo.findAllByUserId(userId);
	}
	
	public Page<User> findUserByTel(String tel, int page, int size) {
		List<User> users = new ArrayList<User>();
		for(Phone phone : findPhoneByTelContaining(tel)) {
			users.add(phone.getUser());
		}
		HashSet<User> users_hash = new HashSet<User>(users);
		ArrayList<User> users_deduplication = new ArrayList<User>(users_hash);
		
		Page<User> users_page = new PageImpl(users_deduplication, PageRequest.of(page, size), users_deduplication.size());
		
		return users_page;
	}
	
	public boolean authenticateTel(String tel, Long id) {
		boolean result = false;
		if(id == null) {
			// 전화기 추가
			if(phoneRepo.authenticateTelForInsert(tel) == 0) {
				result = true;
			} else {
				result = false;
			}
		} else {
			// 전화기 수정
			if(phoneRepo.authenticateTelForUpdate(tel, id) == 0) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
} 
