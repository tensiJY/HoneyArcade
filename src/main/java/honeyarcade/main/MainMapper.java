package honeyarcade.main;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
	
	/**
	 * 회사소개 이미지 가져오기
	 * @return
	 * @throws Exception
	 */
	public MainVO getHoneyInfo() throws Exception;
	
}
