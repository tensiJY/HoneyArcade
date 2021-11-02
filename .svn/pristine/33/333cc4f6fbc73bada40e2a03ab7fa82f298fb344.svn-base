package honeyarcade.owner.ad.build;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerAdBuildMapper {

	/**
	 * 건물 광고 : 건물 광고 신청권 목록 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdBuildVO> getBuildList(String owner_id) throws Exception;

	/**
	 * 광고 요청 조회 
	 * @param ownerAdBuildVO
	 * @return
	 * @throws Exception
	 */
	public OwnerAdBuildVO getAdReq(OwnerAdBuildVO ownerAdBuildVO) throws Exception;

	/**
	 * 지역 광고 요청인서트
	 * @param ownerAdBuildVO
	 * @throws Exception
	 */
	public void insertAdReq(OwnerAdBuildVO ownerAdBuildVO) throws Exception;
	
	/**
	 * 지역 광고 업데이트(반려 > 미승인)
	 * @param ownerAdBuildVO
	 * @throws Exception
	 */
	public void updateAdReq(OwnerAdBuildVO ownerAdBuildVO) throws Exception;

	/**
	 * 광고 사용여부 테이블 업데이트
	 * @param ownerAdBuildVO
	 * @throws Exception
	 */
	public void updatePayDtl(OwnerAdBuildVO ownerAdBuildVO) throws Exception;
}
