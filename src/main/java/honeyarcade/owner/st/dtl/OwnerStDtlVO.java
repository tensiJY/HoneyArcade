package honeyarcade.owner.st.dtl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class OwnerStDtlVO {
	
	//	지역
	private Integer sido_seq;
	private String  sido_name;
	private Integer sigungu_seq;
	private String  sigungu_name;
	private Integer dong_seq;
	private String  dong_name;
	private Integer	build_seq;
	private Integer floor_seq;

	//	정보
	private String	owner_id;
	private String	store_name;
	private Integer	used_money;
	private Integer	remain_build;
	private Integer remain_area;
	private Integer remain_coupon;
	private Integer	owner_status;
	private String	owner_email;
	private String	owner_phone;
	private Integer	store_floor;
	//private String	store_link;		//	유튜브 URL
	private String	store_ho;		//	점포 호실
	private Integer	lcate_seq;
	private Integer mcate_seq;
	private String	store_keyword;	//	키워드
	private String	store_text;		//	한줄소개
	private Integer	store_sort;		//	노출 순위
	private Integer	busi_reg_file_seq;
	private Integer main_file_seq;
	private Integer pro_file_seq;	//	프로필
	private Integer	store_status;
	private String	store_tel;

	//	상품 변수
	private	String	product_name;
	private Integer	product_seq;
	private Integer	product_price;
	
	//	오픈 시간
	private Integer open_day;
	private String	open_time;
	private String	close_time;
	private String	open_day_text;
	
	//	휴게 시간
	private Integer	break_day;
	private String	break_day_text;
	
	//	점포 휴무일
	private String	day_off_week;
	private String	day_off_day;
	private String	week_text;
	private String	day_text;
	
	//	파일
	private	String	file_dtl_desc;
	private	String	file_dtl_ext;
	private	String	file_dtl_origin;
	private	String	file_dtl_path;
	private String	file_dtl_url_path;
	private	Integer	file_type;
	private	Integer	file_seq;
	private	Integer	holiday_seq;
	private Integer	holiday_type;
	private String	holiday_start_day;
	private String	holiday_end_day;
	private String	mod_id;
}
