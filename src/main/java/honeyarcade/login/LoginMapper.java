package honeyarcade.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	public UserVO findByLoginId(String username);

	public void resetFailureCount(String username);

	public void addFailureCount(String username);

	public int getFailureCount(String username);

	public void disabledUsername(String username);

	/**
	 * 아이디 찾기 : email
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	public UserVO getOwnerId(UserVO userVO) throws Exception;

	/**
	 * 비밀번호 찾기 : id, email
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	public Integer existsOwner(UserVO userVO) throws Exception;

	/**
	 * 비밀번호 찾기 : 비밀번호 업데이트
	 * @param userVO
	 * @throws Exception
	 */
	public void updateOwnerPwd(UserVO userVO) throws Exception;
	
	

	
}
