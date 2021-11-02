package honeyarcade.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UserVO {

	private String	QRLocation;
	private String	type;
	
	private String	search_text;
	private String	build_name;
	
	private String	store_name;
	private String	floor_name;
	private	Integer	build_seq;
	private String	owner_id;
	private	Integer	floor_seq;
	private	Integer	store_floor;
	private	String	file_dtl_url_path;
	private	String	coupon_dtl_text;
	private	String	position;
	private Integer	banner_seq;
	private String	banner_img;
	private String	banner_main_img;
	private	String	banner_start_day;
	private String	banner_end_day;
	private	Integer	banner_sort;
}
