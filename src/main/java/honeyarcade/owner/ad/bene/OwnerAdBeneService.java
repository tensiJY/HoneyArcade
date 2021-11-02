package honeyarcade.owner.ad.bene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerAdBeneService {
	
	@Autowired
	private OwnerAdBeneMapper ownerAdBeneMapper;

	/**
	 * 혜택사항 : 이미지 가져오기
	 * @return
	 * @throws Exception
	 */
	public OwnerAdBeneVO getBeneInfo() throws Exception {
		return ownerAdBeneMapper.getBeneInfo();
	}

}
