package honeyarcade.owner.ad.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerAdProService {

	@Autowired
	private OwnerAdProMapper ownerAdProMapper;
	
	/**
	 * 프로모션 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdProVO> getEventList() throws Exception{
		return ownerAdProMapper.getEventList();
	}

}
