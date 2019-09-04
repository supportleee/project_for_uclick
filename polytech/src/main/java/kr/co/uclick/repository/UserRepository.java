package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;

public interface UserRepository
		extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomUserRepository {

	public List<User> findUserByNameLike(String name);
}
