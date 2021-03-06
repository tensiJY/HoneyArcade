package honeyarcade.common.imp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class ImpVO {

	private Integer	event_seq;
	private String	event_title;
	private	Integer	event_price;
	private String	event_text;
	private String	member_id;
	private String	owner_email;
	private String	store_name;
	private String	owner_phone;
	private String	owner_id;
	//	결제 키
	private String  imp_uid;
	private String	merchant_uid;
	//	프로모션 세부정보
	private String	event_dtl_type;
	private String	event_dtl_day;
	//	프로모션 결제 
	private Integer	pay_money;
	private Integer	pay_seq;
	private Integer pay_dtl_type;
	private Integer pay_dtl_day;
}
