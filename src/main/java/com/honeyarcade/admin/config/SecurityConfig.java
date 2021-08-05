package com.honeyarcade.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.honeyarcade.admin.login.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

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

	@Override
	public void configure(WebSecurity web) { // scr/main/resources/static 하위 폴더들 접근 가능하게 하기
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				// 페이지 권한 설정
				.antMatchers("/").permitAll()	//	누구나 접근 가능
				.anyRequest().hasRole("ADMIN") // ADMIN만 접근 가능
				//.anyRequest().authenticated()	//	나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근
			.and() // 로그인 설정
				.formLogin()
				.loginPage("/")
				.loginProcessingUrl("/login/proc")
				.defaultSuccessUrl("/main/home") // 로그인이 성공했을 때 이동되는 페이지이며, 마찬가지로 컨트롤러에서 URL 매핑
				//.permitAll()
			.and() // 로그아웃 설정
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/pro/logout"))
				.logoutSuccessUrl("/")	//
				//	세션 날리기
				.invalidateHttpSession(true)
			.and()
				// 403 예외처리 핸들링
            	.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
}
