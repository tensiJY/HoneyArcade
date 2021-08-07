package honeyarcade.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	public LoginUserDetailHelper loginUserDetailHelper;
	
	/**
	 * 인덱스
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, HttpSession session)throws Exception {
		log.info("call index");
		
		return "index";
	}
	
	
	/**
	 * 로그인
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/login/form")
	public String loginForm(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		log.info("login form call");
		
				
		System.out.println(req.getAttribute("errorMsg"));
		
		return "login/loginForm";
	}
	
	
	/**
	 * 로그인 프로세스
	 * @param model
	 * @return
	 */
	@PostMapping("/login/proc")
	public void loginProc(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String url = null;
		
		boolean isError = (boolean) req.getAttribute("isError");
		if(isError) {
			
			url = "/login/form";
			
			req.getRequestDispatcher(url).forward(req, res);
			
		}
	}
	
	@GetMapping("/logout/proc")
	public void logoutProc() {
		
	}
	
	@GetMapping("/main/home")
	public String mainHome() {
		return "main/index";
	}
	
}
