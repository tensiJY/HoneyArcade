package honeyarcade.owner.ad.build;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class OwnerAdBuildVO {
	
	private	String	event_title;
	private Integer	pay_seq;
	private Integer	pay_dtl_type;
	private Integer	pay_dtl_day;
	private Integer	pay_dtl_use;
	private Integer	sido_seq;
	private Integer	sigungu_seq;
	private Integer dong_seq;
	private Integer build_seq;
	private String	owner_id;
	private	Integer ad_seq;
	private Integer ad_req_type;
	private String	ad_req_id;
	private	String 	ad_req_text;
	private Integer	ad_req_day;
	private Integer ad_req_total;
	private Integer	file_seq;

}
