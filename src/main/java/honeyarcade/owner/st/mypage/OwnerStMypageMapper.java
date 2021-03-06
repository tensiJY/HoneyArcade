package honeyarcade.owner.st.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerStMypageMapper {
	
	/**
	 * 지역광고 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	List<OwnerStMypageVO> getAreaList(OwnerStMypageVO paramVO) throws Exception;

	/**
	 * 건물광고 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	List<OwnerStMypageVO> getBuildList(OwnerStMypageVO paramVO) throws Exception;

	/**
	 * 쿠폰 현황 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	List<OwnerStMypageVO> getCouponList(OwnerStMypageVO paramVO) throws Exception;

	/**
	 * 일별 차트 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	List<OwnerStMypageVO> getChartData(OwnerStMypageVO paramVO) throws Exception;

}
