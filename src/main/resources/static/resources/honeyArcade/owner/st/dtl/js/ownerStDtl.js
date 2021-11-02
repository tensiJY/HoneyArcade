$(function(){
	
	var _ownerStDtl = function(){
		
		var _init = function(){
			var _dateOption = {
				showOtherMonths: true,
      			selectOtherMonths: true,
      			changeMonth: true,
      			changeYear: true,
      			dateFormat: 'yy-mm-dd', 
      			autoSize: true
			}
			$.datepicker.setDefaults( $.datepicker.regional[ "ko" ] );
			var _time = flatpickr('.datepick', {
				enableTime: true,
				noCalendar: true,
				dateFormat: "H:i",
				time_24hr: true,
			});
			$("#holDayStart").datepicker(_dateOption);
			$("#holDayEnd").datepicker(_dateOption);
			$("#phDate").datepicker(_dateOption);			
						
			//	매장 대표 사진 업로드
			$("#main_img").on("change", function(){
				_changeEvt(this, 1)
			});
			
			//	사업자 등록증 업로드
			$("#busi_img").on("change", function(){
				_changeEvt(this, 2)
			});
			
			//	상품 이미지 첨부 업로드
			$("#product_file").on("change", function(){
				_changeEvt(this, 3);
			});
			
			//	메인 이미지 이벤트
			$("#mainImgTarget .mainImg").find(".mainImgViewBtn").on("click", function(){
				_addPrevEvt(this, 1);
			});
			$("#mainImgTarget .mainImg").find(".mainImgDelBtn").on("click", function(){
				_delImgBtn(this, 1);
			})		
			
			//	사업자 등록증 이벤트
			$(".busi_img").on("click", function(){
				_addPrevEvt(this, 2);
			});
			
			//	상품 추가 
			$("#productAddBtn").on("click", function(){
				var _productPrice = $("#productPrice").val();
				var _productName = $("#productName").val().trim();
				if(_productPrice == ""){
					alert("상품 금액을 입력해주세요");
					return;
				}
				if(_productPrice<0){
					alert("상품 금액은 0보다 큰 숫자이어야 합니다");
					return;
				}
				if(isNaN(Number(_productPrice))){
					alert("상품의 금액은 숫자만 입력해주세요");
					return;
				}
				if(_productName  == ""){
					alert("상품명을 입력해주세요");
					return;
				}
				if($("#productImsiImg").children().length == 0){
					alert("상품 이미지를 등록해주세요");
					return;
				}
				$("#target-product").find(".no-product-text").remove();
				var _clone = $("#productClone").find("li").clone();
				$(_clone).find(".productName").text(_productName);
				$(_clone).find(".priductPrice").text(koreasoft.modules.stringUtil.numberWithCommas(_productPrice));
				$(_clone).find("input[name=file_dtl_desc]").val($("#productImsiImg").children().find("input[name=file_dtl_desc]").val());
				$(_clone).find("input[name=file_dtl_ext]").val($("#productImsiImg").children().find("input[name=file_dtl_ext]").val());
				$(_clone).find("input[name=file_dtl_origin]").val($("#productImsiImg").children().find("input[name=file_dtl_origin]").val());
				$(_clone).find("input[name=file_dtl_path]").val($("#productImsiImg").children().find("input[name=file_dtl_path]").val());
				$(_clone).find("input[name=file_dtl_url_path]").val($("#productImsiImg").children().find("input[name=file_dtl_url_path]").val());
				$(_clone).find("input[name=file_type]").val($("#productImsiImg").children().find("input[name=file_type]").val());
				$(_clone).find("input[name=file_seq]").val($("#productImsiImg").children().find("input[name=file_seq]").val());
				$(_clone).find("img").attr("src", $("#productImsiImg").children().find("img").attr("src"));
				$(_clone).find(".imgPrevBtn").on("click", function(){
					_addPrevEvt(this, 3);
				});
				$(_clone).find(".imgDelBtn").on("click", function(){
					_delImgBtn(this, 3);
				})
				$("#target-product").append(_clone);
				$("#productImsiImg").children().remove();
				$("#productName").val("");
				$("#productPrice").val("");
				$(".product-file").val("");
				$("#product_file").val("");
			});

			//	영업시간 등록
			$("#openAddBtn").on("click", function(){
				var _value = $("#opendaySelect").val();
				var _text = $("#opendaySelect option:selected").text();
				var _openDayStart = $("#openDayStart").val();
				var _openDayEnd = $("#openDayEnd").val();
				if(_text == ""){
					alert("영업 요일을 선택해주세요");
					return;
				}
				if(_value != 11){
					if(_openDayStart == ""){
						alert("영업 오픈 시간을 입력해주세요");
						return;
					}
					if(_openDayEnd == ""){
						alert("영업 마감시간을 입력해주세요")
						return;
					}
				}else{
					_openDayStart = " ";
					_openDayEnd = " ";
				}
				var _check = true;
				var _len = $("#openDayList").find(".openDayClass");
				for(var i=0; i<_len.length; i++){
					var _v = $(_len[i]).find("input[name=open_day]").val();
					if(_v == _value){
						_check = false;	
						break;
					}
				}
				if(!_check){
					alert("영업의 요일이 중복 됩니다");
					return;
				}
				$("#openDayList").find(".noData").remove();
				var _clone = $("#openDayClone").find("li").clone();
				$(_clone).find(".openDaySpan").text(_text);
				var _timeSpanText = null;
				if(_value != 11){
					_timeSpanText = " " + _openDayStart + " ~ " + _openDayEnd;
				}else{
					_timeSpanText = " ";
				}
				$(_clone).find(".openTimeSpan").text(_timeSpanText);
				$(_clone).find("input[name=open_day]").val(_value);
				$(_clone).find("input[name=open_time]").val(_openDayStart);
				$(_clone).find("input[name=close_time]").val(_openDayEnd);		
				$(_clone).find(".openDayDelBtn").on("click", function(){
					$(this).parents(".openDayClass").remove();
					var _l = $("#openDayList").find(".openDayClass");
					if(_l.length == 0){
						var _c = $("#noDataClone").find(".noData").clone();
						$("#openDayList").append(_c);
					}
				});
				$("#openDayList").append(_clone);				
				$("#opendaySelect").val("");
				$("#openDayStart").val("");
				$("#openDayEnd").val("");
			});
			
			//	휴무일 등록
			$("#dayOffAddBnt").on("click", function(){
				var _offWeek = $("#dayOffWeekSelect").val();
				var _offWeekText = $("#dayOffWeekSelect option:selected").text();
				var _offDay = $("#dayOffDaySelect").val();
				var _offDayText = $("#dayOffDaySelect option:selected").text();
				if(_offWeekText == ""){
					alert("휴무일의 주차를 선택 해주세요")
					return;
				}
				if(_offDayText == ""){
					alert("휴무일의 요일을 선택해주세요")
					return;
				}
				var _check = true;
				var _len = $("#dayOffList").find(".dayOffClass");
				for(var i=0; i<_len.length; i++){
					var _v = $(_len[i]).find("input[name=day_off_week]").val();
					if(_v == _offWeek){
						_check = false;	
						break;
					}
				}
				if(!_check){
					alert("휴무일의 주차가 중복 됩니다");
					return;
				}
				$("#dayOffList").find(".noData").remove();
				var _clone = $("#dayOffClone").find("li").clone();
				$(_clone).find(".offWeekSpan").text(_offWeekText);
				$(_clone).find(".offDaySpan").text(" " + _offDayText);
				$(_clone).find("input[name=day_off_week]").val(_offWeek);
				$(_clone).find("input[name=day_off_day]").val(_offDay);
				$(_clone).find(".dayOffDelBtn").on("click", function(){
					$(this).parents(".dayOffClass").remove();
					var _l = $("#dayOffList").find(".dayOffClass");
					if(_l.length == 0){
						var _c = $("#noDataClone").find(".noData").clone();
						$("#dayOffList").append(_c);
					}
				});
				$("#dayOffList").append(_clone);				
				$("#dayOffWeekSelect").val("");
				$("#dayOffDaySelect").val("");
			});
			//	휴게 시간 등록
			$("#breakDayAddBtn").on("click", function(){
				var _breakDaySelect = $("#breakDaySelect").val();
				var _breakDayText = $("#breakDaySelect option:selected").text();
				var _breakDayStart = $("#breakDayStart").val();
				var _breakDayEnd = $("#breakDayEnd").val();
				if(_breakDayText == ""){
					alert("휴게시간의 요일을 선택해주세요");
					return;
				}
				if(_breakDaySelect != 11){
					if(_breakDayStart == ""){
						alert("휴게시간의 시작 시간을 입력해주세요");
						return;
					}
					if(_breakDayEnd == ""){
						alert("휴게시간의 종료 시간을 입력해주세요")
						return;
					}
				}else{
					_breakDayStart = " ";
					_breakDayEnd = " ";
				}
				var _check = true;
				var _len = $("#breakDayList").find(".breakDayClass");
				for(var i=0; i<_len.length; i++){
					var _v = $(_len[i]).find("input[name=break_day]").val();
					if(_v == _breakDaySelect){
						_check = false;	
						break;
					}
				}
				if(!_check){
					alert("휴게시간의 요일이 중복 됩니다");
					return;
				}
				$("#breakDayList").find(".noData").remove();
				var _clone = $("#breakClone").find("li").clone();
				$(_clone).find(".breakDaySpan").text(_breakDayText);
				var _breakText = "";
				if(_breakDaySelect != 11){
					_breakText = " " + _breakDayStart + " ~ " + _breakDayEnd;
				}else{
					_breakText = " ";
				}
				$(_clone).find(".breakTimeSpan").text(_breakText);
				$(_clone).find("input[name=break_day]").val(_breakDaySelect);
				$(_clone).find("input[name=open_time]").val(_breakDayStart);
				$(_clone).find("input[name=close_time]").val(_breakDayEnd);		
				$(_clone).find(".breakDelBtn").on("click", function(){
					$(this).parents(".breakDayClass").remove();
					var _l = $("#breakDayList").find(".breakDayClass");
					
					if(_l.length == 0){
						var _c = $("#noDataClone").find(".noData").clone();
						$("#breakDayList").append(_c);
					}
				});
				$("#breakDayList").append(_clone);				
				$("#breakDaySelect").val("");
				$("#breakDayStart").val("");
				$("#breakDayEnd").val("");
			});
			
			//	휴가 설정(긴급 휴무)
			$("#holDayAddBtn").on("click", function(){
				var _holDayStart = $("#holDayStart").val();
				var _holDayEnd = $("#holDayEnd").val();
				if(_holDayStart == "" ){
					alert("휴무 시작일을 입력해주세요");
					return;
				}
				if(_holDayEnd == ""){
					alert("휴무 종료일을 입력해주세요");
					return;
				}
				if(_holDayStart > _holDayEnd){
					alert("휴무 시작일은 종료일보다 작아야 합니다");
					return;
				}
				var _check = true;
				var _len = $("#holDayList").find(".holDayClass");
				for(var i=0; i<_len.length; i++){
					var _hs = $(_len[i]).find("input[name=holiday_start_day]").val();
					var _he = $(_len[i]).find("input[name=holiday_end_day]").val();
					if(_hs == _holDayStart && _he== _holDayEnd){
						_check = false;	
						break;
					}
				}
				if(!_check){
					alert("중복된 휴무일 일정을 등록 할 수 없습니다");
					return;
				}
				$("#holDayList").find(".noData").remove();
				var _clone = $("#holDayClone").find("li").clone();
				$(_clone).find(".holDayStartSpan").text(_holDayStart);
				$(_clone).find(".holDayEndSpan").text(_holDayEnd);
				$(_clone).find("input[name=holiday_type]").val(1);
				$(_clone).find("input[name=holiday_start_day]").val(_holDayStart);
				$(_clone).find("input[name=holiday_end_day]").val(_holDayEnd);		
				$(_clone).find(".holdayDelBtn").on("click", function(){
					$(this).parents(".holDayClass").remove();
					var _l = $("#holDayList").find(".holDayClass");
					if(_l.length == 0){
						var _c = $("#noDataClone").find(".noData").clone();
						$("#holDayList").append(_c);
					}
				});
				$("#holDayList").append(_clone);				
				$("#holDayStart").val("");
				$("#holDayEnd").val("");
			});
			
			//	공휴일 설정
			$("#phAddBtn").on("click", function(){
				var _phDate = $("#phDate").val();
				if(_phDate == "" ){
					alert("공휴일을 입력해주세요");
					return;
				}
				var _check = true;
				var _len = $("#phDateList").find(".phDateClass");
				for(var i=0; i<_len.length; i++){
					var _hs = $(_len[i]).find("input[name=holiday_start_day]").val();
					if(_hs == _phDate ){
						_check = false;	
						break;
					}
				}
				if(!_check){
					alert("중복된 공휴일 일정을 등록 할 수 없습니다");
					return;
				}
				$("#phDateList").find(".noData").remove();
				var _clone = $("#phDateClone").find("li").clone();
				$(_clone).find(".phDateSpan").text(_phDate);
				$(_clone).find("input[name=holiday_type]").val(2);
				$(_clone).find("input[name=holiday_start_day]").val(_phDate);
				$(_clone).find("input[name=holiday_end_day]").val(_phDate);		
				$(_clone).find(".phDelBtn").on("click", function(){
					$(this).parents(".phDateClass").remove();
					var _l = $("#phDateList").find(".phDateClass");
					if(_l.length == 0){
						var _c = $("#noDataClone").find(".noData").clone();
						$("#phDateList").append(_c);
					}
				});
				$("#phDateList").append(_clone);				
				$("#phDate").val("");
			});	
			
			//	초기화 버튼						
			$("#resetBtn").on("click", function(){
				$("#store_tel").val("");
				$("input[name=lcate]").prop("checked", false);
				$("#mcateSelect").children(".areaSelect").remove();
				$("#storeText").val("");
				$("#storeKeyword").val("");
				$("#target-product").find(".productList").find(".imgDelBtn").click();
				$("#openDayList").find(".openDayClass").find(".openDayDelBtn").click();
				$("#dayOffList").find(".dayOffClass").find(".dayOffDelBtn").click();
				$("#breakDayList").find(".breakDayClass").find(".breakDelBtn").click();
				$("#holDayList").find(".holDayClass").find(".holdayDelBtn").click();
				$("#phDateList").find(".phDateClass").find(".phDelBtn").click();
			});
			
			//	저장 버튼
			$("#saveBtn").on("click", function(){
				if(confirm("저장하시겠습니까?")){
					var _storeName = $("#store_name").val();
					if(_storeName.trim() == ""){
						alert("매장명을 입력해주세요")
						return;
					}
					
					var _tel = $("#store_tel").val();
					if(!koreasoft.modules.regex.isTel(_tel)){
						alert("매장 전화번호 형식에 맞게 작성해주세요");
						return ;
					}
					if($("#mainImgTarget .mainImg").length == 0){
						alert("매장 대표 사진을 등록해주세요");
						return ;
					} 
					if($("#owner_busi_img").children().length == 0){
						alert("사업장 등록증을 등록해주세요");
						return ;
					} 
					var _lcate = $('input[name=lcate]:checked').val();
					if(_lcate == undefined){
						alert("대분류를 선택해주세요")
						return ;
					}
					var _mcateSelect = $("#mcateSelect option:selected").val();
					if(_mcateSelect == ""){
						alert("소분류를 선택해주세요");
						return ;
					}
					//	영업시간 체크
					if($("#openDayList").find(".openDayClass").length == 0){
						alert("영업 시간을 등록해주세요");
						return ;
					};
					//	휴무일 체크
					if($("#dayOffList").find(".dayOffClass").length == 0){
						alert("휴무일을 등록해주세요");
						return ;
					};				
					//	휴게 시간 체크
					if($("#breakDayList").find(".breakDayClass").length == 0){
						alert("휴게시간을 등록해주세요");
						return ;
					};					
					//	휴가 설정(긴급 휴무) 체크
					if($("#holDayList").find(".holDayClass").length == 0){
						alert("휴가를 등록해주세요");
						return ;
					};					
					//	공휴일 체크
					if($("#phDateList").find(".phDateClass").length == 0){
						alert("공휴일을 등록해주세요");
						return ;
					};					
					//	상품명 체크
					if($("#target-product").find(".productList").length == 0){
						alert("상품을 등록해주세요");
						return ;
					};	
					var _storeText = $("#storeText").val();
					if(_storeText ==""){
						alert("가게 상세 소개 및 공지를 입력해주세요");
						return ;
					}
					var _storeKeyword = $("#storeKeyword").val();
					if(_storeKeyword ==""){
						alert("검색 키워드를 입력해주세요");
						return ;
					}
					var _arr = $("#storeKeyword").val().split(",")
					if(_arr.length > 5){
						alert("검색키워드는 최대 5개 입니다, 5개이하로 작성해주세요");
						return ;
					}
					var _obj = {};
					_obj.store_tel = _tel;
					_obj.owner_main_img = []
					_obj.owner_main_img_seq = $("#owner_main_img").find("input[name=main_file_seq]").val();
					_obj.owner_busi_img = _getImgObj($("#owner_busi_img"));
					_obj.lcate_seq = $("input[name=lcate]:checked").val();
					_obj.mcate_seq = $("#mcateSelect").val();
					_obj.store_text = _storeText;
					_obj.store_keyword = _storeKeyword;
					_obj.store_name = _storeName;
					_obj.product = [];
					_obj.open = [];
					_obj.break = [];
					_obj.holiday = [];
					_obj.off = [];
					_obj.del_file = [];
					//	메인 이미지
					var _mainImg = $("#mainImgTarget .mainImg");
					for(var i=0; i<_mainImg.length; i++){
						_obj.owner_main_img.push(_getImgObj($(_mainImg[i])));
					}
					//	영업시간
					var _open = $("#openDayList").find(".openDayClass");
					for(var i=0; i<_open.length; i++){
						var _openObj = {};
						_openObj.open_day	= $(_open[i]).find("input[name=open_day]").val();
						_openObj.open_time	= $(_open[i]).find("input[name=open_time]").val();
						_openObj.close_time	= $(_open[i]).find("input[name=close_time]").val();
						_obj.open.push(_openObj);	
					}
					//	휴무일
					var _off = $("#dayOffList").find(".dayOffClass");
					for(var i=0; i<_off.length; i++){
						var _offObj = {};
						_offObj.day_off_week	= $(_off[i]).find("input[name=day_off_week]").val();
						_offObj.day_off_day		= $(_off[i]).find("input[name=day_off_day]").val();
						_obj.off.push(_offObj);	
					}
					//	휴게시간
					var _break = $("#breakDayList").find(".breakDayClass");
					for(var i=0; i<_break.length; i++){
						var _breakObj = {};
						_breakObj.break_day		= $(_break[i]).find("input[name=break_day]").val();
						_breakObj.open_time		= $(_break[i]).find("input[name=open_time]").val();
						_breakObj.close_time	= $(_break[i]).find("input[name=close_time]").val();
						_obj.break.push(_breakObj);						
					}
					//	휴가설정(긴급 휴무)
					var _holDay = $("#holDayList").find(".holDayClass");
					for(var i=0; i<_holDay.length; i++){
						var _holDayOff = {};
						_holDayOff.holiday_type			= $(_holDay[i]).find("input[name=holiday_type]").val();
						_holDayOff.holiday_start_day	= $(_holDay[i]).find("input[name=holiday_start_day]").val();
						_holDayOff.holiday_end_day	= $(_holDay[i]).find("input[name=holiday_end_day]").val();
						_obj.holiday.push(_holDayOff);						
					}
					//	공휴일
					var _ph = $("#phDateList").find(".phDateClass");
					for(var i=0; i<_ph.length; i++){
						var _phObj = {};
						_phObj.holiday_type			= $(_ph[i]).find("input[name=holiday_type]").val();
						_phObj.holiday_start_day	= $(_ph[i]).find("input[name=holiday_start_day]").val();
						_phObj.holiday_end_day	= $(_ph[i]).find("input[name=holiday_end_day]").val();
						_obj.holiday.push(_phObj);						
					}
					//	상품명
					var _pro = $("#target-product").find(".productList");
					for(var i=0; i<_pro.length; i++){
						var _proObj = {};
						_proObj.product_name = $(_pro[i]).find(".productName").text();
						_proObj.product_price = Number($(_pro[i]).find(".priductPrice").text().replaceAll(",",""));
						_proObj.file = _getImgObj($(_pro[i]));
						_obj.product.push(_proObj);
					}
					var _delFileLen = $("#del_file").children().length;
					if( _delFileLen!= 0){
						for(var i=0; i<_delFileLen; i++){
							var _target = $("#del_file").children();
							var _fileSeq = $(_target[i]).val();
							_obj.del_file.push(_fileSeq);
						}
					}
					var _url = "/owner/st/saveProc";
					_doAjax(_url, _obj, _saveCallBack);
				}
			});
		}
		
		function _saveCallBack(res){
			if(res.result == false || res.result == 'false'){
				alert("저장에 실패하였습니다");
			}else{
				alert("저장에 성공하였습니다");
				window.location.reload();
			}
		}
		
		//	상품 : 이미지
		var _productBtn = $("#target-product").find(".productList").find(".imgPrevBtn");
		$(_productBtn).on("click", function(){
			_addPrevEvt(this,3);
		});
		
		//	상품 : 삭제 imgDelBtn
		var _productDelBtn = $("#target-product").find(".productList").find(".imgDelBtn");
		$(_productDelBtn).on("click", function(){
			_delImgBtn(this, 3);
		});
		
		//	영업시간
		var _openDelBtn = $("#openDayList").find(".openDayClass").find(".openDayDelBtn");
		$(_openDelBtn).on("click", function(){
			$(this).parents(".openDayClass").remove();
			var _l = $("#openDayList").find(".openDayClass");
			if(_l.length == 0){
				var _c = $("#noDataClone").find(".noData").clone();
				$("#openDayList").append(_c);
			}
		});
		
		//	휴무일
		var _dayOffDelBtn = $("#dayOffList").find(".dayOffClass").find(".dayOffDelBtn");
		$(_dayOffDelBtn).on("click", function(){
			$(this).parents(".dayOffClass").remove();
			var _l = $("#dayOffList").find(".dayOffClass");
			if (_l.length == 0) {
				var _c = $("#noDataClone").find(".noData").clone();
				$("#dayOffList").append(_c);
			}
		})
		
		//	휴게 시간
		var _breakDelBtn = $("#breakDayList").find(".breakDayClass").find(".breakDelBtn");
		$(_breakDelBtn).on("click", function(){
			$(this).parents(".breakDayClass").remove();
			var _l = $("#breakDayList").find(".breakDayClass");
			if (_l.length == 0) {
				var _c = $("#noDataClone").find(".noData").clone();
				$("#breakDayList").append(_c);
			}
		})
		
		//	휴가 설정(긴급휴무)
		var _holDelBtn = $("#holDayList").find(".holDayClass").find(".holdayDelBtn");
		$(_holDelBtn).on("click", function(){
			$(this).parents(".holDayClass").remove();
			var _l = $("#holDayList").find(".holDayClass");
			if (_l.length == 0) {
				var _c = $("#noDataClone").find(".noData").clone();
				$("#holDayList").append(_c);
			}
		});
		
		//	공휴일 설정
		var _phDelBtn = $("#phDateList").find(".phDateClass").find(".phDelBtn");
		$(_phDelBtn).on("click", function(){
			$(this).parents(".phDateClass").remove();
			var _l = $("#phDateList").find(".phDateClass");
			if (_l.length == 0) {
				var _c = $("#noDataClone").find(".noData").clone();
				$("#phDateList").append(_c);
			}
		});
		
		function _getImgObj(target) {
			var _obj = {};
			_obj.file_dtl_desc = $(target).find("input[name=file_dtl_desc]").val();
			_obj.file_dtl_ext = $(target).find("input[name=file_dtl_ext]").val();
			_obj.file_dtl_origin = $(target).find("input[name=file_dtl_origin]").val();
			_obj.file_dtl_path = $(target).find("input[name=file_dtl_path]").val();
			_obj.file_dtl_url_path = $(target).find("input[name=file_dtl_url_path]").val();
			_obj.file_type = $(target).find("input[name=file_type]").val();
			_obj.file_seq = $(target).find("input[name=file_seq]").val();

			return _obj;
		}
		
		function _delImgBtn(target, type){	
			if(type==1){
				$(target).parents(".mainImg").remove();
			}
			else if(type == 3 ){
				var _fileSeq = $(target).parents("li.productList").find("input[name=file_seq]");
				if($(_fileSeq).val() != ""){
					var _clone = $(_fileSeq).clone();
					$("#del_file").append(_clone);
				}
				$(target).parents("li.productList").remove();
				if($("#target-product").children().length == 0){
					var _clone = $("#noProduct").find("li").clone();
					$("#target-product").append(_clone);
				}
			}
		}
		
		//	미리보기 이벤트
		function _addPrevEvt(target, type){
			if(type == 1){
				var _src = $(target).parents(".mainImg").find("input[name=file_dtl_url_path]").val();
				if(_src == undefined){
					return;
				}
				var _image = new Image();
				_image.src = _src; 
				_imageShow(_image);
			}else if(type == 2){
				var _src = $("#owner_busi_img").find(".file_image").attr("src");
				if(_src == undefined){
					return;
				}
				var _image = new Image();
				_image.src = _src; 
				_imageShow(_image);
				
			}else if(type == 3){
				var _image = new Image();
				_image.src = $(target).parents(".productList").find(".file_image").attr("src");
				_imageShow(_image);	
			}
		}
		
		function _doImageAjax(target, type, callFunction) {
			var _formData = new FormData();
			var _file = $(target)[0].files[0];
			if (!koreasoft.modules.regex.imgSizeCheck(_file.size)) {
				alert("1메가 이하의 파일을 올려주세요");
				return;
			}
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, callFunction, target, type);
		}
		
		//	이미지 업로드시 체인지 이벤트
		function _changeEvt(target, type) {
			var _isImg = koreasoft.modules.regex.isImgExt($(target).val());
			if (!_isImg) {
				$(target).val("")
				return alert('이미지만 업로드 할수 있습니다');
			}else{
				_deleteImageBox(target, type);
			}
			
			if(type==1){
				if($("#mainImgTarget .mainImg").length == 5){
					alert("매장 대표 이미지는 최대 5장 업로드 할 수 있습니다");
					return;
				}
			}
			
			_doImageAjax(target, type, _makeData);
		}	
		
		//	데이터 생성
		function _makeData(res, target, type) {
			if (res.result == false || res.result == 'false') {
				alert("파일 업로드에 문제가 생겼습니다");
				return;
			}
			var _clone = $("#fileClone").children().clone();
			var _fileName = res.files[0].file_dtl_origin;
			$(_clone).find("input[name=file_type]").val(type == 1?  8 : type == 2? 9 : 10);
			$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(_clone).find("input[name=file_dtl_origin]").val(_fileName);
			$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
			$(_clone).find(".file_image").attr('src', res.files[0].file_dtl_url_path);
			
			if (type == 1) {
				var _c = $("#main_img_clone").find("li").clone();
				$(_c).find(".mainImgDelBtn").on("click", function(){
					_delImgBtn(this, 1);
				});
				$(_c).find(".mainImgViewBtn").on("click", function(){
					_addPrevEvt(this, 1);
				});
				$(_c).find(".mainImgName").text(_fileName);
				$(_c).find("p").append(_clone);
				$("#mainImgTarget").append(_c);
			} else if(type == 2) {
				_deleteImageBox(target, type);
				$("#owner_busi_img").append(_clone);
				$(".busi_img").val(res.files[0].file_dtl_origin);
			} else if(type == 3){
				_deleteImageBox(target, type);
				$("#productImsiImg").append(_clone);
				$(".product-file").val(res.files[0].file_dtl_origin);
			}
			$(target).val("");
		}			
		
		//	이미지 삭제
		function _deleteImageBox(target, type){
			if(type == 1){
				
			}else if(type == 2){
				var _fileSeq = $("#owner_busi_img").find("input[name=file_seq]").val();
				if(_fileSeq != ""){
					var _clone = $("#owner_busi_img").find("input[name=file_seq]").clone();
					$("#del_file").append(_clone);					
				}
				$("#owner_busi_img").children().remove()
			}else if(type == 3){
				$("#productImsiImg").children().remove();
			}
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
		return{
			init : _init
		}
	}
	
	new _ownerStDtl().init();
})