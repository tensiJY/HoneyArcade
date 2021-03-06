package honeyarcade.common.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImpService {

	@Autowired
	private ImpMapper impMapper;
	
	/**
	 * 결제 키 중복 조회
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Integer isKey(String uid) throws Exception{
		return impMapper.isKey(uid);
	}

	/**
	 * 프로모션 목록 조회
	 * @param evtSeqList
	 * @return
	 * @throws Exception
	 */
	public List<ImpVO> getEvtList(List evtSeqList) throws Exception {
		return impMapper.getEvtList(evtSeqList);
	}

	/**
	 * 결제자 정보 조회
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public ImpVO getOwnerInfo(String owner_id) throws Exception {
		return impMapper.getOwnerInfo(owner_id);
	}

	/**
	 * 프로모션 세부 목록 조회
	 * @param evtSeqList
	 * @return
	 * @throws Exception
	 */
	public List<ImpVO> getEvtDtlList(List evtSeqList) throws Exception{
		return impMapper.getEvtDtlList(evtSeqList);
	}

	/**
	 * 결제 프로세스 저장
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void completeProc(Map dataMap) throws Exception {
		//		결제자의 정보 및 결제 상품
		HashMap infoMap = (HashMap) dataMap.get("info");
		//	1. 결제자 정보
		HashMap ownerInfoMap = (HashMap) infoMap.get("owner_info");
		//	2. 결제 상품
		List evt_list = (List) infoMap.get("evt_list");
		//	3. 결제 상품 세부정보
		List evt_dtl_list = (List) infoMap.get("evt_dtl_list");	
		//	사용자 아이디
		String owner_id		= String.valueOf(ownerInfoMap.get("owner_id"));
		//	아임포트 결제후 리턴 받은 데이터
		HashMap repMap			= (HashMap) dataMap.get("rep");
		String	imp_uid 		= String.valueOf(repMap.get("imp_uid"));		//	아임포트 고유 키값
		String 	merchant_uid	= String.valueOf(repMap.get("merchant_uid"));	//	시스템에서 생성한 고유 키
		
		for(int i=0; i<evt_list.size(); i++) {
			HashMap evtMap 		= (HashMap) evt_list.get(i);
			Integer	event_seq 	= Integer.parseInt(String.valueOf(evtMap.get("event_seq")));
			Integer pay_money 	= Integer.parseInt(String.valueOf(evtMap.get("event_price")));
			String	event_title = String.valueOf(evtMap.get("event_title"));
			String	event_text 	= String.valueOf(evtMap.get("event_text"));
			ImpVO payVO = new ImpVO();
			payVO.setEvent_seq(event_seq);
			payVO.setOwner_id(owner_id);
			payVO.setPay_money(pay_money);
			payVO.setEvent_title(event_title);
			payVO.setEvent_text(event_text);
			payVO.setMerchant_uid(merchant_uid);
			payVO.setImp_uid(imp_uid);
			//	프로모션 결제 테이블에 저장
			Integer pay_seq = impMapper.insertPay(payVO); 
			for(int j=0; j<evt_dtl_list.size(); j++) {
				HashMap evtDtlMap = (HashMap) evt_dtl_list.get(j);
				Integer evt_seq = Integer.parseInt(String.valueOf(evtDtlMap.get("event_seq")));
				if(event_seq != evt_seq) {
					continue;
				}
				Integer pay_dtl_type	= Integer.parseInt(String.valueOf(evtDtlMap.get("event_dtl_type")));
				Integer pay_dtl_day		= Integer.parseInt(String.valueOf(evtDtlMap.get("event_dtl_day")));
				ImpVO payDtlVO = new ImpVO();
				payDtlVO.setPay_dtl_type(pay_dtl_type);
				payDtlVO.setPay_seq(pay_seq);
				payDtlVO.setPay_dtl_day(pay_dtl_day);
				impMapper.insertPayDtl(payDtlVO);
			}
		}//
	}

}
