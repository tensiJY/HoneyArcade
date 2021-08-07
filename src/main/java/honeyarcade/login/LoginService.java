package honeyarcade.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	/*
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	*/

	/* DB에서 유저정보를 불러온다.
	 * Custom한 Userdetails 클래스를 리턴 해주면 된다.
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("call UserDetails : ");
		System.out.println(username);
		UserVO userVO = loginMapper.findByLoginId(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority(userVO.getRole_id()));
		
		return new User(userVO.getLogin_id(), userVO.getLogin_pwd(), authorities);
		
	}
	
	

}
