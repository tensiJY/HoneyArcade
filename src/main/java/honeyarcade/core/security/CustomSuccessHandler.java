package honeyarcade.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import honeyarcade.login.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공시 핸들러
 * @author wndud
 *
 */
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private CustomUserDetailsService loginService; 

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//	로그인 성공시 부가작업
		//	1. 에러 세션 지우기
		//	2. 실패 초기화
		//	3. URL 리다이렉트
		
		//	에러 세션 삭제
		clearAuthenticationAttributes(request);

		//	실패 초기화
//		String username = request.getParameter("username");
//		loginService.resetFailureCount(username);

		
		//	url 리다이렉트
		resultRedirectStrategy(request, response, authentication);
	}
	
	/**
	 * 에러 세션 삭제
	 * @param request
	 */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        
    }


	
	//	RequestCache 인터페이스를 이용해, 사용자 요청 정보들이 들어 있는 SavedRequest 클래스 객체를 세션에 저장하게 된다. 
	//	RequestCache 객체를 생성해 SavedRequest 객체를 가져와서 로그인 화면을 보기 전에 방문했던 URL 정보를 가져오면 된다.
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	

    	//	로그인 프로세스를 설정 하지 않는 경우, 로그인 프로세스는 타지 않는다.
    	//	로그인 폼에서 action값을 login/form으로 설정
    	//	로그인 화면을 보기 전에 방문하려던 URL을 찾고, 그 URL로 리다이렉트 시킨다
    	//	
    	
    	String targetUrl = null; 
    	String defaultUrl = "/";
    	
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if(savedRequest!=null) {
        	
        	targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
            
        } else {
        	
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
    	
    }

}






/*
//	로그인 프로세스 설정 했으면 로그인 프로세스로 이동한다
//	targetUrl : 방문하려는 URL이 없는 경우	"/" 으로 이동
//	targetUrl : 방문하려는 URL이 있는 경우 이동하려는 페이지

String url = "/login/proc";
boolean isError = false;	
String targetUrl = null;

SavedRequest savedRequest = requestCache.getRequest(request, response);
if(savedRequest!=null) {
	 
	targetUrl = savedRequest.getRedirectUrl();
}else {
	
	targetUrl = "/";
}

request.setAttribute("targetUrl", targetUrl);
request.setAttribute("isError", isError);
request.getRequestDispatcher(url).forward(request, response);
*/

