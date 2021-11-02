var koreasoft = koreasoft || {}; 
koreasoft.namespace = function(ns_string) { 
	var parts = ns_string.split('.'), parent = koreasoft, i; 
	if (parts[0] === "koreasoft") { 
		parts = parts.slice(1); 
	} 
	for (i = 0; i < parts.length; i += 1) { 
		if (typeof parent[parts[i]] === "undefined") { 
			parent[parts[i]] = {}; 
		} 
		parent = parent[parts[i]]; 
	} 
	return parent; 
} 

koreasoft.namespace('koreasoft.modules.mobile');

koreasoft.modules.mobile = function() { 
	//	private 
	var _mobileCheck	= null;
	var _os				= null;
	
	var _androidPackage = "com.adobe.reader&hl=ko&gl=US";	//	카카오
	var _appleAppId = ""; // id1401934913 형식으로 itunes url에 나오는 앱아이디.
	var _androidMarketUrl = "https://play.google.com/store/apps/details?id=com.adobe.reader&hl=ko&gl=US";//
	var _iosMarketUrl = "http://itunes.apple.com/kr/app/" + _appleAppId;

	//	intent://instagram.com/#Intent;package=com.instagram.android;scheme=https;end
	//	https://itunes.apple.com/kr/app/instagram/id389801252?mt=8	
	 
	//	init 
	var _init = function(){
		var _is =(/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase()));
 		_mobileCheck = _is;
 		if(_is){
			//	모바일인경우
			var _varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
			if ( _varUA.indexOf('android') > -1) {
	        	//안드로이드
	        	_os = "android";
	    	} else if ( _varUA.indexOf("iphone") > -1||_varUA.indexOf("ipad") > -1||_varUA.indexOf("ipod") > -1 ) {
	        	//IOS
	  	      	_os = "ios";
	    	} else {
				_os	= "other";
			}
		}else{
			//	모바일이 아닌경우
			_os = "pc";
		}
		
		//	실행가능 함수만 반환
		return{
			isMobile :_isMobile
			, appSetup :_appSetup
			, getOS : _getOS
		}
		
	}
	

		
	///////////////////////////////////////////
	/**
	모바일 여부
	 */
	function _isMobile(){
		return _mobileCheck;
	}
	
	/**
	모바일 유형 : ios, android, win, other
	 */
	function _getOS(){
		return _os;
	}
	
	//	스토어로 이동
	function _appSetup(){
		var _check = _isMobile();
		var _os = _getOS();
		var _startTime = +new Date();
		if(_check){
			setTimeout( function(){
			var _now = +new Date();
				if( _now - _startTime < 1000){
					var _marketUrl = "";
					if(_os == "ios"){
						_marketUrl = _iosMarketUrl;
						
					}else{
						_marketUrl = _androidMarketUrl
											
					}
				}
				
				location.href = _marketUrl;
			},500)
			/*			
			if(_os == "ios"){
				_openiOS();
			}else{
				_openAndroid();
			}
			*/
		}
	}
	
	//	location.href = "Intent://호스트#Intent;scheme=스키마;package=패키지명;end";	
	function _openAndroid() {
		var userAgent = navigator.userAgent.toLowerCase();
		if (userAgent.match(/chrome/)) {
			location.href = "intent://hostName?param1=someValue1&m2=someValue2/"
				+ "#Intent;scheme=schemeName;action=android.intent.action.VIEW;"
				+ "category=android.Intent.category.BROWSABLE;package=" + androidPackage;
		} else {
			var iframe = document.createElement('iframe');
			iframe.style.visivility = 'hidden';
			iframe.src = 'schemeName://hostName?param1=someValue&m2=someValue2';
			document.body.appendChild(iframe);
			document.body.removeChild(iframe);
		}
	}

	function _openiOS() {
		location.href = "schemeName://hostName?param1=someValue&m2=someValue2";
	}
	
	// 생성자 반환
	return { 
		init : _init
	}; 
}();