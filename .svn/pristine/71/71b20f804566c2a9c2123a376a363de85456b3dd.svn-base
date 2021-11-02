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

koreasoft.namespace('koreasoft.modules.stringUtil');

koreasoft.modules.stringUtil = function() { 
	///////////	private 

	
	///////////	public 
	function _getUUID() { // UUID v4 generator in JavaScript (RFC4122 compliant)
  		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    	var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 3 | 8);
    	return v.toString(16);
  		});
	}

	///	3자리수 컴마
	function _numberWithCommas(x) {
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	/**
	 * 날짜 간격 일수, 차이 구하기
	 * param YYYY-MM-DD 
	 * ex) 2021-10-01, 2021-10-10
	 */
	function _dateDiff(sdt, edt){
		var _sdt = new Date(sdt);
		var _edt = new Date(edt);
		var _dateDiff = Math.ceil((_edt.getTime()-_sdt.getTime())/(1000*3600*24));
		return _dateDiff;
	}

	// 특권 메서드가 들어있는 객체를 반환 
	return {		
		getUUID : _getUUID
		, numberWithCommas : _numberWithCommas
		, dateDiff : _dateDiff
	}; 
}(); 


 