package honeyarcade.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import honeyarcade.login.LoginService;

//import honeyarcade.login.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginService loginService;
	
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	
	/* DaoAuthenticationProvider는 내부적으로 UserDetailsService를 이용해 사용자 정보를 읽는다.*/

	@Bean
    public DaoAuthenticationProvider authenticationProvider(LoginService loginService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(loginService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return authenticationProvider;
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		  auth.authenticationProvider(authenticationProvider(loginService));
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
		web.ignoring().antMatchers("/cork/**",  "/favicon.ico", "/error/**");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/login/form").permitAll()
				.antMatchers("/register/form").permitAll()
				.antMatchers("/**").hasRole("OWNER")
				//.anyRequest().authenticated()			//	나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근
			.and() // 로그인 설정
				.formLogin()
				.loginPage("/login/form")				//	로그인 페이지 호출
				.loginProcessingUrl("/login/proc")		//	로그인 프로세스
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
