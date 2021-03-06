package honeyarcade.util;

import org.springframework.security.core.context.SecurityContextHolder;

import honeyarcade.login.CustomUserDetails;



public class SessionUtil {
	
	/**
	 * 세션 여부 확인 : 세션이 없으면 false, 세션이 있으면 true
	 * @return
	 */
	public static boolean hasSession() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean result = principal instanceof CustomUserDetails;
		
		return result;
	}
	
	public static String getOwnerId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		
		return user.getOwner_id();
	}
	
	public static String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		
		return user.getUsername();
	}
	
}
