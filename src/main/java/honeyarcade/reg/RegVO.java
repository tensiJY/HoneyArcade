package honeyarcade.reg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class RegVO {
	//	회원 등록
	private String	member_id;
	private	String	owner_pwd;
	//	점포
	private String	owner_id;
	private String	owner_email;
	
	private Integer	build_seq;
	private Integer	floor_seq;
	private	Integer store_stauts;
	private Integer owner_status;
	private String	owner_phone;
	private String	store_link;
	private Integer	store_sort;
	private String	store_keyword;
	private String	store_text;
	private Integer lcate_seq;
	private Integer mcate_seq;
	private	String	store_tel;
	private String	store_name;
	private String	mod_id;
	private String	store_ho;
	private Integer	store_floor;
	
	//	상품
	private Integer	product_seq;
	private String	product_name;
	private	Integer	product_price;
	private Integer	file_seq;
	
	//	영업 시간
	private	Integer	open_day;
	private	String	open_time;
	private	String	close_time;
	
	//	점포 휴게 시간
	private Integer break_day;
	
	//	점포 휴무일
	private String	day_off_week;
	private String	day_off_day;
	
	//	점포 공휴일 및 긴급 휴가
	private	Integer	holiday_seq;
	private Integer holiday_type;
	private String	holiday_start_day;
	private String	holiday_end_day;
	
	//	사업자 등록증 이미지
	private	Integer	busi_reg_file_seq;
	
	//	매장 대표 사진
	private Integer	main_file_seq;
	
}
