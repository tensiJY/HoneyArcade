package honeyarcade.login;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String login_id;	// 아이디
	
	private String login_pwd;	// 로그인 암호
		
	private String role_id;		// 권한	
	

}
