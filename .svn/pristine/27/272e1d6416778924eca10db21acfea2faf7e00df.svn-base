package honeyarcade.owner.ad.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerAdCouponMapper {

	/**
	 * 할인쿠폰 : 할인쿠폰 목록 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdCouponVO> getCouponList(String owner_id) throws Exception;

	/**
	 * 광고 요청 조회 
	 * @param OwnerAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public OwnerAdCouponVO getAdReq(OwnerAdCouponVO OwnerAdCouponVO) throws Exception;

	/**
	 * 지역 광고 요청인서트
	 * @param OwnerAdCouponVO
	 * @throws Exception
	 */
	public void insertAdReq(OwnerAdCouponVO OwnerAdCouponVO) throws Exception;
	
	/**
	 * 지역 광고 업데이트(반려 > 미승인)
	 * @param OwnerAdCouponVO
	 * @throws Exception
	 */
	public void updateAdReq(OwnerAdCouponVO OwnerAdCouponVO) throws Exception;

	/**
	 * 광고 사용여부 테이블 업데이트
	 * @param OwnerAdCouponVO
	 * @throws Exception
	 */
	public void updatePayDtl(OwnerAdCouponVO OwnerAdCouponVO) throws Exception;
}
