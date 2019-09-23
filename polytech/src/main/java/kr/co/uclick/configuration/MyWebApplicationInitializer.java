package kr.co.uclick.configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

// WAS(tomcat)가 시작될 때 필요한 설정들이 시작되는 곳 = web.xml을 자바클래스 형태로 설정한 것
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	// WAS가 시작될 때 제일 먼저 실행되는 메소드
	@Override
	public void onStartup(ServletContext servletCxt) {

		// Create the 'root' Spring application context
		// SpringConfiguration 클래스에 Annotation을 다는 효과
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class); // root-Context.xml과 동일한 효과

		// Manage the lifecycle of the root application context
		servletCxt.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		// Servlet-Context.xml과 동일
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringWebConfiguration.class);

		// Register and map the dispatcher servlet
		// dispatcherServlet : HTTP 프로토콜을 통해 들어오는 모든 요청을 계층 제일 앞에서 처리해주는 Front Controller
		ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("politech",
				new DispatcherServlet(dispatcherContext)); // 위에서 등록한 Servlet을 사용
		dispatcher.setLoadOnStartup(1); // 이 Servlet의 우선순위를 1로 세팅
		dispatcher.addMapping("/"); // 이 servlet이 매핑할 URL pattern 설정

		// 한글처리를 위한 filter 설정
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8"); // 필터를 UTF-8로 설정
		characterEncodingFilter.setForceEncoding(true); // 다른 설정이 되어 있어도 강제로 Encoding하도록 설정
		
		// servlet설정에 한글필터 추가
		servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false,
				"/*");
		
		// deleteMapping을 사용하기 위한 필터 추가
		servletCxt.addFilter("httpMethodFilter", HiddenHttpMethodFilter.class)
		.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
	}

}
