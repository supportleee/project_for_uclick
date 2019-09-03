package kr.co.uclick.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.service.SampleService;

@RunWith(SpringRunner.class) // JUnit 테스트 실행방법 확장. 지정한 Runner 클래스 실행
@ContextConfiguration(classes = SpringConfiguration.class) // 자동으로 만들어 줄 application context 설정 파일 위치 지정
public class SpringConfigurationTest {

	@Autowired  // service bean을 찾아가도록 의존성 주입
	SampleService sampleService;

	@Test
	public void test() {
		Sample sample = new Sample();
		sample.setName("park");
		sample.setNumber(10);
		sampleService.save(sample);
	}

}
