package honeyarcade.common.file;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonFileVO {
	private MultipartFile file;
	private MultipartFile[] files;
	private Integer file_seq;			//	파일 시퀀스
	private Integer file_dtl_seq;		//	파일 디테일 시퀀스
	private int file_type;				//	파일 업로드 타입
	private String file_dtl_path;		//	파일 패스
	private String file_dtl_desc;		//	파일 변경 후 이름
	private String file_dtl_origin;		//	파일 변경 전 이름
	private String admin_id;			//	관리자 아이디
	private String add_id;				//	작성자 아이디
	private String mod_id;				//	수정자 아이디
	private String file_dtl_ext;		//	파일 확장자
	private String file_dtl_url_path;	//	URL 저장
	private String pre_url;				//	URL 주소
	
}
