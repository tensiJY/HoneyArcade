package honeyarcade.ntc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NtcMapper {

	/**
	 * 공지사항: 공지사항 총 갯수 가져오기
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount(NtcVO ntcVO) throws Exception;

	/**
	 * 공지사항 : 공지사항 목록 가져오기
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	public List<NtcVO> getNtcList(NtcVO ntcVO) throws Exception;

	/**
	 * 공지사항 : 공지사항 상세 데이터 가져오기
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	public NtcVO getNtcView(NtcVO ntcVO) throws Exception;

	/**
	 * 공지사항 : 이전글 번호
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getPrevSeq(NtcVO ntcVO) throws Exception;

	/**
	 * 공지사항 : 다음글 번호
	 * @param ntcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getNextSeq(NtcVO ntcVO) throws Exception;

	
}
