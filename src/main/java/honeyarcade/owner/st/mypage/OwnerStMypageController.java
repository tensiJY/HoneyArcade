package honeyarcade.owner.st.mypage;

import java.util.ArrayList;
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
import honeyarcade.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owner/st")
public class OwnerStMypageController {
	
	@Autowired
	private OwnerStMypageService ownerStMypageService;

	/**
	 * 마이페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage")
	public String stMypage(Model model) throws Exception{
		
		String owner_id			= SessionUtil.getOwnerId();
		String banner_end_day	= StringUtil.getFullYearYYYY() +"-" + StringUtil.getMonthString()+"-"+StringUtil.getDayDD();
		String banner_start_day	= StringUtil.addDate(banner_end_day, -6);
				
		OwnerStMypageVO paramVO = new OwnerStMypageVO();
		paramVO.setOwner_id(owner_id);
		paramVO.setBanner_start_day(banner_start_day);
		paramVO.setBanner_end_day(banner_end_day);
		paramVO.setCoupon_start_day(banner_start_day);
		paramVO.setCoupon_end_day(banner_end_day);
		paramVO.setSearch_start_day(StringUtil.getFirstDayOfWeek(banner_end_day));
		paramVO.setSearch_end_day(StringUtil.getLastDayOfWeek(banner_end_day));
		
		//	지역광고 현황 조회
		List<OwnerStMypageVO> areaList = ownerStMypageService.getAreaList(paramVO);
		
		//	건물광고 현황 조회
		List<OwnerStMypageVO> buildList = ownerStMypageService.getBuildList(paramVO);
		
		//	쿠폰광고 현황 조회
		List<OwnerStMypageVO> couponList = ownerStMypageService.getCouponList(paramVO);
		
		model.addAttribute("ownerStMypageVO", paramVO);
		model.addAttribute("areaList", areaList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("couponList", couponList);
		return "owner/st/mypage/ownerStMypage";
	}
	
	/**
	 * 광고 현황 조회
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAdData", method=RequestMethod.POST)
	public ResponseEntity getAdData(@RequestBody HashMap dataMap) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;
		String msg = null;
		List<OwnerStMypageVO> dataList = new ArrayList<OwnerStMypageVO>();
		
		try {
			
			String owner_id = SessionUtil.getOwnerId();
			OwnerStMypageVO paramVO = new OwnerStMypageVO();
			paramVO.setOwner_id(owner_id);
			Integer type = Integer.parseInt(String.valueOf(dataMap.get("type")));
									
			if(type == 1) {
				paramVO.setBanner_start_day(String.valueOf(dataMap.get("banner_start_day")));
				paramVO.setBanner_end_day(String.valueOf(dataMap.get("banner_end_day")));
				dataList = ownerStMypageService.getBuildList(paramVO);				
			}else if(type == 2) {
				paramVO.setBanner_start_day(String.valueOf(dataMap.get("banner_start_day")));
				paramVO.setBanner_end_day(String.valueOf(dataMap.get("banner_end_day")));  
				dataList = ownerStMypageService.getAreaList(paramVO);
			}else if(type == 3) {
				paramVO.setCoupon_start_day(String.valueOf(dataMap.get("coupon_start_day")));
				paramVO.setCoupon_end_day(String.valueOf(dataMap.get("coupon_end_day")));
				dataList = ownerStMypageService.getCouponList(paramVO);
			}
						
		}catch(Exception e) {
			log.debug("data : " + dataMap.toString());
			log.debug(e.toString());
			result = false;
			msg = e.getMessage();
		}finally {
			resultMap.put("dataList", dataList);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 일별 차트 조회
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getChartData", method=RequestMethod.POST)
	public ResponseEntity getChartData(@RequestBody HashMap dataMap) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;
		String msg = null;
		List<OwnerStMypageVO> dataList = new ArrayList<OwnerStMypageVO>();
		
		try {
			String owner_id = SessionUtil.getOwnerId();
			OwnerStMypageVO paramVO = new OwnerStMypageVO();
			paramVO.setOwner_id(owner_id);
			paramVO.setSearch_start_day(String.valueOf(dataMap.get("search_start_day")));
			paramVO.setSearch_end_day(String.valueOf(dataMap.get("search_end_day")));
			dataList = ownerStMypageService.getChartData(paramVO);
		}catch(Exception e) {
			log.debug("data : " + dataMap.toString());
			log.debug(e.toString());
			result = false;
			msg = e.getMessage();
		}finally {
			resultMap.put("dataList", dataList);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
