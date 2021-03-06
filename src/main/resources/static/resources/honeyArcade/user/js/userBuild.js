$(function(){
	var _userBuild = function(){
		
		var _init = function(){
			
			var _mobile = koreasoft.modules.mobile.init();
			
			var popup = document.querySelector('.popup');
			var webBtn = document.querySelector('.continue');
			webBtn.addEventListener('click', function() {
				popup.classList.remove('on');
			})	
			
			//	검색 버튼
			$("#searchBtn").on("click", function(){
				$("#searchForm").submit();
			});
			
			$("#search_text").on("keypress", function(e){
				if(e.keyCode == 13){
					$("#searchBtn").click();
				}			
			});
			
			//	뒤로가기 버튼
			$("#backBtn").on("click", function(){
				$("#backForm").submit();
			});
			
			//	앱 설치 버튼
			$(".appSetup").on("click", function(){
				_mobile.appSetup();
			});
			
			//	층 리스트
			$(".store_list").on("click", function(){
				$(this).children().find("form").submit();
			})
			
			//	쿠폰 보기
			$("#couponBtn").on("click", function(){
				$("#showForm").attr("action", "/user/coupon").submit();
			});
			
			//	홍보 보기
			$("#bannerBtn").on("click", function(){
				$("#showForm").attr("action", "/user/banner").submit();
			});
		}
		
		
		return {
			init : _init
		}
	}
	
	new _userBuild().init();
});