package honeyarcade.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String index(Model model, HttpSession session) {
		log.info("call index");
		
		return "index";
	}
	
	/**
	 * 로그인 폼
	 * @param model
	 * @return
	 */
	@GetMapping("/login/form")
	public String loginForm(Model model) {
		log.info("call login form");
		return "login/loginForm";
	}
	
	
	/**
	 * 로그인 프로세스
	 * @param model
	 * @return
	 */
	@PostMapping("/login/proc")
	public String loginProc(Model model) {
		log.info("call login proc");
		log.info(model.toString());
		return "/login/loginProc";
	}
	
}
