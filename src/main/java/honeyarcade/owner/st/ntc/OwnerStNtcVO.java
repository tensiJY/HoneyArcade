package honeyarcade.owner.st.ntc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
public class OwnerStNtcVO {

	//	목록 변수
	private Integer	row_num;
	private Integer nowPage;
	private Integer	start_num;
	private Integer	end_num;
	private Integer	row_num_desc;
	private String	type;
	private String	search_text;
	private String 	ntc_title;
	private String	ntc_text;
	private String	add_dt;
	private Integer ntc_seq;
	private Integer diff;
	
}
