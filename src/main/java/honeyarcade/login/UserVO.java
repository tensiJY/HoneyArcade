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
	private String	member_id;
	private String	owner_id;		//	시퀀스
	private String	owner_pwd;		//	로그인 암호
	private String	role_id;		//	권한
	private Integer	owner_status;	//	1미승인 2승인 3거절
	private String	owner_email;	//	이메일
	private	String	store_name;
	private String	store_ho;
	private Integer	store_status;
	private String	build_name;
	private Integer	floor_type;
	private String	floor_name;
	private Integer	store_floor;
	//	비밀번호 찾기
	private String	new_owner_pwd;	//	새로운 비밀번호(문자열)	-> 메일 전송
	private String	crypt_owner_pwd;//	새로운 비밀번호(암호화)	-> DB에 저장
	
}
