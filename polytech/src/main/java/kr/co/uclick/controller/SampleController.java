package kr.co.uclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.service.SampleService;

@Controller // Controller임을 명시
public class SampleController {

	// Log를 작성하기 위해 logger 생성
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired // service bean을 찾아가도록 의존성 주입
	private SampleService sampleService;

	// list.html 경로에 Get 방식으로 접근했을 경우
	@GetMapping(value = "list.html")
	public String list(Model model) {
		// model 객체에 service의 findAll() 메소드 결과를 담는다.
		model.addAttribute("users", sampleService.findAll());
		return "list"; // list.jsp로 model 객체 전달
	}

	// newForm.html 경로에 Get 방식으로 접근했을 경우
	@GetMapping(value = "newForm.html")
	public String newForm(Model model) {
		return "newForm"; // newForm.jsp로 이동
	}

	// editForm.html 경로에 Get 방식으로 접근했을 경우
	@GetMapping(value = "editForm.html")
	public String editForm(Long sampleId, Model model) {
		sampleService.findById(sampleId); // service에서 sampleId값을 찾기
		return "editForm"; // editForm.jsp로 이동
	}

	// save.html 경로에 Post 방식으로 접근했을 경우
	@PostMapping(value = "save.html")
	public String save(Sample sample, Model model) {
		return "redirect:list.html"; // list.html로 redirect함
	}

	// delete.html 경로에 delete 방식으로 접근했을 경우
	@DeleteMapping(value = "delete.html")
	public String delete(Long sampleId, Model model) {
		return "redirect:list.html"; // list.html로 redirect함
	}

	// sample.html에 get 방식으로 접근했을 경우
	@GetMapping(value = "sample.html")
	public String sample(String name, Sample sample, Model model) {

		logger.debug("sample name : {}", name);
		logger.debug("Sample.name : {}", sample.getName());

		model.addAttribute("samples", sampleService.findAll()); // service에서 findAll() 실행결과를 samples에 저장
		model.addAttribute("sample", sample); // sample을 model에 저장
		model.addAttribute("findSampleByName", sampleService.findSampleByName(name)); // 이름으로 검색한 결과 저장
		return "sample";
	}
}
