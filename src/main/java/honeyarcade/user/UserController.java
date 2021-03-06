package honeyarcade.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 사용자 모바일 페이지
 *
 */
@Controller
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/share")
	public String share(@RequestParam(value = "storeId")Integer storeId, Model model) throws Exception{
		Integer build_seq = userService.getBuildSeqOfOwnerId(storeId);
		model.addAttribute("build_seq", build_seq);
		model.addAttribute("owner_id", storeId);
		return "user/userShare";
	}

	/**
	 * App 설치 구분 안내페이지
	 * @param paramVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Location")
	public String qrLocation(@ModelAttribute UserVO paramVO, Model model) throws Exception{
		model.addAttribute("paramVO", paramVO);
		return "user/userIdx";
	}
	
	/**
	 * 건물
	 * @param paramVO
	 * @param type
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/build")
	public String userBuild(@ModelAttribute UserVO paramVO, @RequestParam(defaultValue = "list") String type, Model model) throws Exception{
		paramVO.setBuild_seq(Integer.parseInt(paramVO.getQRLocation()));
		paramVO.setType(type);
		UserVO buildVO = userService.getBuildInfo(paramVO);
		List<UserVO> storeList = userService.getStoreList(paramVO);
		model.addAttribute("type", type);
		model.addAttribute("paramVO", paramVO);
		model.addAttribute("storeList", storeList);
		model.addAttribute("buildVO", buildVO);
		return "user/userBuild";
	}
	
	/**
	 * 층 안내판
	 * @param paramVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/floor")
	public String floor(@ModelAttribute UserVO paramVO, Model model) throws Exception{
		model.addAttribute("paramVO", paramVO);
		UserVO floorVO = userService.getFloorInfo(paramVO);
		model.addAttribute("floorVO", floorVO);    
		model.addAttribute("paramVO", paramVO);
		return "user/userFloor";
	}
	
	/**
	 * 해당 건물 쿠폰 보기
	 * @param paramVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/coupon")
	public String coupon(@ModelAttribute UserVO paramVO, Model model) throws Exception{
		Integer memberCount = userService.getMemberCount(paramVO);
		List<UserVO> couponList = userService.getCouponList(paramVO);
		model.addAttribute("memberCount",memberCount);
		model.addAttribute("couponList",couponList);
		model.addAttribute("paramVO", paramVO);
		return "user/userCoupon";
	}
	
	/**
	 * 해당 건물 홍보 보기
	 * @param paramVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/banner")
	public String banner(@ModelAttribute UserVO paramVO, Model model) throws Exception{
		Integer memberCount = userService.getMemberCount(paramVO);
		List<UserVO> bannerList = userService.getBannerList(paramVO);
		model.addAttribute("bannerList", bannerList);
		model.addAttribute("memberCount",memberCount);
		model.addAttribute("paramVO", paramVO);
		return "user/userBanner";
	}
	
}
