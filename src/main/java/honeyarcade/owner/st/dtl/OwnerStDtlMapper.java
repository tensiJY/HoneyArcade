package honeyarcade.owner.st.dtl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import honeyarcade.reg.RegVO;

@Mapper
public interface OwnerStDtlMapper {

	/**
	 * 사용자 정보 가져오기
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public OwnerStDtlVO getOwnerInfo(String owner_id) throws Exception;

	/**
	 * 상품 정보 목록 
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getProductList(String owner_id) throws Exception;

	/**
	 * 상점 오픈시간
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getOpenDay(String owner_id) throws Exception;

	/**
	 * 상점 휴게시간
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getBreakDay(String owner_id) throws Exception;

	/**
	 * 상점 휴무일
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getDayOffList(String owner_id) throws Exception;

	/**
	 * 휴가 설정 (긴급 휴무)
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStDtlVO> getHolidayList(String owner_id) throws Exception;

	/**
	 * 상점 오프시간 삭제
	 * @param owner_id
	 * @throws Exception
	 */
	public void deleteOpen(String owner_id) throws Exception;

	/**
	 * 상점 휴게시간 삭제
	 * @param owner_id
	 * @throws Exception
	 */
	public void deleteBreak(String owner_id) throws Exception;

	/**
	 * 상점 휴무일 삭제
	 * @param owner_id
	 * @throws Exception
	 */
	public void deleteDayOff(String owner_id) throws Exception;

	/**
	 * 상점 공휴일 및 긴급 휴무 삭제
	 * @param owner_id
	 * @throws Exception
	 */
	public void deleteHoliday(String owner_id) throws Exception;

	/**
	 * 상점 상품 삭제
	 * @param owner_id
	 * @throws Exception
	 */
	public void deleteProduct(String owner_id) throws Exception;

	/**
	 * 상점 영업시간 등록
	 * @param openVO
	 * @throws Exception
	 */
	public void insertOpen(OwnerStDtlVO openVO) throws Exception;

	/**
	 * 상점 휴게 시간
	 * @param vo
	 * @throws Exception
	 */
	public void insertBreak(OwnerStDtlVO vo) throws Exception;

	/**
	 * 상점 공휴일 및 긴급 휴무 등록
	 * @param vo
	 * @throws Exception
	 */
	public void insertHoliday(OwnerStDtlVO vo) throws Exception;

	/**
	 * 상점 휴무일
	 * @param vo
	 * @throws Exception
	 */
	public void insertDayOff(OwnerStDtlVO vo) throws Exception;

	/**
	 * 상품 등록
	 * @param productVO
	 * @throws Exception
	 */
	public void insertProduct(OwnerStDtlVO productVO) throws Exception;

	/**
	 * 점포 상점 수정
	 * @param ownerVO
	 * @throws Exception
	 */
	public void updateOwner(OwnerStDtlVO ownerVO) throws Exception;

	

}
