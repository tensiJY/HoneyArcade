package honeyarcade.owner.st.ntc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import honeyarcade.ntc.NtcVO;

@Service
public class OwnerStNtcService {

	@Autowired
	private OwnerStNtcMapper ownerStNtcMapper;
	
	/**
	 * 공지사항 : 총 갯수
	 * @param ownerStNtcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount(OwnerStNtcVO ownerStNtcVO) throws Exception{
		return ownerStNtcMapper.getTotalCount(ownerStNtcVO);
	}

	/**
	 * 공지사항 : 목록
	 * @param ownerStNtcVO
	 * @return
	 * @throws Exception
	 */
	public List<OwnerStNtcVO> getNtcList(OwnerStNtcVO ownerStNtcVO) throws Exception{
		return ownerStNtcMapper.getNtcList(ownerStNtcVO);
	}
	
	/**
	 * 공지사항 : 상세보기
	 * @param ownerStNtcVO
	 * @return
	 * @throws Exception
	 */
	public OwnerStNtcVO getNtcView(OwnerStNtcVO ownerStNtcVO) throws Exception {
		return ownerStNtcMapper.getNtcView(ownerStNtcVO);
	}
	
	/**
	 * 공지사항 : 이전글 번호
	 * @param ownerStNtcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getPrevSeq(OwnerStNtcVO ownerStNtcVO) throws Exception{
		return ownerStNtcMapper.getPrevSeq(ownerStNtcVO);
	}

	/**
	 * 공지사항 : 다음글 번호
	 * @param ownerStNtcVO
	 * @return
	 * @throws Exception
	 */
	public Integer getNextSeq(OwnerStNtcVO ownerStNtcVO) throws Exception{
		return ownerStNtcMapper.getNextSeq(ownerStNtcVO);
	}

}
