package honeyarcade.common.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/common/api")
@Slf4j
public class CommonApiController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	/**
	 * 시도
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSido", method=RequestMethod.POST)
	public ResponseEntity getSido() throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			List<CommonApiVO> sidoList = commonApiService.getSido();
			resultMap.put("sidoList", sidoList);
		}catch(Exception e) {
			log.debug(e.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 시군구
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSigungu", method=RequestMethod.POST)
	public ResponseEntity getSigungu(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			List<CommonApiVO> sigunguList = commonApiService.getSigungu(commonApiVO);
			resultMap.put("sigunguList", sigunguList);
			resultMap.put("sido_seq", commonApiVO.getSido_seq());
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 동
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDong", method=RequestMethod.POST)
	public ResponseEntity getDong(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
			resultMap.put("dongList", dongList);
			resultMap.put("sigungu_seq", commonApiVO.getSigungu_seq());
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 건물
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getBuild", method=RequestMethod.POST)
	public ResponseEntity getBuild(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			List<CommonApiVO> buildList = commonApiService.getBuild(commonApiVO);
			resultMap.put("buildList", buildList);
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 층
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getFloor", method=RequestMethod.POST)
	public ResponseEntity getFloor(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			List<CommonApiVO> floorList = commonApiService.getFloor(commonApiVO);
			List<CommonApiVO> lcateList = commonApiService.getLcateOfBuild(commonApiVO);
			resultMap.put("floorList", floorList);
			resultMap.put("lcateList", lcateList);
		}catch(Exception e) {
			log.debug("/getFloor");
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 건물
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getStoreOfBuild")
	public ResponseEntity getOwnerOfBuild(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			List<CommonApiVO> ownerList = commonApiService.getOwnerOfBuild(commonApiVO);
			resultMap.put("ownerList", ownerList);
		}catch(Exception e) {
			log.debug("/getOwnerOfBuild");
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 소분류
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMcateOfBuild")
	public ResponseEntity getMcateOfBuild(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			log.info(commonApiVO.toString());
			List<CommonApiVO> mcateList = commonApiService.getMcateOfBuild(commonApiVO);
			resultMap.put("mcateList", mcateList);
		}catch(Exception e) {
			log.debug("/getOwnerOfBuild");
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 상점 목록
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getStore")
	public ResponseEntity getStore(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String errorMsg = null;
		try {
			List<CommonApiVO> storeList = commonApiService.getStore(commonApiVO);
			resultMap.put("storeList", storeList);
		}catch(Exception e) {
			log.debug("/getStore");
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 호 목록 가져오기(build_seq, floor_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getHo")
	public ResponseEntity getHo(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;		
		String msg = null;
		List<CommonApiVO> hoList = new ArrayList<CommonApiVO>();
		try {
			hoList = commonApiService.getHo(commonApiVO);
		}catch(Exception e) {
			log.debug("/getHo");
			log.debug(e.toString());
			result = false;
			msg = e.getMessage();
		}finally {
			resultMap.put("hoList", hoList);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
