package honeyarcade.owner.st.dtl;

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

import honeyarcade.common.api.CommonApiService;
import honeyarcade.common.api.CommonApiVO;
import honeyarcade.common.file.CommonFileService;
import honeyarcade.common.file.CommonFileVO;
import honeyarcade.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owner/st")
public class OwnerStDtlController {
	
	@Autowired
	private OwnerStDtlService ownerStDtlService;
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private CommonFileService commonFileService;

	/**
	 * 세부정보
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dtl")
	public String stDtl(Model model) throws Exception{
		String owner_id = SessionUtil.getOwnerId();
		OwnerStDtlVO ownerInfo = ownerStDtlService.getOwnerInfo(owner_id);
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(ownerInfo.getSido_seq());
		commonApiVO.setSigungu_seq(ownerInfo.getSigungu_seq());
		commonApiVO.setDong_seq(ownerInfo.getDong_seq());
		commonApiVO.setBuild_seq(ownerInfo.getBuild_seq());
		commonApiVO.setLcate_seq(ownerInfo.getLcate_seq());
		commonApiVO.setOwner_id(owner_id);
		commonApiVO.setFloor_seq(ownerInfo.getStore_floor());
		commonApiVO.setHo_type("search");
		
		List<CommonApiVO> sidoList 		= commonApiService.getSido();						//	시도
		List<CommonApiVO> sigunguList	= commonApiService.getSigungu(commonApiVO);			//	시군구
		List<CommonApiVO> dongList 		= commonApiService.getDong(commonApiVO);			//	지역
		List<CommonApiVO> buildList		= commonApiService.getBuild(commonApiVO);			//	건물
		List<CommonApiVO> floorList		= commonApiService.getFloor(commonApiVO);			//	층
		List<CommonApiVO> lcateList		= commonApiService.getLcateOfBuild(commonApiVO);	//	대분류
		List<CommonApiVO> mcateList		= commonApiService.getMcateOfBuild(commonApiVO);	//	소분류
		List<CommonApiVO> hoList		= commonApiService.getHo(commonApiVO);
		List<OwnerStDtlVO> productList	= ownerStDtlService.getProductList(owner_id);		//	상품
		List<OwnerStDtlVO> openList		= ownerStDtlService.getOpenDay(owner_id);			//	오픈시간
		List<OwnerStDtlVO> breakList	= ownerStDtlService.getBreakDay(owner_id);			//	휴게시간
		List<OwnerStDtlVO> dayOffList	= ownerStDtlService.getDayOffList(owner_id);		//	휴무일
		
		//	휴가 설정 (긴급 휴무) + 공휴일 설정 ( type = 1  긴급휴무, type = 2 공휴일) 
		List<OwnerStDtlVO> holDayList = ownerStDtlService.getHolidayList(owner_id); 
				
		CommonFileVO fileVO = new CommonFileVO();
		fileVO.setFile_seq(ownerInfo.getBusi_reg_file_seq());
		CommonFileVO busiVO = commonFileService.getFileDtlInfo(fileVO);	//	사업자등록증
		fileVO.setFile_seq(ownerInfo.getMain_file_seq());
		List<CommonFileVO> mainList = commonFileService.getFileDtlList(fileVO);

		model.addAttribute("holDayList",holDayList);
		model.addAttribute("dayOffList", dayOffList);
		model.addAttribute("breakList", breakList);
		model.addAttribute("openList", openList);
		model.addAttribute("hoList", hoList);
		model.addAttribute("mainList", mainList);
		model.addAttribute("busiVO", busiVO);
		model.addAttribute("productList", productList);
		model.addAttribute("mcateList", mcateList);
		model.addAttribute("lcateList", lcateList);
		model.addAttribute("floorList", floorList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("dongList", dongList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("ownerInfo", ownerInfo);
		return "owner/st/dtl/ownerStDtl";
	}
	
	/**
	 * 세부 정보 저장 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveProc")
	public ResponseEntity saveProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		Integer type = null;	

		try {
			ownerStDtlService.saveProc(dataMap);
		}catch(Exception e) {
			result = false;
			msg = e.getMessage();
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("type", type);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
			
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
