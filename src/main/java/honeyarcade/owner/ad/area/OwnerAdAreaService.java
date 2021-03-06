package honeyarcade.owner.ad.area;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.common.api.CommonApiService;
import honeyarcade.common.api.CommonApiVO;
import honeyarcade.common.file.CommonFileService;
import honeyarcade.common.file.CommonFileVO;
import honeyarcade.util.SessionUtil;

@Service
public class OwnerAdAreaService {

	@Autowired
	private OwnerAdAreaMapper ownerAdAreaMapper;
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private CommonFileService commonFileService;
	
	/**
	 * 지역광고 : 지역 광고 신청권 리스트 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdAreaVO> getAreaList(String owner_id) throws Exception{
		return ownerAdAreaMapper.getAreaList(owner_id);
	}
	
	/**
	 * 지역광고 : 광고 요청 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})	
	public void reqProc(HashMap dataMap) throws Exception {
		String	ad_req_type	= String.valueOf(dataMap.get("pay_dtl_type"));	//	ad_req_type
		String	ad_req_text = String.valueOf(dataMap.get("ad_req_text"));
		String	ad_req_day  = String.valueOf(dataMap.get("pay_dtl_day"));
		String	ad_seq 		= String.valueOf(dataMap.get("pay_seq"));		//	ad_seq
		String	owner_id	= SessionUtil.getOwnerId();
		String	member_id	= SessionUtil.getUsername();
		
		List 	fileList 	= (List) dataMap.get("file");
		if(fileList.size() == 0) {
			throw new Exception("이미지 파일이 없습니다");
		}

		CommonFileVO cvo = new CommonFileVO();
		cvo.setAdd_id(member_id);
		Integer file_seq = commonFileService.insertFileSeq(cvo);
		for(int i=0; i<fileList.size(); i++) {
			HashMap fileMap = (HashMap) fileList.get(i);
			CommonFileVO fileVO = getFileVO(fileMap);
			fileVO.setAdd_id(member_id);
			fileVO.setFile_seq(file_seq);
			fileVO.setFile_dtl_seq(i);
			commonFileService.insertFileDtl(fileVO);
		}
		
		//	시도, 시군구, 동읍면, 건물 시퀀스 조회
		CommonApiVO areaVO =  commonApiService.getAreaInfoOfOwnerId(owner_id);
		OwnerAdAreaVO paramVO = new OwnerAdAreaVO();
		paramVO.setAd_req_id(owner_id);
		paramVO.setAd_seq(Integer.parseInt(ad_seq));
		paramVO.setAd_req_type(Integer.parseInt(ad_req_type));
		paramVO.setAd_req_text(ad_req_text);
		paramVO.setFile_seq(file_seq);
		paramVO.setAd_req_day(Integer.parseInt(ad_req_day));
		paramVO.setSido_seq(areaVO.getSido_seq());
		paramVO.setSigungu_seq(areaVO.getSigungu_seq());
		paramVO.setDong_seq(areaVO.getDong_seq());
		paramVO.setBuild_seq(areaVO.getBuild_seq());
		
		//	광고 요청한 데이터가 있는지 확인
		OwnerAdAreaVO adVO = ownerAdAreaMapper.getAdReq(paramVO);
		//	지역광고 요청
		if(adVO == null) {
			//	데이터가 없는 경우 인서트
			ownerAdAreaMapper.insertAdReq(paramVO);
		}else {
			//	데이터가 있는 경우 업데이트
			CommonFileVO fvo = new CommonFileVO();
			fvo.setFile_seq(adVO.getFile_seq());
			fvo.setMod_id(member_id);
			commonFileService.delFileSeq(fvo);
			paramVO.setAd_req_total(adVO.getAd_req_total());
			ownerAdAreaMapper.updateAdReq(paramVO);
		}
		//	쿠폰 사용 체크
		ownerAdAreaMapper.updatePayDtl(paramVO);
	}
	
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
