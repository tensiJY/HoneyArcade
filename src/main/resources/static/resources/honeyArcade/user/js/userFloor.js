$(function(){
	var _userFloor = function(){
		var _init = function(){
			var _mobile = koreasoft.modules.mobile.init();
			
			var popup = document.querySelector('.popup');
			var webBtn = document.querySelector('.continue');
			webBtn.addEventListener('click', function () {
				popup.classList.remove('on');
			});
			
			//	뒤로가기 버튼
			$("#backBtn").on("click", function(){
				$("#backForm").submit();
			});
			
			//	쿠폰 보기
			$("#couponBtn").on("click", function(){
				$("#showForm").attr("action", "/user/coupon").submit();
			});
			
			//	홍보 보기
			$("#bannerBtn").on("click", function(){
				$("#showForm").attr("action", "/user/banner").submit();
			});			

			//	앱 설치 버튼
			$(".appSetup").on("click", function(){
				_mobile.appSetup();
			});			
		}
		
		return {
			init : _init
		}
	}
	
	new _userFloor().init();
	
})