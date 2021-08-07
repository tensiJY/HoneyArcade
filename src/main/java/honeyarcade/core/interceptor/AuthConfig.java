package honeyarcade.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class AuthConfig implements WebMvcConfigurer {
//	
//	@Autowired
//	private SessionInterceptor sessionInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// TODO Auto-generated method stub
//		//	WebMvcConfigurer.super.addInterceptors(registry);
//		registry.addInterceptor(sessionInterceptor)
//				.addPathPatterns("/**")
//				.excludePathPatterns("/css/**", "/js/**", "/img/**", "/scss/**"); // 정적 리소스 파일은 제외. 제외하지 않으면 에러 발생
//				
//        		// 바로 윗 줄을 추가하지 안으면
//        		// because its mime type 'text/html' is not executable and strict mime type checking is enabled. 에러 발생
//		
//	}
//
//}
