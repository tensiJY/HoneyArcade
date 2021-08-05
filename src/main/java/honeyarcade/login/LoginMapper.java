package honeyarcade.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	public UserVO findByLoginId(@Param("login_id") String id);

	
}
