package honeyarcade.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import honeyarcade.util.SessionUtil;

@Controller
public class MainController {

	@Autowired
	private MainService mainService;
	
	/**
	 * 인덱스 : 메인
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(Model model)throws Exception {
		String url = null;
		if(SessionUtil.hasSession()) {
			url = "redirect:/owner/st/mypage";
		}else {
			url = "index";
			MainVO honeyInfo = mainService.getHoneyInfo();
			model.addAttribute("honeyInfo", honeyInfo);
		}
		return url;
	}
}
