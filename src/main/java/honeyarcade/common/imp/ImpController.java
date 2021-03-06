package honeyarcade.common.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import honeyarcade.util.SessionUtil;
import honeyarcade.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImpController {

	@Value("${imp.uid}")
	private String imp_uid;
	
	@Autowired
	private ImpService impService;
	
	/**
	 * 결제 사용자 아이디 가져오기
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/common/pay/getImpUid", method=RequestMethod.POST)
	public ResponseEntity getImpUid() throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		try {
			resultMap.put("imp_uid", imp_uid);
		}catch(Exception e) {
			log.debug(e.toString());
			result = false;
			msg = e.getMessage();
			resultMap.put("msg", msg);
		}finally {
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 결제 정보 가져오기 : 결제 키
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/common/pay/getPayInfo", method=RequestMethod.POST)
	public ResponseEntity getPayInfo(@RequestBody Map dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		try {
			Integer landom_length = Integer.parseInt(String.valueOf(dataMap.get("landom_length")));
			List	evtSeqList = (List) dataMap.get("evt_seq_arr");
			//	결제 키 생성
			boolean uidCheck = true;
			String uid = StringUtil.createMerchantId(landom_length);
			while(uidCheck) {
				Integer isKey = impService.isKey(String.valueOf(uid));
				if(isKey >0) {
					uid = StringUtil.createMerchantId(landom_length);
				}else {
					break;
				}
			}
			
			//	결제 상품 조회
			List<ImpVO> evt_list = impService.getEvtList(evtSeqList);
			
			//	결제 상품 세부정보 조회
			List<ImpVO> evt_dtl_list = impService.getEvtDtlList(evtSeqList);
			
			Integer total_price = 0;
			Integer total_ea = evt_list.size();
			String	product_title = null;
			
			for(int i=0; i<evt_list.size(); i++) {
				ImpVO temp =  evt_list.get(i);
				total_price += temp.getEvent_price();
			}
			
			//	결제 품목 생성
			ImpVO temp = evt_list.get(0);
			product_title = temp.getEvent_title();
			if(evt_list.size() != 1) {
				product_title = product_title + " 외 " + (evt_list.size()-1);
			}
			
			//	결제자 정보 조회
			String owner_id = SessionUtil.getOwnerId();
			
			ImpVO userVO = impService.getOwnerInfo(owner_id);
			
			resultMap.put("uid", uid);
			resultMap.put("total_price", total_price);
			resultMap.put("product_title", product_title);
			resultMap.put("total_ea", total_ea);
			resultMap.put("owner_info", userVO);
			resultMap.put("evt_list", evt_list);
			resultMap.put("evt_dtl_list", evt_dtl_list);
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(dataMap.toString());
			
			result = false;
			msg = e.getMessage();
			resultMap.put("msg", msg);
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 결제 프로세스 저장
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/common/pay/complete")
	public ResponseEntity complete(@RequestBody Map dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		log.info("결제 처리  >> DB 처리");
		log.info("data : " + dataMap.toString());
		try {
			impService.completeProc(dataMap);
		}catch(Exception e) {
			log.debug(e.getMessage());
			result = false;
			msg = "결제 처리 중 오류가 발생하였습니다. 관리자에게 문의 바랍니다";
			resultMap.put("msg", msg);
		}finally {
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 취소 컨트롤러
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/common/pay/cancelPay", method=RequestMethod.POST)
	public ResponseEntity cancelPay(@RequestBody Map dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		try {
			//	구현 없음
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(dataMap.toString());
			
			result = false;
			msg = e.getMessage();
			resultMap.put("msg", msg);
			
		}finally {
			resultMap.put("result", result);	
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
