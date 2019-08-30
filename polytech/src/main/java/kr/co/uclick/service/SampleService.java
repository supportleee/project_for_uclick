package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QSample;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.repository.SampleRepository;

@Service
@Transactional
public class SampleService {

	@Autowired
	private SampleRepository sampleRepository;

	public void delete(Sample entity) {
		sampleRepository.delete(entity);
	}

	@Transactional(readOnly = true)
	public List<Sample> findAll() {
		return sampleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Sample> findSampleByName(String name) {

		sampleRepository.findAll(QSample.sample.name.eq(name));
		sampleRepository.doSample(name);

		return sampleRepository.findSampleByName(name);
	}

	public void save(Sample sample) {
		sampleRepository.save(sample);
	}

	public void findById(Long sampleId) {
		sampleRepository.findById(sampleId);
	}
}
