package honeyarcade.common.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonApiService {
	
	@Autowired
	private CommonApiMapper commonApiMapper;
	
	public List<CommonApiVO> getSido() throws Exception{
		return commonApiMapper.getSido();
	}

	public List<CommonApiVO> getSigungu(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getSigungu(commonApiVO);
	}

	public List<CommonApiVO> getDong(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getDong(commonApiVO);
	}

	public List<CommonApiVO> getBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getBuild(commonApiVO);
	}

	public List<CommonApiVO> getFloor(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getFloor(commonApiVO);
	}

	public List<CommonApiVO> getOwnerOfBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getOwnerOfBuild(commonApiVO);
	}
	
	/**
	 * 대 카테고리 가져오기(build_seq) 
	 * @param commonApiVO 
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getLcateOfBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getLcateOfBuild(commonApiVO);
	}

	/**
	 * 소 카테고리 가져오기(build_seq, lcate_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getMcateOfBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getMcateOfBuild(commonApiVO);
	}

	/**
	 * 점포 목록 가져오기(build_seq, floor_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getStore(CommonApiVO commonApiVO) throws Exception {
		return commonApiMapper.getStore(commonApiVO);
	}
	
	 /**
	  * 점주가 속한 지역정보 가져오기
	  * @param owner_id
	  * @return
	  * @throws Exception
	  */
	public CommonApiVO getAreaInfoOfOwnerId(String owner_id) throws Exception{
		return commonApiMapper.getAreaInfoOfOwnerId(owner_id);
	}

	/**
	 * 호 목록 가져오기(build_seq, floor_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getHo(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getHo(commonApiVO);
	}	

}
