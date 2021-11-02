package honeyarcade.owner.ad.pro;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerAdProMapper {

	/**
	 * 프로모션 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	public List<OwnerAdProVO> getEventList() throws Exception;

}
