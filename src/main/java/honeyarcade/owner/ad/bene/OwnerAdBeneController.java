package honeyarcade.owner.ad.bene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import honeyarcade.main.MainVO;

@Controller
@RequestMapping("/owner/ad")
public class OwnerAdBeneController {
	
	@Autowired
	private OwnerAdBeneService ownerAdBeneService;

	/**
	 * 혜택사항
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bene")
	public String adBene(Model model) throws Exception{
		OwnerAdBeneVO beneVO = ownerAdBeneService.getBeneInfo();
		model.addAttribute("beneVO", beneVO);
		return "owner/ad/bene/ownerAdBene";
	}
	
}
