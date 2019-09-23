package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;

public interface PhoneRepository extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone> {
		
	@QueryHints(value = {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})
	public List<Phone> findPhoneByTelContaining(String tel);
	
	public List<Phone> findAllByUserId(Long userId);
	
	// 전화기 추가 시 중복 검사
	@Query(value="SELECT count(tel) FROM phone WHERE tel=?1", nativeQuery=true)
	public int authenticateTelForInsert(String tel);
	
	// 전화기 수정 시 중복 검사
	@Query(value="SELECT count(tel) FROM phone WHERE tel=?1 and id<>?2", nativeQuery=true)
	public int authenticateTelForUpdate(String tel, Long id);
}