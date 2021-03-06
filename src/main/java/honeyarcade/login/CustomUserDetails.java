package honeyarcade.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomUserDetails extends User{

	private static final long serialVersionUID = 1L;
	private Integer owner_status;
	private Integer	floor_type;
	private Integer	store_floor;
	private Integer	store_status;
	private String	owner_id;
	private	String	store_name;
	private String	store_ho;
	private String	build_name;
	private String	floor_name;
	
    public CustomUserDetails(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
    	
        super(
            userVO.getMember_id(),	//	username
            userVO.getOwner_pwd(),	//	password
            userVO.getOwner_status() == 2 || userVO.getOwner_status() == 3 ? true : false, 					//	enabled
            true,					//	accountNonExpired
            true,					//	credentialsNonExpired;
            true,
            authorities
        );
        
        this.owner_status	= userVO.getOwner_status();
        this.owner_id		= userVO.getOwner_id();
        this.store_ho		= userVO.getStore_ho();
        this.store_status	= userVO.getStore_status();
        this.build_name		= userVO.getBuild_name();
        this.floor_type		= userVO.getFloor_type();
        this.floor_name		= userVO.getFloor_name();
        this.store_floor	= userVO.getStore_floor();
        this.store_name		= userVO.getStore_name();
    }
    
}
