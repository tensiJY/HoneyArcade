package honeyarcade.ntc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NtcController {
	
	@Autowired
	private NtcService ntcService;

	/**
	 * 공지사항 : 목록
	 * @param model
	 * @param ntcVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/list")
	public String ntcList(Model model, @ModelAttribute NtcVO ntcVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "12")int listCount
			) throws Exception{
				
		ntcVO.setType(type);
		Integer total_count = ntcService.getTotalCount(ntcVO);
		PageUtil pageUtil = new PageUtil(nowPage, total_count, listCount);
		
		ntcVO.setStart_num(pageUtil.getStartNum());
		ntcVO.setEnd_num(pageUtil.getEndNum());
		
		List<NtcVO> ntcList = ntcService.getNtcList(ntcVO);
		
		model.addAttribute("ntcList", ntcList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("type", type);
		model.addAttribute("pageUtil", pageUtil);
		return "ntc/ntcList";
	}
	
	/**
	 * 공지사항 : 리스트 가져오기
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/getList")
	public ResponseEntity ntcGetList(@RequestBody HashMap dataMap) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			Integer listCount = Integer.parseInt(String.valueOf(dataMap.get("listCount")));
			Integer nowPage	  = Integer.parseInt(String.valueOf(dataMap.get("nowPage")));
			String	type	  = String.valueOf(dataMap.get("type"));
			NtcVO queryVO = new NtcVO();
			queryVO.setType(type);
			Integer total_count = ntcService.getTotalCount(queryVO);
			PageUtil pageUtil = new PageUtil(nowPage, total_count, listCount);
			queryVO.setStart_num(pageUtil.getStartNum());
			queryVO.setEnd_num(pageUtil.getEndNum());
			List<NtcVO> ntcList = ntcService.getNtcList(queryVO);
			resultMap.put("ntcList", ntcList);
		}catch(Exception e) {
			result = false;
			msg = "서버 처리 중 에러가 발생하였습니다. 관리자에게 문의 바랍니다";
			log.error("공통 공지사항 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 공지사항 : 공지사항 상세보기
	 * @param model
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/view")
	public String ntcView(Model model, @ModelAttribute NtcVO ntcVO) throws Exception{
		NtcVO ntcView = ntcService.getNtcView(ntcVO);
		Integer ntcPrevSeq = ntcService.getPrevSeq(ntcVO);
		Integer ntcNextSeq = ntcService.getNextSeq(ntcVO);
		model.addAttribute("ntcPrevSeq", ntcPrevSeq);
		model.addAttribute("ntcNextSeq", ntcNextSeq);
		model.addAttribute("ntcView", ntcView);
		return "ntc/ntcView";
	}
}
