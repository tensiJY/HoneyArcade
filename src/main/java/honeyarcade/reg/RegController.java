package honeyarcade.reg;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import honeyarcade.common.api.CommonApiService;
import honeyarcade.common.api.CommonApiVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RegController {
	
	@Autowired
	private RegService regService;
	
	@Autowired
	private CommonApiService commonApiService;
	
    @Autowired
    private MessageSource messageSource;
	
	@GetMapping("/reg/form")
	public String joinForm(Model model) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido();
		model.addAttribute("sidoList", sidoList);
		return "reg/regForm";
	}
	
	/**
	 * 회원가입 : 아이디 중복 체크
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg/isId")
	public ResponseEntity regisId(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		Integer type = null;	//	1: 동일ID가 존재합니다, 2: 사용할 수 있는 아이디입니다
		String member_id = String.valueOf(dataMap.get("member_id"));
		
		try {
			if(member_id == null || "".equals(member_id)) {
				msg = messageSource.getMessage("data.id.null", null, Locale.KOREA);
				throw new Exception(msg);
			}else {
				Integer count = regService.idCheck(member_id);
				if(count > 0) {
					type = 1;
					msg = messageSource.getMessage("data.id.exists", null, Locale.KOREA);
				}else {
					type = 2;
					msg = messageSource.getMessage("data.id.able", null, Locale.KOREA);
				}
			}
		}catch(Exception e) {
			result = false;
			msg = e.getMessage();
			log.error("아이디 중복 체크 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("type", type);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
			resultMap.put("member_id", member_id);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 이메일 중복체크
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg/isEmail")
	public ResponseEntity regIsEmail(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		Integer type = null;	//	1: 사용할 수 있는 이메일주소 입니다, 2: 사용할 수 없는 이메일주소 입니다
		String owner_email = String.valueOf(dataMap.get("owner_email"));
		try {
			if(owner_email == null || "".equals(owner_email)) {
				msg = messageSource.getMessage("data.email.null", null, Locale.KOREA);
				throw new Exception(msg);
			}else {
				Integer count = regService.emailCheck(owner_email);
				if(count > 0) {
					type = 1;
					msg = messageSource.getMessage("data.email.exists", null, Locale.KOREA);
				}else {
					type = 2;
					msg = messageSource.getMessage("data.email.able", null, Locale.KOREA);		
				}
			}
		}catch(Exception e) {
			result = false;
			msg = e.getMessage();
			log.error("아이디 중복 체크 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("type", type);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
			resultMap.put("owner_email", owner_email);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 회원가입 : 저장 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg/saveProc")
	public ResponseEntity saveProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
	
		try {
			log.info("점주 회원 가입 : " + dataMap.toString());	
			regService.saveProc(dataMap);
		}catch(Exception e) {
			result = false;
			msg = e.getMessage();
			log.error("회원 가입 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {

			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
}
