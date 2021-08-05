package honeyarcade.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import honeyarcade.util.LoginUserDetailHelper;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	public LoginUserDetailHelper loginUserDetailHelper;
	
	/**
	 * 로그인 페이지, 인덱스
	 * id가 없거나 잘못된 비밀번호 일경우 에러
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String proc(Model model) {
		model.addAttribute("exception","가입하지 않은 아이디이거나, 잘못된 비밀번호입니다");
		return "index";
	}
	
	/*
	@GetMapping("/login/proc")
	public String loginProc(Model model, RedirectAttributes rttr) {
		//log.info("call login proc");
		rttr.addAttribute("exception","가입하지 않은 아이디이거나, 잘못된 비밀번호입니다");
		//return "index";
		return "redirect:/";
	}
	*/
}
