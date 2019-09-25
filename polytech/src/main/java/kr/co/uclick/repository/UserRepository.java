package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// 이름으로 사용자 검색
	// query cache 적용
	@QueryHints(value = { 
			@QueryHint(name = "org.hibernate.cacheable", value = "true"),
			@QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL") 
			})
	public Page<User> findUserByNameContaining(String name, Pageable pageable);

	// 비동기 검색어 자동완성을 위한 이름 목록찾기
	// native query 사용
	@Query(value = "SELECT name FROM user", nativeQuery = true)
	public List<String> findAllName();
	
}
