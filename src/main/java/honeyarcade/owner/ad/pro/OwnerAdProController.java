package honeyarcade.owner.ad.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner/ad")
public class OwnerAdProController {
	
	@Autowired
	private OwnerAdProService ownerAdProService;
	
	/**
	 * 신청접수 : 프로모션 목록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pro")
	public String adPro(Model model) throws Exception{
		List<OwnerAdProVO> eventList = ownerAdProService.getEventList();
		model.addAttribute("eventList", eventList);
		return "owner/ad/pro/ownerAdPro";
	}
}
