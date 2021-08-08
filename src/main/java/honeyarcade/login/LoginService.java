package honeyarcade.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

	
	@Autowired
	private LoginMapper loginMapper;
	
	PasswordEncoder passwordEncoder;
	

	/* DB에서 유저정보를 불러온다.
	 * Custom한 Userdetails 클래스를 리턴 해주면 된다.
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserVO userVO = loginMapper.findByLoginId(username);
		
		
		//	1. UserDetailsService -> 
		//	2. AuthenticationFailureHandler -> 
		//	3. login process (시큐리티 설정)
		
		//	오류처리를 하지 않으면 서버 에러 
		if(userVO == null) {
			//	userNotFoundPassword 예외처리가 안된다 -> InternalAuthenticationServiceException 변경하여 사용
			throw new InternalAuthenticationServiceException(username);
		}
		
		
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userVO.getRole_id()));
		
		
		return new User(userVO.getLogin_id(), userVO.getLogin_pwd(), authorities);
		
	}
	
	

	
	/**
	 * 로그인 성공시 실패 횟수 초기화
	 * @param username
	 */
	public void resetFailureCount(String username) {
		
		loginMapper.resetFailureCount(username);
		
	}

	/**
	 * 로그인 실패시 실패 횟수 증가
	 * @param username
	 */
	public void addFailureCount(String username) {
		// TODO Auto-generated method stub
		loginMapper.addFailureCount(username);
	}

	
	/**
	 * 실패 회수 조회
	 * @param username
	 * @return
	 */
	public int getFailureCount(String username) {
		
		return loginMapper.getFailureCount(username);
	}

	/**
	 * 계정 잠금
	 * @param username
	 */
	public void disabledUsername(String username) {
		// TODO Auto-generated method stub
		
	}

	
	

}
