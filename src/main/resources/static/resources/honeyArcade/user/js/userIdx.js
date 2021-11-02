$(function(){
	var _userIdx = function(){
		
		var _init = function(){
			var _mobile = koreasoft.modules.mobile.init();
			$("#findBuild").on("click", function(){
				$("#userForm").submit();
			});
			$("#appSetup").on("click", function(){
				_mobile.appSetup();
			});
		}
		
		return{
			init : _init
		}
	}
	
	
	new _userIdx().init()
})