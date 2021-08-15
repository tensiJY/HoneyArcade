package honeyarcade.core.security;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;


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

	
	/**
	 * 로그인 실패
	 * @return
	 */
    @Bean
	public CustomFailureHandler customFailureHandler() {
		return new CustomFailureHandler();
	};
	
	/**
	 * 로그인 성공 
	 * @return
	 */
	@Bean
	public CustomSuccessHandler customSuccessHandler() {
		return new CustomSuccessHandler();
	}

	/**
	 * 접근 권한 페이지 - 403
	 * @return
	 */
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		// TODO Auto-generated method stub
		return new CustomAccessDeniedHandler();
	}
	
	/**
	 * 로그아웃 핸들러
	 * @return
	 */
	@Bean
	public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	
	// logout 후 login할 때 정상동작을 위함
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
    
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
    
	
	@Override
	public void configure(WebSecurity web) { 
		//web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/sass/**",  "/favicon.ico");
		web.ignoring().antMatchers("/resources/**",  "/favicon.ico", "/error/**");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login/form").permitAll()
				.antMatchers("/join/**").permitAll()
				.antMatchers("/common/ntc/**").permitAll()
				.antMatchers("/owner/**").hasRole("OWNER")
				.anyRequest().hasRole("OWNER")			//	나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근
			.and() // 로그인 설정
				.formLogin()
				.loginPage("/login/form")				//	로그인 페이지 호출
				//.loginProcessingUrl("/login/proc")	//	로그인 프로세스
				.successHandler(customSuccessHandler())	//	로그인 성공시 핸들러		
				.failureHandler(customFailureHandler())	//	로그인 실패시 핸들러
				//.defaultSuccessUrl("/") 				//	로그인이 성공했을 때 이동되는 페이지이며, 마찬가지로 컨트롤러에서 URL 매핑
			.and() // 로그아웃 설정
				.logout()
				//.logoutUrl("/logout/proc")			//	로그아웃 페이지로 이동
				.logoutSuccessHandler(customLogoutSuccessHandler())
				.invalidateHttpSession(false)
				.permitAll()
			.and()
				// 403 예외처리 핸들링
            	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
            .and()
            	.sessionManagement()
            	.maximumSessions(1)						//	 한 사용자가 몇 개의 세션까지 사용가능할지 여부로, 1로 하면 동시 접속 불가 설정을 할 수 있다.
            	.maxSessionsPreventsLogin(false)		//	 false일 경우 : 먼저 접속한 사용자 logout 처리		
				.expiredUrl("/error/duplicatedLogin")
				.sessionRegistry(sessionRegistry());
	}

	
	
	
}
