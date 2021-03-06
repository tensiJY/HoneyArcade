package honeyarcade.common.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CommonApiVO {
	private Integer sido_seq;		//	시도 시퀀스
	private String 	sido_name;		//	시도 명
	private Integer sigungu_seq;	//	시군구 시퀀스
	private String 	sigungu_name;	//	시군구 명
	private Integer dong_seq;		//	동읍면 시퀀스
	private String 	dong_name;		//	동명
	private String 	admin_id;		//	관리자 아이디
	private Integer build_seq;		//	빌드 시퀀스
	private String 	build_name;		//	건물 명
	private Integer floor_seq;		//	층
	private Integer floor_type;		//	1 지상 2지하
	private String	owner_id;		//	점주 아이디
	private String	store_name;		//	상점 명
	private String	store_ho;		//	호
	private Integer	lcate_seq;		//	대 카테고리 
	private String	lcate_name;		//	대 카테고리 명
	private Integer	mcate_seq;		//	소 카테고리
	private String	mcate_name;		//	소 카테고리 명
	private Integer	store_flag;		//	1 사용(점주가 있음),  2 미사용:(점주가없음)	-> (상점에 점주 아이디 있는지 확인)
	private String	ho_type;
	
}
