package honeyarcade.reg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import honeyarcade.common.file.CommonFileService;
import honeyarcade.common.file.CommonFileVO;
import honeyarcade.util.StringUtil;

@Service
public class RegService {
	
	@Autowired
	private RegMapper regMapper;
	
	@Autowired
	private CommonFileService commonFileService;

	/**
	 * 회원 가입 : 아이디 중복 체크
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public Integer idCheck(String owner_id) throws Exception{
		return regMapper.idCheck(owner_id);
	}
	
	/**
	 * 회원 가입 : 이메일 중복 체크
	 * @param owner_email
	 * @return
	 * @throws Exception
	 */
	public Integer emailCheck(String owner_email) throws Exception{
		return regMapper.emailCheck(owner_email);
	}

	/**
	 * 회원 가입 : 저장 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	public void saveProc(HashMap dataMap) throws Exception {
		String	username = String.valueOf(dataMap.get("username"));
		String	password = String.valueOf(dataMap.get("password"));
		String	owner_email	= String.valueOf(dataMap.get("owner_email"));
		String	owner_phone = String.valueOf(dataMap.get("owner_phone"));
		String	build_seq = String.valueOf(dataMap.get("build_seq"));
		String	floor_seq = String.valueOf(dataMap.get("floor_seq"));
		String	mcate_seq = String.valueOf(dataMap.get("mcate_seq"));
		String	lcate_seq = String.valueOf(dataMap.get("lcate_seq"));
		String	store_keyword = String.valueOf(dataMap.get("store_keyword"));
		String	store_text	= String.valueOf(dataMap.get("store_text"));
		String	store_ho = String.valueOf(dataMap.get("store_ho"));
		String	store_tel = String.valueOf(dataMap.get("store_tel"));
		String	store_name	= String.valueOf(dataMap.get("store_name"));
		String	pre_owner_id = String.valueOf(dataMap.get("pre_owner_id"));
		HashMap	owner_busi_img = (HashMap) dataMap.get("owner_busi_img");
		
		List productList = (List) dataMap.get("product");	//	점포 상품 등록
		List openList = (List)dataMap.get("open");			//	점포 영업시간
		List breakList = (List) dataMap.get("break");		//	점포 휴게시간
		List holidayList = (List) dataMap.get("holiday");	//	점포 공휴일 및 긴급 휴가	
		List offList = (List) dataMap.get("off");			//	점포 휴무일
		List ownerMainImgList = (List) dataMap.get("owner_main_img");
		
		if(username == "null" || "".equals(username)) {
			throw new Exception("로그인 아이디를 등록해주세요");
		}
		
		if(password == "null" || "".equals(password)) {
			throw new Exception("로그인 비밀번호를 등록해주세요");
		}
						
		if(owner_phone == "null" || "".equals(owner_phone)) {
			throw new Exception("휴대폰 번호를 등록해주세요");
		}

		if(owner_email == "null" || "".equals(owner_email)) {
			throw new Exception("이메일 주소를 등록해주세요");
		}
		
		if(store_keyword == "null" || "".equals(store_keyword)) {
			throw new Exception("검색 키워드를 등록해주세요");
		}
		
		if(store_text == "null" || "".equals(store_text)) {
			throw new Exception("점포 소개글을 등록해주세요");
		}
		
		if(store_tel == "null" || "".equals(store_tel)) {
			throw new Exception("점포 전화번호를 등록해주세요");
		}		
		
		if(build_seq == "null" || "".equals(build_seq)) {
			throw new Exception("건물을 등록해주세요");
		}
		
		if(floor_seq == "null" || "".equals(floor_seq)) {
			throw new Exception("층을 등록해주세요");
		}
		
		if(lcate_seq == "null") {
			throw new Exception("대분류를 등록해주세요");
		}
		
		if(mcate_seq == "null" || "".equals(mcate_seq)) {
			throw new Exception("소분류를 등록해주세요");
		}
		
		if(owner_busi_img.isEmpty()) {
			throw new Exception("사업자 등록증을 등록해주세요");
		}
		
		if(ownerMainImgList.size()==0) {
			throw new Exception("매장 대표사진을 등록해주세요");
		}
		
		if(productList.size() == 0) {
			throw new Exception("상품을 등록해주세요");
		}
		
		if(breakList.size() == 0) {
			throw new Exception("점포 휴게시간을 등록해주세요");
		}
		
		if(holidayList.size() == 0) {
			throw new Exception("점포 공휴일 및 긴급 휴무를 등록해주세요");
		}
		
		if(offList.size() == 0) {
			throw new Exception("점포 휴무일을 등록해주세요");
		}
		
		if(openList.size() == 0) {
			throw new Exception("점포 영업시간을 등록해주세요");
		}
		
		RegVO ownerVO = new RegVO();
		ownerVO.setBuild_seq(Integer.parseInt(build_seq));
		ownerVO.setOwner_phone(owner_phone);
		ownerVO.setStore_name(store_name);
		ownerVO.setStore_keyword(store_keyword);
		ownerVO.setStore_text(store_text);
		ownerVO.setLcate_seq(Integer.parseInt(lcate_seq));
		ownerVO.setMcate_seq(Integer.parseInt(mcate_seq));
		ownerVO.setStore_tel(store_tel);
		ownerVO.setMod_id(username);
		ownerVO.setOwner_email(owner_email);
		ownerVO.setStore_ho(store_ho);
		ownerVO.setStore_floor(Integer.parseInt(floor_seq));
		
		Integer owner_id = null;
		
		String ownerIdFlag = regMapper.checkOwnerId(Integer.parseInt(pre_owner_id));
		if("N".equals(ownerIdFlag)) {
			owner_id = Integer.parseInt(pre_owner_id);
			ownerVO.setOwner_id(String.valueOf(owner_id));
			regMapper.updateOwnerInfo(ownerVO);
		}else {
			owner_id = regMapper.insertOwnerInfo(ownerVO);
			ownerVO.setOwner_id(String.valueOf(owner_id));
		}
		
		//	점포 상품 등록
		for(int i=0; i<productList.size();i++) {
			HashMap productMap = (HashMap) productList.get(i);
			HashMap fileMap = (HashMap) productMap.get("file");
			
			CommonFileVO vo = new CommonFileVO();
			vo.setAdd_id(username);
			Integer file_seq = commonFileService.insertFileSeq(vo);
			
			CommonFileVO fileVO = getFileVO(fileMap);
			fileVO.setFile_seq(file_seq);
			fileVO.setAdd_id(username);
			fileVO.setFile_dtl_seq(0);
			
			commonFileService.insertFileDtl(fileVO);
			
			RegVO productVO = new RegVO();
			productVO.setOwner_id(String.valueOf(owner_id));
			productVO.setProduct_seq(i);
			productVO.setProduct_name(String.valueOf(productMap.get("product_name")));
			productVO.setProduct_price(Integer.parseInt(String.valueOf(productMap.get("product_price"))));
			productVO.setFile_seq(file_seq);
			
			regMapper.insertProduct(productVO);
		}

		//	점포 영업시간
		for(int i=0; i<openList.size(); i++) {
			HashMap openMap = (HashMap) openList.get(i);
			RegVO openVO = new RegVO();
			openVO.setOwner_id(String.valueOf(owner_id));
			openVO.setOpen_day(Integer.parseInt(String.valueOf(openMap.get("open_day"))));
			openVO.setOpen_time(String.valueOf(openMap.get("open_time")));
			openVO.setClose_time(String.valueOf(openMap.get("close_time")));
			
			regMapper.insertOpen(openVO);
		}
		
		//	점포 휴게 시간
		for(int i=0; i<breakList.size();i++) {
			Map map = (Map) breakList.get(i);
			RegVO vo = new RegVO();
			vo.setOwner_id(String.valueOf(owner_id));
			vo.setBreak_day(Integer.parseInt(String.valueOf(map.get("break_day"))));
			vo.setOpen_time(String.valueOf(map.get("open_time")));
			vo.setClose_time(String.valueOf(map.get("close_time")));	
			
			regMapper.insertBreak(vo);
		}
		
		//	점포 공휴일 및 긴급 휴무(휴가)
		for(int i=0; i<holidayList.size();i++) {
			Map map = (Map) holidayList.get(i);
			RegVO vo = new RegVO();
			vo.setOwner_id(String.valueOf(owner_id));
			vo.setHoliday_type(Integer.parseInt(String.valueOf(map.get("holiday_type"))));
			vo.setHoliday_start_day(String.valueOf(map.get("holiday_start_day")));
			vo.setHoliday_end_day(String.valueOf(map.get("holiday_end_day")));
			vo.setHoliday_seq(i);
			
			regMapper.insertHoliday(vo);
		}		
		
		//	점포 휴무일
		for(int i=0; i<offList.size(); i++) {
			Map map = (Map) offList.get(i);
			RegVO vo = new RegVO();
			vo.setOwner_id(String.valueOf(owner_id));
			vo.setDay_off_week(String.valueOf(map.get("day_off_week")));	
			vo.setDay_off_day(String.valueOf(map.get("day_off_day")));
			
			regMapper.insertDayOff(vo);
		}
		
		CommonFileVO fileVO = new CommonFileVO();
		fileVO.setAdd_id(username);
		
		//	사업자 등록증
		Integer busi_reg_file_seq = commonFileService.insertFileSeq(fileVO);
		CommonFileVO busiVO = getFileVO(owner_busi_img);
		busiVO.setFile_seq(busi_reg_file_seq);
		busiVO.setFile_dtl_seq(0);
		
		commonFileService.insertFileDtl(busiVO);
		
		//	매장 대표 사진
		Integer main_file_seq = commonFileService.insertFileSeq(fileVO);
		for(int i=0; i<ownerMainImgList.size();i++) {
			CommonFileVO mainVO = getFileVO( (HashMap)ownerMainImgList.get(i));
			mainVO.setFile_seq(main_file_seq);
			mainVO.setFile_dtl_seq(i);
			commonFileService.insertFileDtl(mainVO);	
		}
				
		ownerVO.setBusi_reg_file_seq(busi_reg_file_seq);
		ownerVO.setMain_file_seq(main_file_seq);		
		regMapper.updateOwnerImg(ownerVO);
		ownerVO.setMember_id(username);
		ownerVO.setOwner_pwd(StringUtil.getPasswordEncode(password));
		regMapper.insertMember(ownerVO);
	}//


	private CommonFileVO getFileVO(HashMap fileMap) {
		CommonFileVO vo = new CommonFileVO();
		vo.setFile_dtl_desc(String.valueOf(fileMap.get("file_dtl_desc")));
		vo.setFile_dtl_ext(String.valueOf(fileMap.get("file_dtl_ext")));
		vo.setFile_dtl_origin(String.valueOf(fileMap.get("file_dtl_origin")));
		vo.setFile_dtl_path(String.valueOf(fileMap.get("file_dtl_path")));
		vo.setFile_dtl_url_path(String.valueOf(fileMap.get("file_dtl_url_path")));
		vo.setFile_type(Integer.parseInt(String.valueOf(fileMap.get("file_type"))));
		return vo;
	}
}
