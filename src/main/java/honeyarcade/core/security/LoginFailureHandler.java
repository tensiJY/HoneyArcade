package honeyarcade.core.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler{
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		
		//	로그인 프로세스로 포워드
		String defaultFailureUrl = "/login/proc";
		
		
		boolean isError = true;	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
        
        System.out.println("LoginFailureHandler : " + username);
        System.out.println(password);
        System.out.println(defaultFailureUrl);
        System.out.println(exception.toString());
        
        
        
        if(exception instanceof BadCredentialsException) {							//	비밀 번호 혹은 존재 하지 않는 아이디 일때
        	errorMsg = "비밀 번호 혹은 존재 하지 않는 아이디 일때"; 							// MessageUtils.getMessage("error.BadCredentials");
        } else if(exception instanceof InternalAuthenticationServiceException) {	//	시스템 문제로 내부 인증 관련 처리 요청을 할 수 없는 경우
        	errorMsg = "존ㅇㄴㅇㅇㅇㅇ";
        } else if(exception instanceof DisabledException) {							//	인증거부 : 잠긴 계정일 경우
           // errormsg = MessageUtils.getMessage("error.Disaled");
        } else if(exception instanceof CredentialsExpiredException) {				//	인증거부 : 비밀번호 유효 기간 만료
           // errormsg = MessageUtils.getMessage("error.CredentialsExpired");
        } else if(exception instanceof UsernameNotFoundException) {
        	errorMsg = "계정 없음";
        }
        
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("errorMsg", errorMsg);
        request.setAttribute("isError", isError);
 
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);


	}

}
