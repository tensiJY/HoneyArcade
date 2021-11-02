package honeyarcade.owner.st.dtl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import honeyarcade.common.file.CommonFileService;
import honeyarcade.common.file.CommonFileVO;
import honeyarcade.util.SessionUtil;

@Service
public class OwnerStDtlService {

	@Autowired
	private OwnerStDtlMapper ownerStDtlMapper;
	
	@Autowired
	private CommonFileService commonFileService;
	
	/**
	 * 사용자 정보 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public OwnerStDtlVO getOwnerInfo(String owner_id) throws Exception{
		return ownerStDtlMapper.getOwnerInfo(owner_id);
	}

	/**
	 * 상품 정보 목록 
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getProductList(String owner_id) throws Exception {
		return ownerStDtlMapper.getProductList(owner_id);
	}

	/**
	 * 상점 오픈시간
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getOpenDay(String owner_id) throws Exception {
		return ownerStDtlMapper.getOpenDay(owner_id);
	}

	/**
	 * 상점 휴게시간
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getBreakDay(String owner_id) throws Exception{
		return ownerStDtlMapper.getBreakDay(owner_id);
	}

	/**
	 * 상점 휴무일
	 * @param owner_id
	 * @return
	 */
	public List<OwnerStDtlVO> getDayOffList(String owner_id) throws Exception{
		return ownerStDtlMapper.getDayOffList(owner_id);
	}

