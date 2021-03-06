$(function(){
	
	var _ownerAdPro = function(){
		var _init = function(){
			
			var _pay = koreasoft.modules.pay();
			
			var arcodianBox = document.querySelector('#arcodian-menu')
			arcodianBox.addEventListener('click', function (e) {
				e.target.classList.toggle('on')
			})
			
			$("input[name=event_check]").on("click", function(e){
				//e.preventDefault();
				var _list = $("input[name=event_check]");
				var _totalPrice = 0;
				for(var i=0; i<_list.length; i++){
					var _target = $(_list[i]);
					var _isChecked = $(_target).prop("checked")
					if(_isChecked){
						$(_target).parents(".arcodian").addClass("selected");
						var _price = Number($(_target).parents("li").find("input[name=event_price]").val());
						_totalPrice += _price;
					}else{
						$(_target).parents(".arcodian").removeClass("selected");
					}
				}
				$("#sumPrice").text(koreasoft.modules.stringUtil.numberWithCommas(_totalPrice));
			});
			
			//	결제 버튼
			$("#buyBtn").on("click", function(){
				if(confirm("선택한 프로모션을 결제하시겠습니까?")){
					//	동의 체크
					if(!$("#agree").prop("checked")){
						alert("결제 내용에 대한 동의를 선택해주시기 바랍니다");
						return;
					}
				
					var _list = $("input[name=event_check]");
					var _evtSeqArr = [];
						
					for(var i=0; i<_list.length; i++){
						var _target = $(_list[i]);
						if($(_target).prop("checked")){
							var _evtSeq = $(_target).parents("li").find("input[name=event_seq]").val();
							var _obj = {};
							_obj.event_seq = _evtSeq;
							_evtSeqArr.push(_obj);
						}
					}//
					
					if(_evtSeqArr.length == 0){
						alert("선택한 프로모션이 없습니다");
						return;
					}
					
					var _obj = {}
					_obj.evt_seq_arr = _evtSeqArr;
					
					_pay.requestPay(_obj, null);
				}
			})
			
		}//
		
		return {
			init : _init
		}
	}
	
	new _ownerAdPro().init();
		
})