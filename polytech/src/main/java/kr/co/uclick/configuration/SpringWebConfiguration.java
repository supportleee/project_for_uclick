package kr.co.uclick.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // 설정 파일임을 명시
@EnableWebMvc // Spring MVC의 설정임을 표시
@ComponentScan("kr.co.uclick.controller") // Spring이 스캔할 controller 지정
public class SpringWebConfiguration implements WebMvcConfigurer {

	// REST-FUL 설정을 위한 메소드
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false); // 확장자로 ContentType을 구분하지 않도록 설정
		configurer.favorParameter(true); // 특정 파라미터로 ContentType을 구분하도록 설정
		configurer.parameterName("mediaType"); // 파라미터 이름을 mediaType으로 지정
		configurer.ignoreAcceptHeader(true); // XML, Json 등 호출하는 URL이 Return값에 따라 변하게 함
		configurer.useJaf(false);
		configurer.mediaType("xml", MediaType.APPLICATION_XML); // xml확장자의 mediaType을 매핑에 추가
		configurer.mediaType("json", MediaType.APPLICATION_JSON); // json확장자의 mediaType을 매핑에 추가
	}

	// HTTP로 접근 시 Controller 앞에 둘 Interceptor 설정 메소드
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 다국어 설정을 위한 interceptor 등록
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	// default servlet 핸들러를 위한 설정 메소드
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// default servlet에 포워딩이 가능하게 함
		configurer.enable();
	}

	// 다국어 설정을 위한 LocalResolver에 사용할 Resolver를 반환하는 메소드
	@Bean
	public LocaleResolver LocaleResolver() {
		return new CookieLocaleResolver();
	}

	// 다국어 설정에서 사용할 메시지 소스 설정 메소드
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("validate-message.properties"); // 메시지 프로퍼티 지정
		return resourceBundleMessageSource;
	}

	// 웹 애플리케이션 WAR 파일 내의 뷰 템플릿을 찾아주는 Resolver
	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/"); // 경로의 접두어 설정
		internalResourceViewResolver.setSuffix(".jsp"); // 경로의 접미어 설정
		return internalResourceViewResolver;
	}
}
