package honeyarcade.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import honeyarcade.common.email.CustomMail;
import honeyarcade.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CustomMail customMail;
	
	/**
	 * 로그인
	 * @param model
	 * @return
	 */
	@RequestMapping("/login/form")
	public String loginForm(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception{
		return "login/loginForm";
	}
	
	/**
	 * 아이디 찾기
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login/findid")
	public ResponseEntity findid(@RequestBody HashMap dataMap) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			String owner_email = String.valueOf(dataMap.get("owner_email"));
			UserVO queryVO = new UserVO();
			queryVO.setOwner_email(owner_email);
			
			UserVO userVO = customUserDetailsService.getOwnerId(queryVO);
			if(null == userVO) {
				result = false;
				msg = "등록된 ID를 찾을 수가 없습니다";
			}else {
				boolean res = customMail.find_id(userVO);
				if(res == false) {
					result = false;
					msg = "[아이디찾기] 메일 전송에 실패하였습니다";
				}else {
					msg = "[아이디찾기] 메일 전송에 성공하였습니다";
				}
			}
		}catch(Exception e) {
			result = false;
			msg = "서버 처리 중 에러가 발생하였습니다. 관리자에게 문의 바랍니다";
			log.error("아이디 찾기 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("msg", msg);
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 비밀번호 찾기
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login/findpwd")
	public ResponseEntity findpwd(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			String owner_email	= String.valueOf(dataMap.get("owner_email"));
			String member_id 	= String.valueOf(dataMap.get("member_id"));
			UserVO queryVO = new UserVO();
			queryVO.setMember_id(member_id);
			queryVO.setOwner_email(owner_email);
			Integer count = customUserDetailsService.existsOwner(queryVO);
			if(count == 0) {
				result = false;
				msg = "등록된 계정을 찾을 수 없습니다";
			}else {
				String new_owner_pwd = StringUtil.getRandomSpecialEnglishNumberStr(8);
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String crypt_owner_pwd = bc.encode(new_owner_pwd);
				queryVO.setNew_owner_pwd(new_owner_pwd);
				queryVO.setCrypt_owner_pwd(crypt_owner_pwd);
				boolean res = customMail.find_pwd(queryVO);
				if(res == false) {
					result = false;
					msg = "[비밀번호찾기] 메일 전송에 실패하였습니다";
				}else {
					
					customUserDetailsService.updateOwnerPwd(queryVO);
					msg = "[비밀번호찾기] 메일 전송에 성공하였습니다";
				}
			}
		}catch(Exception e) {
			result = false;
			msg = "서버 처리 중 에러가 발생하였습니다. 관리자에게 문의 바랍니다";
			log.error("비밀번호 찾기 에러");
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("msg", msg);
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
