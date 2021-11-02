package honeyarcade.owner.ad.area;

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
public class OwnerAdAreaController {
	
	@Autowired
	private OwnerAdAreaService ownerAdAreaService;

	/**
	 * 지역 광고 : 지역 광고 신청권 목록 가져오기
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area")
	public String adArea(Model model) throws Exception{
		String owner_id = SessionUtil.getOwnerId();
		List<OwnerAdAreaVO> areaList = ownerAdAreaService.getAreaList(owner_id);
		model.addAttribute("areaList", areaList);
		return "owner/ad/area/ownerAdArea";
	}
	
	/**
	 * 지역 광고 : 지역 광고 신청 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/area/reqProc", method=RequestMethod.POST)
	public ResponseEntity areaReqProc(@RequestBody HashMap dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String msg = null;
		try {
			log.info("지역 광고 신청 프로세스 : " + dataMap.toString());
			ownerAdAreaService.reqProc(dataMap);
		}catch(Exception e) {
			log.debug(e.toString());
			result = false;
			msg = e.getMessage();
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
