/**
시도, 군구, 동 지역 관련
*/
 
$(function(){
	//	bootstrap-select.min.js	이후
	$.fn.selectpicker.Constructor.DEFAULTS.noneResultsText = "검색결과가 없습니다... {0}";	
	
	var _sidoSelect = document.getElementsByName("sido_seq");
	var _sigunguSelect = document.getElementsByName("sigungu_seq");
	var _dongSelect = document.getElementsByName("dong_seq");
	var _buildSelect = document.getElementsByName("build_seq");
	var _storeSelect = document.getElementsByName("owner_id");
	
	$(_sidoSelect).selectpicker();
	$(_sigunguSelect).selectpicker();
	$(_dongSelect).selectpicker();
	$(_buildSelect).selectpicker();
	$(_storeSelect).selectpicker();
	
	for(var i=0; i<_sidoSelect.length; i++){
		var _sidoTarget = $(_sidoSelect[i]);
		var _sigunguTarget = $(_sigunguSelect[i]);
		var _dongTarget = $(_dongSelect[i]);
		_makeChangeEvt(_sidoTarget, 1);
		_makeChangeEvt(_sigunguTarget, 2);
		_makeChangeEvt(_dongTarget, 3);
	}
	
	for(var i=0; i<_buildSelect.length; i++){
		var _buildTarget = $(_buildSelect[i]);
		if($(_buildTarget).parents("form").children().find("select[name=owner_id]").length > 0){
			_makeChangeEvt(_buildTarget, 4);
		}
	}	
	
	function _makeChangeEvt(target, type){
		if(type==1){
			$(target).on("change", function(){
				var _url = "/common/api/getSigungu";
				var _obj = {};
				_obj.sido_seq = $(this).val();
				_doAjax(_url, _obj, _doSelectChange, target,  2);
			});
			
		}else if(type == 2){
			$(target).on("change", function(){
				var _url = "/common/api/getDong";
				var _type = 3;
				var _obj = {};
				_obj.sigungu_seq = $(this).val();
				_doAjax(_url, _obj, _doSelectChange, target, _type);
			})
			
		}else if(type == 3){
			$(target).on("change", function(){
				var _form = $(target).parents('form');
				var _target = $(_form).children().find("select[name=build_seq]");
				if($(_target).length == 0){
					return;
				}
				var _url = "/common/api/getBuild";
				var _type = 4;
				var _obj = {};
				_obj.dong_seq = $(this).val();
				_doAjax(_url, _obj, _doSelectChange, target, _type);
			});
		}else if(type == 4){
			$(target).on("change", function(){
				var _form = $(target).parents('form');
				var _target = $(_form).children().find("select[name=owner_id]");
				if($(_target).length == 0){
					return;
				}
				var _url = "/common/api/getOwnerOfBuild";
				var _type = 5;
				var _obj = {};
				_obj.build_seq = $(this).val();
				_doAjax(_url, _obj, _doSelectChange, target, _type);
			})
		}
	}
	
	function _doAjax(url, data, callBack, target, type){
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				callBack(res, target, type)
				
			},
			error: function (e) {
				//console.log(e)
			}
		});
	}
	
	function _doSelectChange(res, that, type){
		if(res.result == false || res.result == 'false'){
			alert("처리하는데 문제가 생겼습니다. 관리자에게 문의바랍니다");
			return;
		}
		
		
		var _form = $(that).parents('form');
		if(type == 2){
			//	시도 -> 시군구
			var _target = $(_form).children().find("select[name=sigungu_seq]");
			_selectRemove(that, 2);
			var _list = res.sigunguList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].sigungu_seq;
				_e.innerText = _list[i].sigungu_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
		}else if(type == 3){
			//	시군구 -> 동
			var _target = $(_form).children().find("select[name=dong_seq]");
			_selectRemove(that, 3);
			var _list = res.dongList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].dong_seq;
				_e.innerText = _list[i].dong_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
			
		}else if(type == 4){
			//	동 -> 건물
			var _target = $(_form).children().find("select[name=build_seq]");
			_selectRemove(that, 4);
			var _list = res.buildList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].build_seq;
				_e.innerText = _list[i].build_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
		}else if(type == 5){
			var _target = $(_form).children().find("select[name=owner_id]");
			_selectRemove(that, 5);
			var _list = res.ownerList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].owner_id;
				_e.innerText = _list[i].store_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
		}
	}
	
	function _selectRemove(target, type){
		var _form = $(target).parents('form');
		var _sigungu	= $(_form).children().find("select[name=sigungu_seq]");
		var _dong		= $(_form).children().find("select[name=dong_seq]");
		var _build		= $(_form).children().find("select[name=build_seq]");
		var _owner		= $(_form).children().find("select[name=owner_id]");
		
		if(type == 2){
			$(_sigungu).children().remove();
			$(_dong).children().remove();
			$(_sigungu).selectpicker("refresh");
			$(_dong).selectpicker("refresh");
			if($(_build).length != 0){
				$(_build).children().remove();
				$(_build).selectpicker("refresh");						
			}
			if($(_owner).length != 0){
				$(_owner).children().remove();
				$(_owner).selectpicker("refresh");						
			}
		}else if(type == 3){
			$(_dong).children().remove();
			$(_dong).selectpicker("refresh");
			
			if($(_build).length != 0){
				$(_build).children().remove();
				$(_build).selectpicker("refresh");						
			}
			if($(_owner).length != 0){
				$(_owner).children().remove();
				$(_owner).selectpicker("refresh");						
			}
			if($(_owner).length != 0){
				$(_owner).children().remove();
				$(_owner).selectpicker("refresh");						
			}
		}else if(type == 4){
			if($(_build).length != 0){
				$(_build).children().remove();
				$(_build).selectpicker("refresh");						
			}
			if($(_owner).length != 0){
				$(_owner).children().remove();
				$(_owner).selectpicker("refresh");						
			}
		}else if(type == 5){
			if($(_owner).length != 0){
				$(_owner).children().remove();
				$(_owner).selectpicker("refresh");						
			}
		}
	}
	
});