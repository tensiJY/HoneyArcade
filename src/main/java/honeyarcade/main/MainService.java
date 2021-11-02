package honeyarcade.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

	@Autowired
	private MainMapper mainMapper;

	/**
	 * 회사소개 이미지 가져오기
	 * @return
	 * @throws Exception
	 */
	public MainVO getHoneyInfo() throws Exception{
		return mainMapper.getHoneyInfo();
	}
}
