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
	
	private String owner_id;	// 아이디
	
	private String owner_pwd;	// 로그인 암호
		
	private String role_id;		// 권한	
	
	private int owner_status;
	
	
	public boolean isEnabled() {
		System.out.println(" this.owner_status : " + this.owner_status);
		return this.owner_status == 1? false : true;
	}
	
	
}
