
var _page = function(){
	var _totalPage	= $("#totalPage").val();
	var _nowPage 	= $("#nowPage").val();
	var _listCount	= $("#listCount").val();
	var _type		= $("#type").val();
	
	//var _isLoading	= false;
	var _flag 		= true;
	
	var _doAjax 	= function(callback){
		_flag = false;

		var _obj = {};
		_obj.nowPage	= _nowPage;
		_obj.listCount 	= _listCount;
		_obj.type		= _type;
		
		var _url = "/ntc/getList";
		
		$.ajax({
			type: "POST",
			url: _url,
			data: JSON.stringify(_obj),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				$("#loding-wrapper").addClass("on");
			},
			success: function (res) {
				callback(res);
				
			},
			error: function (e) {
				alert("서버 처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.");
				console.log("error : " + e.toString())
			},
			complete : function(e){
				$("#loding-wrapper").removeClass("on");
			}
		});	
	}
	
	var _addTag		= function(res){
		
		if(res.result == false || res.result == 'false'){
			alert(res.msg);
		}else{
			var _ntc = res.ntcList;
			for(var i=0; i<_ntc.length; i++){
				var _clone = $("#cloneTag").find(".evtClass").clone();
				var _input = document.createElement("input");
				$(_input).attr("name", "ntc_seq");
				$(_input).attr("type", "hidden")
				$(_input).attr("value", _ntc[i].ntc_seq);
				
				var _td = $(_clone).find("td");
				$(_td[0]).text(_ntc[i].row_num)
				var _p = $(_clone).find("p");
				$(_p[0]).text(_ntc[i].ntc_title);
				$(_p[1]).text(_ntc[i].add_dt);
				
				$(_td[0]).append(_input);
				
				if(_ntc[i].diff == 1){
					$(_clone).addClass("new");
				}
				
				$(_clone).on("click", function(){
					_addEvtClass(this);
				})
				
				$(".notice-table").append(_clone);
			}					
		}
		
		_flag = true;
	}
	
	//	form submit event
	var _addEvtClass = function (target){
		var _v = $(target).find("input[name=ntc_seq]").val();
		$("#hiddenForm").find("input[name=ntc_seq]").val(_v)
		$("#hiddenForm").submit();
	}
	
	var _init		= function(){
		var _evtClass = $(".evtClass");
		for(var i=0; i<_evtClass.length; i++){
			$(_evtClass[i]).on("click", function(e){
				_addEvtClass(this);
			})
		}
		
		$("div.table-wrap").scroll(function(){
			//console.log(_nowPage)
			var _a = $("div.table-wrap").scrollTop();
			var _b = $("table.notice-table").height();
			var _c = $("div.table-wrap").height();
			//console.log("a , b, c : "  + _a  + " " + _b + " " + _c);
			//console.log(_a >= _b - _c)
					
			if(_a >= _b - _c){
    			//console.log(_flag)	
    			if(_flag){
					_nowPage = Number(_nowPage) + 1;
					if(_nowPage > _totalPage){
						_flag = false;
						return;
					}
					_doAjax(_addTag);
				}					
     		} 
		})
	}	 
	
	return  {
		init : _init
	}
		
}

$(function(){
	new _page().init();
	
})