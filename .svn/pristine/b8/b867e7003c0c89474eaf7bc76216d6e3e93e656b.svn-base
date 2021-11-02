package honeyarcade.reg;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegMapper {

	/**
	 * 회원 가입 : 아이디 중복 체크
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public Integer idCheck(String owner_id) throws Exception;

	/**
	 * 회원 가입 : 이메일 중복 체크
	 * @param owner_email
	 * @return
	 * @throws Exception
	 */
	public Integer emailCheck(String owner_email) throws Exception;

	/**
	 * 회원 가입 : 상품 등록
	 * @param regVO
	 * @throws Exception
	 */
	public void insertProduct(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : 점포 영업시간 등록
	 * @param regVO
	 */
	public void insertOpen(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : 점포 휴게 시간 등록
	 * @param regVO
	 * @throws Exception
	 */
	public void insertBreak(RegVO regVO) throws Exception;
	
	/**
	 * 회원 가입 : 점포 휴무일 등록
	 * @param regVO
	 * @throws Exception
	 */
	public void insertDayOff(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : 점포 공휴일 및 긴급 휴무(휴가)
	 * @param regVO
	 * @throws Exception
	 */
	public void insertHoliday(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : 점포 업데이트 : 메인사진, 사업자 등록증
	 * @param regVO
	 * @throws Exception
	 */
	public void updateOwnerImg(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : 아이디 등록
	 * @param regVO
	 * @throws Exception
	 */
	public void insertMember(RegVO regVO) throws Exception;

	/**
	 * 회원 가입 : owner_id 키 생성
	 * @param ownerVO
	 * @return
	 * @throws Exception
	 */
	public Integer insertOwnerInfo(RegVO ownerVO) throws Exception;

	/**
	 * owner_id 사용 여부 체크 
	 * del_flag = 'N' 인경우 사용가능, 'Y'인 경우 owner_id키 생성
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public String checkOwnerId(int parseInt) throws Exception;

	/**
	 * owner_id를 이용하여 호실 업데이트
	 * @param ownerVO
	 * @throws Exception
	 */
	public void updateOwnerInfo(RegVO ownerVO) throws Exception;

}
