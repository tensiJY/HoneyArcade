package com.honeyarcade.admin.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminPrincipalVO implements UserDetails{
	
	//UID값을 명시 해주지 않으면 자바 컴파일러가 임시적인 값을 부여한다.
	private static final long serialVersionUID = 1L;
	
	private ArrayList<AdminVO> adminVO;
	
	public AdminPrincipalVO(ArrayList<AdminVO> userAuthes) {
		this.adminVO = userAuthes;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //유저가 갖고 있는 권한 목록

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(int x=0; x<adminVO.size(); x++) {
			authorities.add(new SimpleGrantedAuthority(adminVO.get(x).getRole_id()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() { //유저 비밀번호
		return adminVO.get(0).getLogin_pwd();
	}

	@Override
	public String getUsername() {// 유저 이름 혹은 아이디
		return adminVO.get(0).getLogin_id();
	}
	
	@Override
	public boolean isAccountNonExpired() {// 유저 아이디가 만료 되었는지
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 유저 아이디가 Lock 걸렸는지
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호가 만료 되었는지
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정이 활성화 되었는지
		return true;
	}

}
