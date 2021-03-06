package honeyarcade.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 건물 정보 가져오기
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public UserVO getBuildInfo(UserVO paramVO) throws Exception {
		return userMapper.getBuildInfo(paramVO);
	}

	/**
	 * 상점정보 가져오기
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<UserVO> getStoreList(UserVO paramVO) throws Exception {
		return userMapper.getStoreList(paramVO);
	}

	/**
	 * 층 정보 가져오기
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public UserVO getFloorInfo(UserVO paramVO) throws Exception {
		return userMapper.getFloorInfo(paramVO);
	}

	/**
	 * 건물의 가맹점 수 조회
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberCount(UserVO paramVO) throws Exception {
		return userMapper.getMemberCount(paramVO);
	}

	/**
	 * 건물의 쿠폰 목록 조회 
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<UserVO> getCouponList(UserVO paramVO) throws Exception {
		return userMapper.getCouponList(paramVO);
	}

	/**
	 * 건물광고 : 건물에 설정되어 있는 배너 광고 목록
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<UserVO> getBannerList(UserVO paramVO) throws Exception {
		return userMapper.getBannerList(paramVO);
	}

	/**
	 * 
	 * @param storeId
	 * @return
	 */
	public Integer getBuildSeqOfOwnerId(Integer storeId) throws Exception{
		return userMapper.getBuildSeqOfOwnerId(storeId);
	}
	
}
