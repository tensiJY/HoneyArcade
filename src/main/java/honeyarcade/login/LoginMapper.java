package honeyarcade.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	public UserVO findByLoginId(@Param("login_id") String username);

	public void resetFailureCount(@Param("login_id") String username);

	public void addFailureCount(@Param("login_id") String username);

	public int getFailureCount(@Param("login_id") String username);
	
	

	
}
