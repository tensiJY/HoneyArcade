package honeyarcade.common.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import honeyarcade.login.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomMail {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Value("${mail.sender}")
	private String mail_sender;
	
	/**
	 * 메일 전송 : 아이디 찾기
	 * @param userVO
	 * @param owner_email
	 * @return
	 * @throws Exception
	 */
	public boolean find_id(UserVO userVO)throws Exception{
		boolean result = true;
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			//	메일 제목 설정
			helper.setSubject("[허니아케이드] 아이디 찾기");
			//	수신자 설정
			helper.setTo(userVO.getOwner_email());
			helper.setFrom(mail_sender);
			//	템플릿에 전달할 테이터 설정
			Context context = new Context();
			context.setVariable("member_id", userVO.getMember_id());
			//	메일 내용 설정 템플릿
			String html = templateEngine.process("email/email-findid", context);
			helper.setText(html, true);
			//	메일 보내기
			javaMailSender.send(message);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
			
		}finally {
			return result;
		}
	}
	
	/**
	 * 메일 전송 : 비밀번호 찾기
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	public boolean find_pwd(UserVO userVO) throws Exception{
		boolean result = true;
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			//	메일 제목 설정
			helper.setSubject("[허니아케이드] 비밀번호 찾기");
			//	수신자 설정
			helper.setTo(userVO.getOwner_email());
			helper.setFrom(mail_sender);
			//	템플릿에 전달할 테이터 설정
			Context context = new Context();
			context.setVariable("member_id", userVO.getMember_id());
			context.setVariable("new_owner_pwd", userVO.getNew_owner_pwd());
			//	메일 내용 설정 템플릿
			String html = templateEngine.process("email/email-password", context);
			helper.setText(html, true);
			//	메일 보내기
			javaMailSender.send(message);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}finally {
			return result;
		}
	}
	
	
}
