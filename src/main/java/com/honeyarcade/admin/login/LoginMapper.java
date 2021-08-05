package com.honeyarcade.admin.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	public AdminVO findByLoginId(@Param("login_id") String id);

	
}
