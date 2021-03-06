package honeyarcade.common.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonApiMapper {
	public List<CommonApiVO> getSido() throws Exception;
	public List<CommonApiVO> getSigungu(CommonApiVO commonApiVO) throws Exception;
	public List<CommonApiVO> getDong(CommonApiVO commonApiVO) throws Exception;
	public List<CommonApiVO> getBuild(CommonApiVO commonApiVO) throws Exception;
	public List<CommonApiVO> getFloor(CommonApiVO commonApiVO) throws Exception;
	public List<CommonApiVO> getOwnerOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 대 카테고리 가져오기(build_seq) 
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getLcateOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 소 카테고리 가져오기(build_seq, lcate_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getMcateOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 점포 목록 가져오기(build_seq, floor_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getStore(CommonApiVO commonApiVO) throws Exception;
	
	 /**
	  * 점주가 속한 지역정보 가져오기
	  * @param owner_id
	  * @return
	  * @throws Exception
	  */
	public CommonApiVO getAreaInfoOfOwnerId(String owner_id) throws Exception;

	/**
	 * 호 목록 가져오기(build_seq, floor_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getHo(CommonApiVO commonApiVO) throws Exception;

}
