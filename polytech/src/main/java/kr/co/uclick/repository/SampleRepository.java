package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Sample;

// Service 하위 계층인 Repository
public interface SampleRepository
		extends JpaRepository<Sample, Long>, QuerydslPredicateExecutor<Sample>, CustomSampleRepository {

	// 이름으로 검색하는 메소드
	public List<Sample> findSampleByName(String name);

}
