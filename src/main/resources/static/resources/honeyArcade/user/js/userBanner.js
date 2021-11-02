$(function(){
	var _userBanner = function(){
		
		var _init = function(){
			
			var _mobile = koreasoft.modules.mobile.init();
			
			var popup = document.querySelector('.popup');
			var webBtn = document.querySelector('.continue');
			webBtn.addEventListener('click', function () {
				popup.classList.remove('on');
			});
			
			//	뒤로 가기 버튼
			$("#backBtn").on("click", function(){
				var _position = $("input[name=position]").val();
				if(_position == "floor"){
					//	층
					$("#backForm").attr("action", "/user/floor").submit();
				}else{
					//	건물
					$("#backForm").attr("action", "/user/build").submit();
				}
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
	
	new _userBanner().init();
})