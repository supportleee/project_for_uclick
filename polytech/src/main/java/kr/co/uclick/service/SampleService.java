package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QSample;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.repository.SampleRepository;

@Service // service 계층임을 명시
@Transactional // 각 메소드 transaction 처리 명시
public class SampleService {

	@Autowired // repository bean을 찾아가도록 의존성 주입
	private SampleRepository sampleRepository;

	// delete record 
	public void delete(Sample entity) {
		sampleRepository.delete(entity);
	}

	// table 전체 조회
	@Transactional(readOnly = true) // transaction을 읽기 전용으로 설정
	public List<Sample> findAll() {
		return sampleRepository.findAll();
	}

	// 특정 이름으로 검색했을 때 조회하는 서비스
	@Transactional(readOnly = true) // transaction을 읽기 전용으로 설정
	public List<Sample> findSampleByName(String name) {

		sampleRepository.findAll(QSample.sample.name.eq(name));
		sampleRepository.doSample(name);

		return sampleRepository.findSampleByName(name);
	}
	
	// insert record
	public void save(Sample sample) {
		sampleRepository.save(sample);
	}

	// select one
	public void findById(Long sampleId) {
		sampleRepository.findById(sampleId);
	}
}
