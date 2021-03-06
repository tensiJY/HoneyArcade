package honeyarcade.owner.st.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerStMypageService {
	
	@Autowired
	private OwnerStMypageMapper ownerStMypageMapper; 

	/**
	 * 지역광고 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStMypageVO> getAreaList(OwnerStMypageVO paramVO) throws Exception{
		return ownerStMypageMapper.getAreaList(paramVO);
	}

	/**
	 * 건물광고 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStMypageVO> getBuildList(OwnerStMypageVO paramVO) throws Exception {
		return ownerStMypageMapper.getBuildList(paramVO);
	}

	/**
	 * 쿠폰 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStMypageVO> getCouponList(OwnerStMypageVO paramVO) throws Exception {
		return ownerStMypageMapper.getCouponList(paramVO);
	}

	/**
	 * 일별 차트 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStMypageVO> getChartData(OwnerStMypageVO paramVO) throws Exception{
		return ownerStMypageMapper.getChartData(paramVO);
	}

}
