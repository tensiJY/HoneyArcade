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
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		log.info("call index");
		
		return "index";
	}
	
	@GetMapping("/login/form")
	public String loginForm(Model model) {
		log.info("call login form");
		return "login/loginForm";
	}
	
	
	@PostMapping("/login/proc")
	public String loginProc(Model model) {
		log.info("call login proc");
		log.info(model.toString());
		model.addAttribute("exception","가입하지 않은 아이디이거나, 잘못된 비밀번호입니다");
		//return "index";
		//return "redirect:/";
		
		return "/login/loginProc";
	}
	
}
