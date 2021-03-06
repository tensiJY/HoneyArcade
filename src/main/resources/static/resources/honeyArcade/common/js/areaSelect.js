/**
시도, 군구, 동 지역 관련
*/
 
$(function(){
	var _sidoSelect = document.getElementById("sidoSelect");
	var _sigunguSelect = document.getElementById("sigunguSelect");
	var _dongSelect = document.getElementById("dongSelect");
	var _buildSelect = document.getElementById("buildSelect");
	var _floorSelect = document.getElementById("floorSelect");
	var _hoSelect = document.getElementById("hoSelect");
	var _lcate = document.getElementsByName("lcate");
	var _mcate = document.getElementById("mcateSelect")
	
	if(_lcate != undefined){
		$("input[name=lcate]").on("change", function(){
			var _obj = {};
			_obj.lcate_seq = $(this).val();
			_obj.build_seq = $(this).attr("data-build-seq");
			var _type = 7;
			var _url = "/common/api/getMcateOfBuild"
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	if(_buildSelect != undefined){
		$("#dongSelect").on("change", function(){
			_selectRemove(3);
			var _url = "/common/api/getBuild";
			var _type = 4;
			var _obj = {};
			_obj.dong_seq = $(this).val();
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	if(_floorSelect != undefined){
		$("#buildSelect").on("change", function(){
			_selectRemove(4);
			var _url = "/common/api/getFloor";
			var _type = 5
			var _obj = {};
			_obj.build_seq = $(this).val();
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
		
	if(_hoSelect != undefined){
		$("#floorSelect").on("change", function(){
			_selectRemove(5);
			var _url = "/common/api/getHo";
			var _type = 6;
			var _obj = {};
			_obj.floor_seq = $(this).val();
			_obj.build_seq = $("#floorSelect option:selected").attr("data-build-seq");
			_obj.ho_type = "regForm";
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	function _selectRemove(type){
		if(type == 1){
			$(_sigunguSelect).find(".areaSelect").remove();
			$(_dongSelect).find(".areaSelect").remove();
			if(_buildSelect != undefined){
				$(_buildSelect).find(".areaSelect").remove();
			}
			if(_floorSelect != undefined){
				$(_floorSelect).find(".areaSelect").remove();
			}
			if($("input[name=lcate]").length !=0){
				_cateClear();
			}	
			if(_mcate != undefined){
				$(_mcate).find(".areaSelect").remove();
			}
			if(_hoSelect != undefined){
				$(_hoSelect).find(".areaSelect").remove();
			}
		}else if(type==2){
			$(_dongSelect).find(".areaSelect").remove();
			if(_buildSelect != undefined){
				$(_buildSelect).find(".areaSelect").remove();
			}
			if(_floorSelect != undefined){
				$(_floorSelect).find(".areaSelect").remove();
			}
			if($("input[name=lcate]").length !=0){
				_cateClear();
			}	
			if(_mcate != undefined){
				$(_mcate).find(".areaSelect").remove();
			}
			if(_hoSelect != undefined){
				$(_hoSelect).find(".areaSelect").remove();
			}
		}else if(type==3){
			if(_buildSelect != undefined){
				$(_buildSelect).find(".areaSelect").remove();
			}
			if(_floorSelect != undefined){
				$(_floorSelect).find(".areaSelect").remove();
			}
			if($("input[name=lcate]").length !=0){
				_cateClear();
			}	
			if(_mcate != undefined){
				$(_mcate).find(".areaSelect").remove();
			}
			
			if(_hoSelect != undefined){
				$(_hoSelect).find(".areaSelect").remove();
			}
			
		}else if(type==4){
			if(_floorSelect != undefined){
				$(_floorSelect).find(".areaSelect").remove();
			}
			
			if($("input[name=lcate]").length !=0){
				_cateClear();
			}	
			if(_mcate != undefined){
				$(_mcate).find(".areaSelect").remove();
			}
			
			if(_hoSelect != undefined){
				$(_hoSelect).find(".areaSelect").remove();
			}
			
		}else if(type == 5){
			
			if(_hoSelect != undefined){
				$(_hoSelect).find(".areaSelect").remove();
			}
		}	
	}
	
	function _cateClear(){
		$("input[name=lcate]").prop("disabled", true);
		$("input[name=lcate]").prop("checked", false);
		var _text = "대분류";
		var _lcate = $("input[name=lcate]");
		for(var i=0; i<_lcate.length; i++){
			var _t = _lcate[i];
			$(_t).parent("label").find("span").text(_text+(i+1));
		}
		$("#mcateSelect").prop("disabled", true);
	}
	
	$("#sidoSelect").on('change', function(){
		_selectRemove(1);
		var _url = "/common/api/getSigungu" ;
		var _type = 2;	//	군구 조회
		var _obj = {};
		_obj.sido_seq = $(this).val();
		_doAjax(_url, _obj, _doSelectChange, _type);
	})
	
	$("#sigunguSelect").on('change', function(){
		_selectRemove(2);
		var _url = "/common/api/getDong";
		var _type = 3;
		var _obj = {};
		_obj.sigungu_seq = $(this).val();
		_doAjax(_url, _obj, _doSelectChange, _type);
	});
		
	function _doAjax(url, data, callBack, type){
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
				callBack(res, type)
			},
			error: function (e) {
				//console.log(e)
			},
			complete : function(e){
				$("#loding-wrapper").removeClass("on");
			}
		});
	}
	
	function _doSelectChange(data, type){
		if(data.result == false || data.result == 'false'){
			return alert("데이터를 가져올 수가 없습니다 관리자에게 문의 바랍니다.");
		}
		
		if(type == 1){
			var _target = $("#sidoSelect");
			$(_target).children().remove()
			var _list = data.sidoList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].sido_seq;
				_e.innerText = _list[i].sido_name;
				$(_target).append(_e);
			}
		}else if(type == 2){
			//	시군구 리스트
			var _target = $("#sigunguSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.sigunguList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].sigungu_seq;
				_e.innerText = _list[i].sigungu_name;
				_e.classList.add("areaSelect");
				$(_target).append(_e);
			}
			
		}else if(type == 3){
			//	동리스트
			var _target = $("#dongSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.dongList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].dong_seq;
				_e.innerText = _list[i].dong_name;
				_e.classList.add("areaSelect");
				$(_target).append(_e);
			}
			
		}else if(type == 4){
			//	건물 선택
			var _target = $("#buildSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.buildList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].build_seq;
				_e.innerText = _list[i].build_name;
				_e.classList.add("areaSelect");
				$(_target).append(_e);
			}
						
		}else if(type == 5){
			var _target = $("#floorSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.floorList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].floor_seq;
				_e.innerText = _list[i].floor_type == 1? "지상 "+ _list[i].floor_seq + "층" : '지하 '+(Math.abs(_list[i].floor_seq)) + "층";
				_e.classList.add("areaSelect");
				$(_e).attr("data-build-seq", _list[i].build_seq);
				$(_target).append(_e);
			}
			
			if($("input[name=lcate]").length !=0){	
				var _lcateList = data.lcateList;
				for(var i=0; i<_lcateList.length; i++){
					var _lcate = $("input[name=lcate]")[i];
					$(_lcate).val(_lcateList[i].lcate_seq);
					$(_lcate).attr("data-build-seq", _lcateList[i].build_seq);
					$(_lcate).parent('label').find('span').text(_lcateList[i].lcate_name);
				}
				$("input[name=lcate]").attr("disabled", false);
			}
		}else if(type == 6){
			
			var _target = $("#hoSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.hoList;
			
			for(var i=0; i<_list.length; i++){
				var _ho = _list[i].store_ho;
						
				var _e = document.createElement("option");
				_e.value = _ho;
				_e.innerText = _ho;
				_e.classList.add("areaSelect");
				$(_e).attr("data-store-flag", _list[i].store_flag);
				$(_e).attr("data-pre-owner-id", _list[i].owner_id);
			
				$(_target).append(_e);
			}
		
		}else if (type == 7){
			$("#mcateSelect").attr("disabled", false);
			var _target = $("#mcateSelect");
			$(_target).find(".areaSelect").remove();
			var _list = data.mcateList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].mcate_seq
				_e.innerText = _list[i].mcate_name;
				_e.classList.add("areaSelect");
				$(_e).attr("data-build-seq", _list[i].build_seq);
				$(_e).attr("data-lcate-seq", _list[i].lcate_seq);
				$(_target).append(_e);
			}
		}
	};//
	
	
});