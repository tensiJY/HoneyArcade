package honeyarcade.owner.ntc.list;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/owner")
@Slf4j
public class NtcController {
	
	@GetMapping("/ntc/list")
	public String ownerNtcList() throws Exception{
		System.out.println(111);
		
		return "/owner/ntc/ownerNtcList";
	}

	
	@GetMapping("/ntc/view")
	public String ownerNtcView() throws Exception{
		
		return "/owner/ntc/ownerNtcView";
	}
}