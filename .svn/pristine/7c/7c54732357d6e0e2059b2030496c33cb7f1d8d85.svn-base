package honeyarcade.owner.ad.build;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import honeyarcade.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owner/ad")
public class OwnerAdBuildController {
	
	@Autowired
	private OwnerAdBuildService ownerAdBuildService;
	
	/**
	 * 건물 광고 : 건물 광고 신청권 목록 가져오기
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build")
	public String adBuild(Model model) throws Exception{
		String owner_id = SessionUtil.getOwnerId();
		List<OwnerAdBuildVO> buildList = ownerAdBuildService.getBuildList(owner_id);
		model.addAttribute("buildList", buildList);
		return "owner/ad/build/ownerAdBuild";
	}
	
	/**
	 * 건물 광고 : 건물 광고 신청 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/build/reqProc", method=RequestMethod.POST)
	public ResponseEntity buildReqProc(@RequestBody HashMap dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			log.info("건물 광고 신청 프로세스 : " + dataMap.toString());
			ownerAdBuildService.reqProc(dataMap);
		}catch(Exception e) {
			log.debug(e.toString());
			result = false;
			errorMsg = e.getMessage();
		}finally {
			resultMap.put("errorMsg", errorMsg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