	/**
	 * 휴가 설정 (긴급 휴무)
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getHolidayList(String owner_id) throws Exception {
		return ownerStDtlMapper.getHolidayList(owner_id);
	}

	public void saveProc(HashMap dataMap) throws Exception{
		String 	member_id = SessionUtil.getUsername();
		String	owner_id = SessionUtil.getOwnerId();
		String	mcate_seq = String.valueOf(dataMap.get("mcate_seq"));
		String	lcate_seq = String.valueOf(dataMap.get("lcate_seq"));
		String	store_keyword = String.valueOf(dataMap.get("store_keyword"));
		String	store_text	= String.valueOf(dataMap.get("store_text"));
		String	store_tel = String.valueOf(dataMap.get("store_tel"));
		String	store_name	= String.valueOf(dataMap.get("store_name"));
		String	owner_main_img_seq = String.valueOf(dataMap.get("owner_main_img_seq"));
		HashMap	owner_busi_img = (HashMap) dataMap.get("owner_busi_img");
						
		List productList = (List) dataMap.get("product");	//	점포 상품 등록
		List openList = (List)dataMap.get("open");			//	점포 영업시간
		List breakList = (List) dataMap.get("break");		//	점포 휴게시간
		List holidayList = (List) dataMap.get("holiday");	//	점포 공휴일 및 긴급 휴가	
		List offList = (List) dataMap.get("off");			//	점포 휴무일
		List delFileList = (List) dataMap.get("del_file");
		List owner_main_img = (List) dataMap.get("owner_main_img");
		
		if(owner_main_img.size() == 0) {
			throw new Exception("매장 대표 사진을 등록해주세요");
		}
		
		if(store_tel == "null" || "".equals(store_tel)) {
			throw new Exception("점포 전화번호를 등록해주세요");
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
		
		//	상점 오프시간 삭제
		ownerStDtlMapper.deleteOpen(owner_id);
			
		//	상점 휴게시간 삭제
		ownerStDtlMapper.deleteBreak(owner_id);
			
		//  상점 휴무일 삭제	
		ownerStDtlMapper.deleteDayOff(owner_id);
		
		//	상점 공휴일 및 긴급 휴무 삭제
		ownerStDtlMapper.deleteHoliday(owner_id);		
		
		//	상점 상품 삭제
		ownerStDtlMapper.deleteProduct(owner_id);
		
		//	파일 이미지 삭제
		if(delFileList.size() != 0) {
			for(int i=0; i<delFileList.size(); i++) {
				Integer file_seq = Integer.parseInt(String.valueOf(delFileList.get(i)));
				CommonFileVO vo = new CommonFileVO();
				vo.setFile_seq(file_seq);
				vo.setMod_id(member_id);
				
				commonFileService.delFileSeq(vo);
			}
		}
		
		//	상점 영업시간 등록
		for(int i=0; i<openList.size(); i++) {
			HashMap openMap = (HashMap) openList.get(i);
			OwnerStDtlVO openVO = new OwnerStDtlVO();
			openVO.setOwner_id(owner_id);
			openVO.setOpen_day(Integer.parseInt(String.valueOf(openMap.get("open_day"))));
			openVO.setOpen_time(String.valueOf(openMap.get("open_time")));
			openVO.setClose_time(String.valueOf(openMap.get("close_time")));
			
			ownerStDtlMapper.insertOpen(openVO);
		}
		
		//	상점 휴게 시간
		for(int i=0; i<breakList.size();i++) {
			Map map = (Map) breakList.get(i);
			OwnerStDtlVO vo = new OwnerStDtlVO();
			vo.setOwner_id(owner_id);
			vo.setBreak_day(Integer.parseInt(String.valueOf(map.get("break_day"))));
			vo.setOpen_time(String.valueOf(map.get("open_time")));
			vo.setClose_time(String.valueOf(map.get("close_time")));	
			
			ownerStDtlMapper.insertBreak(vo);
		}
		
		//	상점 공휴일 및 긴급 휴무 등록
		for(int i=0; i<holidayList.size();i++) {
			Map map = (Map) holidayList.get(i);
			OwnerStDtlVO vo = new OwnerStDtlVO();
			vo.setOwner_id(owner_id);
			vo.setHoliday_type(Integer.parseInt(String.valueOf(map.get("holiday_type"))));
			vo.setHoliday_start_day(String.valueOf(map.get("holiday_start_day")));
			vo.setHoliday_end_day(String.valueOf(map.get("holiday_end_day")));
			vo.setHoliday_seq(i);
			
			ownerStDtlMapper.insertHoliday(vo);
		}		
		
		//	상점 휴무일
		for(int i=0; i<offList.size(); i++) {
			Map map = (Map) offList.get(i);
			OwnerStDtlVO vo = new OwnerStDtlVO();
			vo.setOwner_id(owner_id);
			vo.setDay_off_week(String.valueOf(map.get("day_off_week")));	
			vo.setDay_off_day(String.valueOf(map.get("day_off_day")));
			
			ownerStDtlMapper.insertDayOff(vo);
		}
				
		//	상품 등록
		for(int i=0; i<productList.size(); i++) {
			HashMap productMap = (HashMap) productList.get(i);
			HashMap fileMap = (HashMap) productMap.get("file");
			CommonFileVO fileVO = getFileVO(fileMap);
			
			Integer file_seq;
			if(fileVO.getFile_seq() == null) {
				//	파일 SEQ  생성
				CommonFileVO vo = new CommonFileVO();
				vo.setAdd_id(member_id);
				file_seq = commonFileService.insertFileSeq(vo);
				
				//	파일 세부 정보 작성
				fileVO.setFile_seq(file_seq);
				fileVO.setAdd_id(member_id);
				fileVO.setFile_dtl_seq(0);
				
				commonFileService.insertFileDtl(fileVO);
			}else {
				file_seq = fileVO.getFile_seq();
			}
			
			//	상품 등록
			OwnerStDtlVO productVO = new OwnerStDtlVO();
			productVO.setOwner_id(owner_id);
			productVO.setProduct_seq(i);
			productVO.setProduct_name(String.valueOf(productMap.get("product_name")));
			productVO.setProduct_price(Integer.parseInt(String.valueOf(productMap.get("product_price"))));
			productVO.setFile_seq(file_seq);
			
			ownerStDtlMapper.insertProduct(productVO);
		}
		
		Integer busi_img_seq;
		Integer main_img_seq = Integer.parseInt(owner_main_img_seq);
		
		//	사업자 등록
		CommonFileVO busiVO = getFileVO(owner_busi_img);
		if(busiVO.getFile_seq() == null) {
			CommonFileVO vo = new CommonFileVO();
			vo.setAdd_id(member_id);
			busi_img_seq = commonFileService.insertFileSeq(vo);
			
			//	파일 세부 정보 작성
			busiVO.setFile_seq(busi_img_seq);
			busiVO.setAdd_id(member_id);
			busiVO.setFile_dtl_seq(0);
			commonFileService.insertFileDtl(busiVO);
			
		}else {
			busi_img_seq = busiVO.getFile_seq();
		}
		
		//	메인 이미지 디테일 삭제
		commonFileService.delDtlFileOfFileSeq(main_img_seq);
		//	메인 사진 등록
		for(int i=0; i<owner_main_img.size(); i++) {
			HashMap imgMap = (HashMap) owner_main_img.get(i);
			CommonFileVO imgVO = new CommonFileVO();
			imgVO.setFile_seq(main_img_seq);
			imgVO.setFile_dtl_seq(i);
			imgVO.setFile_type(Integer.parseInt(String.valueOf(imgMap.get("file_type"))));
			imgVO.setFile_dtl_path(String.valueOf(imgMap.get("file_dtl_path")));
			imgVO.setFile_dtl_desc(String.valueOf(imgMap.get("file_dtl_desc")));
			imgVO.setFile_dtl_origin(String.valueOf(imgMap.get("file_dtl_origin")));
			imgVO.setFile_dtl_ext(String.valueOf(imgMap.get("file_dtl_ext")));
			imgVO.setFile_dtl_url_path(String.valueOf(imgMap.get("file_dtl_url_path")));
			commonFileService.insertFileDtl(imgVO);
		}
		
		//	정보 업데이트
		OwnerStDtlVO ownerVO = new OwnerStDtlVO();
		ownerVO.setOwner_id(owner_id);
		ownerVO.setStore_tel(store_tel);			//	상점 전화
		ownerVO.setMain_file_seq(main_img_seq);		//	메인 사진
		ownerVO.setBusi_reg_file_seq(busi_img_seq);	//	사업자 등록증
		ownerVO.setLcate_seq(Integer.parseInt(lcate_seq));
		ownerVO.setMcate_seq(Integer.parseInt(mcate_seq));
		ownerVO.setStore_keyword(store_keyword);	//	
		ownerVO.setStore_text(store_text);
		ownerVO.setMod_id(member_id);
		ownerVO.setStore_name(store_name);
		ownerStDtlMapper.updateOwner(ownerVO);
	}

	
	private CommonFileVO getFileVO(HashMap fileMap) {
		CommonFileVO vo = new CommonFileVO();
		vo.setFile_dtl_desc(String.valueOf(fileMap.get("file_dtl_desc")));
		vo.setFile_dtl_ext(String.valueOf(fileMap.get("file_dtl_ext")));
		vo.setFile_dtl_origin(String.valueOf(fileMap.get("file_dtl_origin")));
		vo.setFile_dtl_path(String.valueOf(fileMap.get("file_dtl_path")));
		vo.setFile_dtl_url_path(String.valueOf(fileMap.get("file_dtl_url_path")));
		vo.setFile_type(Integer.parseInt(String.valueOf(fileMap.get("file_type"))));
		
		String fileSeq = String.valueOf(fileMap.get("file_seq"));
		Integer file_seq;
		
		if("".equals(fileSeq) || "null".equals(fileSeq)) {
			file_seq = null;
		}else{
			file_seq = Integer.parseInt(fileSeq);
		};
		vo.setFile_seq(file_seq);
		return vo;
	}
}
