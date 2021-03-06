$(function(){
	var _ownerAdArea = function(){
		
		var _init = function(){
			
			$(".areaList").on("click", function() {
				var _s = $(this).hasClass("ad-selected");
				$(".areaList").removeClass("ad-selected");
				if(!_s){	
					$(this).addClass("ad-selected");
				}
			});
			
			//	첨부
			$("#fileImg").on("change", function(){
				var _len = $("#addImgBox").find("li").find("img").length;
				if(_len == 5){
					alert("이미지는 최대 5개만 올릴 수 있습니다");
					$(this).val("");
					return; 
				}	
				_changeEvt(this)
			});
			
			//	프로모션 신청
			$("#adReqBtn").on("click", function(){
				if(confirm("프로모션을 신청 하시겠습니까?")){
					
					var _list = $(".areaList");
					var _check = false;
					for (var i = 0; i < _list.length; i++) {
						if ($(_list[i]).hasClass("ad-selected")) {
							_check = true;
						}
					}
					if (!_check) {
						alert("상품을 선택해주세요");
						return;
					}
					if($("#addImgBox").find(".fileImgArea").length == 0){
						alert("광고 이미지를 한개 이상 등록해주세요");
						return;
					}
					var _adReqText = $("#adReqText").val().trim();
					if(_adReqText == ""){
						alert("배너 광고 의견 제안을 작성해주세요");
						return;
					}
					
					var _obj = {};
					_obj.ad_req_text = _adReqText;
					_obj.file = [];
					_obj.pay_seq	  = $(".ad-selected").find("input[name=pay_seq]").val();
					_obj.pay_dtl_type = $(".ad-selected").find("input[name=pay_dtl_type]").val();
					_obj.pay_dtl_day  = $(".ad-selected").find("input[name=pay_dtl_day]").val();
					
					var _fileList = $("#addImgBox").find(".fileImgArea");
					for(var i=0; i<_fileList.length; i++){
						var _fileObj = _getImgObj($(_fileList[i]));
						_obj.file.push(_fileObj);
					}
					
					var _url = "/owner/ad/area/reqProc"; 
					_doAjax(_url, _obj, _saveCallBack);
				}
			});
		}
		
		function _saveCallBack(res){
			if(res.result == false || res.result == 'false'){
				alert("프로모션 신청에 실패하였습니다");
			}else{
				alert("프로모션 신청에 성공하였습니다");
				window.location.reload();
			}
		}
		
		function _getImgObj(target){
			var _obj = {};
			_obj.file_dtl_desc = $(target).find("input[name=file_dtl_desc]").val();
			_obj.file_dtl_ext = $(target).find("input[name=file_dtl_ext]").val();
			_obj.file_dtl_origin = $(target).find("input[name=file_dtl_origin]").val();
			_obj.file_dtl_path = $(target).find("input[name=file_dtl_path]").val();
			_obj.file_dtl_url_path = $(target).find("input[name=file_dtl_url_path]").val();
			_obj.file_type = $(target).find("input[name=file_type]").val();
			
			return _obj;
		}
		
		var viewer = null;
		var _imageShow = function(image){
			if(viewer != null){
				viewer.destroy();
			}
			 viewer= new Viewer(image,
					{  
						hidden: function () { 
								viewer.destroy(); 
					}
					, toolbar: { 
								zoomIn: 4, 
								zoomOut: 4, 
								oneToOne: 4, 
								reset: 4, 
								prev: 0, 
							    play: { 
							    	show: 4, 
							    	size: 'large' 
							    }, 
							    next: 0, 
							    rotateLeft: 4, 
							    rotateRight: 4, 
							    flipHorizontal: 4, 
							    flipVertical: 4, 
							}, 
							navbar : 0 
					} 
				); 
			viewer.show();
		}

		//	데이터 생성
		function _makeData(res, target) {
			if (res.result == false || res.result == 'false') {
				alert("파일 업로드에 문제가 생겼습니다");
				return;
			}
			var _clone = $("#upFileClone").children().clone();
			$(_clone).find("input[name=file_type]").val(12);
			$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
			$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
			$(_clone).find(".fileImgClass").attr("src", res.files[0].file_dtl_url_path);
			$(_clone).find(".fileImgClass").on("click", function(){
				var _image = new Image();
				_image.src = $(this).attr("src");
				_imageShow(_image);
			});
			$(_clone).find(".fileDelBtn").on("click", function(){
				$(this).parents(".delDiv").remove();
				var _liList = $("#addImgBox").find("li");
				var _cnt = 0;
				for(var i=0; i<_liList.length; i++){
					if($(_liList[i]).find(".fileImgClass").length == 0){
						$(_liList[i]).remove();
						_cnt++;		
					}
				}
				for(var i=0; i<_cnt; i++){
					var _li = document.createElement("li");
					$("#addImgBox").find("ul").append(_li);
				}				
			});
			var _liList = $("#addImgBox").find("li");
			for(var i=0; i<_liList.length; i++){
				if($(_liList[i]).find(".fileImgClass").length == 0){
					$(_liList[i]).append(_clone);
					break;
				}
			}
			$(target).val("");
		}

		//	이미지 업로드시 체인지 이벤트
		function _changeEvt(target) {
			var _isImg = koreasoft.modules.regex.isImgExt($(target).val());
			if (!_isImg) {
				$(target).val("")
				return alert('이미지만 업로드 할수 있습니다');
			}
			_doImageAjax(target, _makeData);
		}	

		//	이미지 업로드
		function _doImageAjax(target, callFunction) {
			var _formData = new FormData();
			var _file = $(target)[0].files[0];
			if (!koreasoft.modules.regex.imgSizeCheck(_file.size)) {
				alert("1메가 이하의 파일을 올려주세요");
				return;
			}
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, callFunction, target);
		}		
						
		//	ajax 공통
		function _doAjax(url, obj, callback){
			$.ajax({
				type: "POST",
				url: url,
				data: JSON.stringify(obj),
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
		
		//	return
		return {
			init : _init
		}
	}
	
	new _ownerAdArea().init();
	
});