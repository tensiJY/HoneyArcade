package honeyarcade.common.imp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImpMapper {

	/**
	 * 결제 키 중복 조회
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Integer isKey(String uid) throws Exception;

	/**
	 * 프로모션 목록 조회
	 * @param evtSeqList
	 * @return
	 * @throws Exception
	 */
	public List<ImpVO> getEvtList(List evtSeqList) throws Exception;

	/**
	 * 결제자 정보 조회
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public ImpVO getOwnerInfo(String owner_id) throws Exception;

	/**
	 * 프로모션 세부 목록 조회
	 * @param evtSeqList
	 * @return
	 * @throws Exception
	 */
	public List<ImpVO> getEvtDtlList(List evtSeqList) throws Exception;

	/**
	 * 프로모션 결제 테이블에 저장
	 * @param payVO
	 * @return
	 * @throws Exception
	 */
	public Integer insertPay(ImpVO impVO) throws Exception;

	/**
	 * 프로모션 결제 - 세부내용 저장
	 * @param payDtlVO
	 * @throws Exception
	 */
	public void insertPayDtl(ImpVO payDtlVO) throws Exception;

}
