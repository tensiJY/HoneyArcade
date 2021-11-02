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

koreasoft.namespace('koreasoft.modules.pay');

koreasoft.modules.pay = function() {
	//	_csrfToken 필요
	var _impUid = null;
	
	var _init = function(){
		//	가맹점 식별코드 가져오기
		var _url = "/common/pay/getImpUid";
		var _response = _doAjaxAsync(_url, null);
		
		if(_response.result == 'false' || _response.result == false){
			//alert("결제를 진행할수 없습니다");
		}else{
			_impUid = _response.imp_uid;	
		}
	}
	
	//	객체 생성시 >> 가맹점 식별코드(결제자 고유번호) 가져옮
	_init();
	
	/////
	//	주문번호 및 결제자 정보, 결제 상품 등 정보 가져오기 
	var _getPayInfo = function(data){
		var _obj = {};
		_obj.landom_length = 8;
		_obj.evt_seq_arr = data.evt_seq_arr;
		var _url = "/common/pay/getPayInfo";
		var _result = _doAjaxAsync(_url, _obj);
		return _result;
	}
	
	//	공통 ajax
	function _doAjax(url, data, callBack ){
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				$("#loding-wrapper").addClass("on");
			},
			success: function (res) {
				callBack(res)
				
			},
			error: function (e) {
				//console.log(e)
			},
			complete : function(e){
				$("#loding-wrapper").removeClass("on");
			}
		});
	}
	
	//	공통 ajax async
	function _doAjaxAsync(url, data){
		var _result = null;
						
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			async: false,
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				_result = res
			},
			error: function (e) {
				//console.log(e)
			}
		});
		
		return _result;
	}	
	
	function _cancelCallback(res){
		console.log(res);
	}
	
	function _setData(data, info, rsp){
		var _obj = {};
		_obj.data = data;
		_obj.info = info;
		_obj.rep = rsp;
		var _url = "/common/pay/complete";
		_doAjax(_url, _obj, _sucCallBack);
	}
	
	function _sucCallBack(res){
		if(res.result == false || res.result == 'false'){
			return alert("결제 데이터 처리중 오류가 발생하였습니다 관리자에게 문의 바랍니다.");
		}else{
			alert("결제에 성공하였습니다");
			window.location.reload();
		}
	}
	
	/////////////////
		
	// 결재 요청
    function _requestPay(data, callback) {
	
		var _payInfoRes = _getPayInfo(data);	//	ORD-20211001-81485315
				
		if(_payInfoRes.result == false || _payInfoRes.result == 'false'){
			alert("처리하는데 문제가 생겼습니다. 관리자에게 문의 바랍니다");
			return;
		}
		
        IMP.init(_impUid); //가맹점 식별코드 : 운영에 반영시 고객이 신청해서 발급받으면 그 식별코드로 적용	imp03528463
        IMP.request_pay({ // param
            pg: "html5_inicis",
            // pg: "kakao",
            pay_method: "card",
            merchant_uid: _payInfoRes.uid,		//	createMerchant_Uid(8), // 주문번호 : 서버에서 생성
            name: _payInfoRes.product_title, 	// 	결제 제목
            amount: _payInfoRes.total_price,	//	결제 가격
            buyer_email: _payInfoRes.owner_info.owner_email, // 결제자 Email
            buyer_name: _payInfoRes.owner_info.member_id + "("+_payInfoRes.owner_info.store_name+")", // 결재자(점주) 이름
            buyer_tel: _payInfoRes.owner_info.owner_phone, // 결제자 핸드폰 번호
            buyer_addr: "",
            buyer_postcode: ""
        }, function (rsp) { // callback
			console.log(callback)
			console.log("rsp::::::: ", rsp);
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';    
               	//	data 결제 데이터(품목 데이터)
				//	_payInfoRes 결제 관련 데이터
				//	rsp	결제 후 콜백 데이터
                if(callback == null){
					_setData(data,_payInfoRes, rsp);		
				}else{
					callback(data, _payInfoRes, rsp);
				}
            } else {
                var msg = '결제에 실패하였습니다.';
            	alert(msg);    
            }
          
        });
      
    }

	// 특권 메서드가 들어있는 객체를 반환 
	return { 
		requestPay : _requestPay
		
	}; 
}; 

