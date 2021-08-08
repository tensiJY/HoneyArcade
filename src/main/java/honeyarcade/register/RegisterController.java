package honeyarcade.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
	
	@GetMapping("/register/form")
	public String joinForm() throws Exception{
		
		return "register/registerForm";
	}

}
