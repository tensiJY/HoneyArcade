package honeyarcade.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider();
    }
    
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customAuthenticationProvider());
	}

	
	
    @Bean
	public CustomFailureHandler customFailureHandler() {
		return new CustomFailureHandler();
	};
	
	@Bean
	public CustomSuccessHandler customSuccessHandler() {
		return new CustomSuccessHandler();
	}

	
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		// TODO Auto-generated method stub
		return new CustomAccessDeniedHandler();
	}

	@Override
	public void configure(WebSecurity web) { 
		//web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/sass/**",  "/favicon.ico");
		web.ignoring().antMatchers("/cork/**",  "/favicon.ico", "/error/**", "/honeyArcade/**");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/owner/ntc/**").permitAll()
				.antMatchers("/login/form").permitAll()		
				.antMatchers("/owner/**").hasRole("OWNER")
				.anyRequest().hasRole("OWNER")			//	나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근
			.and() // 로그인 설정
				.formLogin()
				.loginPage("/login/form")				//	로그인 페이지 호출
				//.loginProcessingUrl("/login/proc")	//	로그인 프로세스
				.successHandler(customSuccessHandler())	//	로그인 성공시 핸들러		
				.failureHandler(customFailureHandler())	//	로그인 실패시 핸들러
				//.defaultSuccessUrl("/") 				// 로그인이 성공했을 때 이동되는 페이지이며, 마찬가지로 컨트롤러에서 URL 매핑
			.and() // 로그아웃 설정
				.logout()
				.logoutUrl("/logout/proc")
				.logoutSuccessUrl("/")	
				//	세션 날리기
				.invalidateHttpSession(true)
			.and()
				// 403 예외처리 핸들링
            	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}


	
	
}
