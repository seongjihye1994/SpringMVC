package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 이 서블릿 컴포넌트 스캔이 위치한 패키지부터 하위 경로를 스캔해서
// 서블릿을 모두 찾아서 자동으로 서블릿을 등록해준다.
@ServletComponentScan // 서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

	// 스프링 부트가 자동으로 아래처럼 뷰리졸버를 등록해준다.
	// 그래서 굳이 수동으로 등록해주지 않아도 된다.
	/*
	@Bean
	ViewResolver internalResourceViewResolver (){
		return new InternalResourceViewResolver("/WEB-INF/views", ".jsp");
	}*/

}
