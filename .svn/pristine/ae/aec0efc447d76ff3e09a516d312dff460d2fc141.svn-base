package honeyarcade.owner.ad.area;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerAdAreaMapper {

	/**
	 * 지역광고 : 지역 광고 신청권 리스트 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdAreaVO> getAreaList(String owner_id) throws Exception;

	/**
	 * 광고 요청 조회 
	 * @param ownerAdAreaVO
	 * @return
	 * @throws Exception
	 */
	public OwnerAdAreaVO getAdReq(OwnerAdAreaVO ownerAdAreaVO) throws Exception;

	/**
	 * 지역 광고 요청인서트
	 * @param ownerAdAreaVO
	 * @throws Exception
	 */
	public void insertAdReq(OwnerAdAreaVO ownerAdAreaVO) throws Exception;
	
	/**
	 * 지역 광고 업데이트(반려 > 미승인)
	 * @param ownerAdAreaVO
	 * @throws Exception
	 */
	public void updateAdReq(OwnerAdAreaVO ownerAdAreaVO) throws Exception;

	/**
	 * 광고 사용여부 테이블 업데이트
	 * @param ownerAdAreaVO
	 * @throws Exception
	 */
	public void updatePayDtl(OwnerAdAreaVO ownerAdAreaVO) throws Exception;

}
