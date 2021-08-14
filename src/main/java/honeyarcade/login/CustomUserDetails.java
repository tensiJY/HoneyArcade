package honeyarcade.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User{
	
	//	스프링 시큐리티는 UserDetails 라는 인터페이스를 구현한 객체를 로그인과정에서 사용합니다.
	//	하지만 요즘에는 이 인터페이스를 어느정도 구현하고 있는 User 라는 클래스를 제공하고 있기 때문에, 이 User 클래스를 상속받아서 생성자를 UserVO 에 맞게 설정해 주면 편리하게 사용할 수 있습니다.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    public CustomUserDetails(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
    	
    	
    	
        super(
            userVO.getOwner_id(),	//	username
            userVO.getOwner_pwd(),	//	password
            userVO.isEnabled(),		//	enabled
            true,					//	accountNonExpired
            true,					//	credentialsNonExpired;
            true,
            authorities
        );
        
      
    }


}



//public User(String username, String password, boolean enabled, boolean accountNonExpired,
//		boolean credentialsNonExpired, boolean accountNonLocked,
//		Collection<? extends GrantedAuthority> authorities) {
//	Assert.isTrue(username != null && !"".equals(username) && password != null,
//			"Cannot pass null or empty values to constructor");
//	this.username = username;
//	this.password = password;
//	this.enabled = enabled;
//	this.accountNonExpired = accountNonExpired;
//	this.credentialsNonExpired = credentialsNonExpired;
//	this.accountNonLocked = accountNonLocked;
//	this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
//}


//private ArrayList<UserVO> userVO;
//
//public CustomUserDetails(ArrayList<UserVO> userAuthes) {
//	this.userVO = userAuthes;
//}
//
//@Override
//public Collection<? extends GrantedAuthority> getAuthorities() { //유저가 갖고 있는 권한 목록
//
//	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//	
//	for(int x=0; x<userVO.size(); x++) {
//		authorities.add(new SimpleGrantedAuthority(userVO.get(x).getRole_id()));
//	}
//	
//	return authorities;
//}
//
//@Override
//public String getPassword() { 				//유저 비밀번호
//	return userVO.get(0).getOwner_id();
//}
//
//@Override
//public String getUsername() {				// 유저 이름 혹은 아이디
//	return userVO.get(0).getOwner_pwd();
//}
//
//@Override
//public boolean isAccountNonExpired() {		// 유저 아이디가 만료 되었는지
//	return true;
//}
//
//@Override
//public boolean isAccountNonLocked() { 		// 유저 아이디가 Lock 걸렸는지
//	return true;
//}
//
//@Override
//public boolean isCredentialsNonExpired() {	//비밀번호가 만료 되었는지
//	return true;
//}
//
//@Override
//public boolean isEnabled() {				 // 계정이 활성화 되었는지
//	return true;
//}