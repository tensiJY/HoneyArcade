$(function(){
	var _regForm = function(){
		
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
									
			var pageBtn = document.querySelectorAll('.page');
			var page = document.querySelectorAll('article');
			
			var pageNum;
			
			pageBtn.forEach(function(item) {
				item.addEventListener('click', function(e) {
					e.preventDefault;
					pageNum = e.target.dataset.num;
								
					var _isCheck = _pageCheck(pageNum);
					
					if(!_isCheck){
						return;
					}
					
					page.forEach(function(item) {
						item.classList.remove('on')
					})
					page[pageNum].classList.add('on')
				})
			});
			
			$("#allAgree").on("change", function(){
				var _v = $(this).prop("checked");
				if(_v){
					$("#agree1").prop("checked", true);
					$("#agree2").prop("checked", true);
				}else{
					$("#agree1").prop("checked", false);
					$("#agree2").prop("checked", false);
				}
			});
			
			$("#agree1").on("change", function(){
				if($(this).prop("checked")){
					if($("#agree2").prop("checked")){
						$("#allAgree").prop("checked", true);			
					}
				}else{
					$("#allAgree").prop("checked", false);
				}
			});
			
			$("#agree2").on("change", function(){
				if($(this).prop("checked")){
					if($("#agree1").prop("checked")){
						$("#allAgree").prop("checked", true);			
					}
				}else{
					$("#allAgree").prop("checked", false);
				}
			});
			
			//	아이디 중복 확인
			$("#idCheckBtn").on("click", function(){
				var _userName = $("#username").val().trim();
				var _obj = {};
				_obj.member_id = _userName;
				if(_userName == "" || _userName == null){
					$("#idMsg").text("아이디를 입력해주시기 바랍니다");
					$("#check_username").val("");
					_idMsgShow();
					return 
				}
				
				var _check = koreasoft.modules.regex.isId(_userName);
				if(!_check){
					$("#idMsg").text("아이디는 영문자로 시작하며 6~20자 영문자 숫자 이어야 합니다");
					$("#check_username").val("");
					_idMsgShow();
					return;
				}
				
				var _url = "/reg/isId";
				
				_doAjax(_url, _obj, _idCheckCall);
			});
			
			//	아이디 on 이벤트 제거
			$("#username").on("focus", function(){
				$("#idMsg").text("");
				$("#idMsg").removeClass("on")
			});
			
			$("#password").on("keyup", function(){
				var _p = $("#password").val();
				var _c = $("#passwordCheck").val();
				if(_p == _c){
					if(!koreasoft.modules.regex.isPassword(_p)){
						$("#isPassword").removeClass("correct");
						$("#isPassword").addClass("incorrect");	
					}else{
						$("#isPassword").removeClass("incorrect");
						$("#isPassword").addClass("correct");	
					}
				}else{
					$("#isPassword").removeClass("correct");
					$("#isPassword").addClass("incorrect");
				}
			});
			
			$("#passwordCheck").on("keyup", function(){
				var _p = $("#password").val();
				var _c = $("#passwordCheck").val();
				if(_p == _c){
					if(!koreasoft.modules.regex.isPassword(_p)){
						$("#isPassword").removeClass("correct");
						$("#isPassword").addClass("incorrect");	
					}else{
						$("#isPassword").removeClass("incorrect");
						$("#isPassword").addClass("correct");	
					}
				}else{
					$("#isPassword").removeClass("correct");
					$("#isPassword").addClass("incorrect");
				}
			})
			
			//	이메일 체인지 이벤트
			$("#emailSelect").on("change", function(){
				if($(this).val() == 0){
					$("#email_sub").val("");
				}else{
					var _text = $("select[id=emailSelect] option:selected").text();
					$("#email_sub").val(_text)
				}
			})
			
			//	이메일 중복 확인
			$("#emailCheckBtn").on("click", function(){
				var _pre = $("#email_pre").val().trim();
				var _sub = $("#email_sub").val().trim();
				if(_pre == "" || _pre == null){
					$("#email_pre").focus();
					alert("이메일을 입력해주세요");
					return;
				}
				if(_sub == "" || _sub == null){
					$("#email_sub").focus();
					alert("이메일을 입력해주세요");
					return;
				}
				var _ownerEmail = _pre + "@"+ _sub;;
				if(!koreasoft.modules.regex.isEmail(_ownerEmail)){
					alert("이메일 형식에 맞게 입력해주세요");
					return;
				}
				var _url = "/reg/isEmail";
				var _obj = {};
				_obj.owner_email = _ownerEmail;
				_doAjax(_url, _obj, _emailCheckCall);
			});
			
			//	매장 대표 사진
			$("#main_img").on("change", function(){
				_changeEvt(this, 1)
			});
			
			$("#mainImgAddBtn").on("click", function(){
				var _mainImg = $("#owner_main_img").children('div')
				var _mainImgCnt = $("#mainImgTarget").find("li.owner_main_img").length;
				if($(_mainImg).length == 0){
					alert("매장 대표 사진을 등록해주세요");
					return;
				}
				if(_mainImgCnt==0){
					$("#mainImgTarget").children().remove();
				}else if(_mainImgCnt == 5){
					alert("매장 대표 사진은 최대 5장입니다");
					return;
				}
				var _clone = $("#ownerMainImgClone").find("li").clone();
				$(_clone).find("input[name=file_dtl_desc]").val($(_mainImg).find("input[name=file_dtl_desc]").val());
				$(_clone).find("input[name=file_dtl_ext]").val($(_mainImg).find("input[name=file_dtl_ext]").val());
				$(_clone).find("input[name=file_dtl_origin]").val($(_mainImg).find("input[name=file_dtl_origin]").val());
				$(_clone).find("input[name=file_dtl_path]").val($(_mainImg).find("input[name=file_dtl_path]").val());
				$(_clone).find("input[name=file_dtl_url_path]").val($(_mainImg).find("input[name=file_dtl_url_path]").val());
				$(_clone).find("input[name=file_type]").val($(_mainImg).find("input[name=file_type]").val());
				$(_clone).find("input[name=file_seq]").val($(_mainImg).find("input[name=file_seq]").val());
				$(_clone).find("img").attr("src", $(_mainImg).find("img").attr("src"));
				$(_clone).find(".imageName").text($(_mainImg).find("input[name=file_dtl_origin]").val());
				$(_clone).find(".mainImgViewBtn").on("click", function(){
					_addPrevEvt(this, 1);
				});
				$(_clone).find(".mainImgDelBtn").on("click", function(){
					$(this).parents(".owner_main_img").remove();
					if($("#mainImgTarget").find("li.owner_main_img").length==0){
						$("#mainImgTarget").append($("#noDataClone").find("li").clone());
					}
				});
				$("#mainImgTarget").append(_clone);
				$(".main_img").val("");
				$(_mainImg).remove();
			});
			
			//	사업자 등록증
			$("#busi_img").on("change", function(){
				_changeEvt(this, 2)
			});
			
			//	상품 이미지 첨부
			$("#product_file").on("change", function(){
				_changeEvt(this, 3);
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
					_delImgBtn(this);
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
			
			//	회원 가입
			$("#saveBtn").on("click", function(){
				if(confirm("회원 가입 하시겠습니까?")){
					var _check = null;
					for(var i=0; i<5; i++){
						_check = _pageCheck(i);
						
						if(! _check ){
							break;
						}
					}
					
					if(!_check){
						return;
					}
					
					var _obj = {};
					_obj.username = $("#username").val().trim();
					_obj.password = $("#password").val().trim();
					_obj.owner_phone = $("#phone1").val() + "-" + $("#phone2").val()+ "-" + $("#phone3").val();
					_obj.owner_email = $("#email_pre").val() + "@" + $("#email_sub").val();
					_obj.owner_main_img = [];
					_obj.owner_busi_img = _getImgObj($("#owner_busi_img"));
					_obj.sido_seq = $("#sidoSelect").val();
					_obj.sigungu_seq = $("#sigunguSelect").val();
					_obj.dong_seq = $("#dongSelect").val();
					_obj.build_seq = $("#buildSelect").val();
					_obj.floor_seq = $("#floorSelect").val();
					_obj.store_ho = $("#hoSelect option:selected").val();
					_obj.store_tel = $("#tel1").val() + "-" + $("#tel2").val() + "-" + $("#tel3").val();
					_obj.lcate_seq = $("input[name=lcate]:checked").val();
					_obj.mcate_seq = $("#mcateSelect").val();
					_obj.store_text = $("#storeText").val();
					_obj.store_keyword = $("#storeKeyword").val();
					_obj.store_name = $("#storeName").val();
					_obj.product = [];
					_obj.open = [];
					_obj.break = [];
					_obj.holiday = [];
					_obj.off = [];
					_obj.pre_owner_id = $("#hoSelect option:selected").attr("data-pre-owner-id"); 
					
					//	메인이미지
					var _mainImg = $("#mainImgTarget").children(".owner_main_img");
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
					var _url = "/reg/saveProc";
					_doAjax(_url, _obj, _saveCallBack);
				}
			})
		}//	init end
		
		function _saveCallBack(res){
			if(res.result == false || res.result == 'false'){
				alert("회원 가입에 실패하였습니다");
			}else{
				alert("가입에 성공하였습니다 \r\n승인이 완료 된 후 이용하실 수 있습니다");
				location.href = "/login/form";
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
		
		function _delImgBtn(target){
			$(target).parents("li.productList").remove();
			if($("#target-product").children().length == 0){
				var _clone = $("#noProduct").find("li").clone();
				$("#target-product").append(_clone);
			}
		}
		
		//	미리보기 이벤트
		function _addPrevEvt(target, type){
			var _image = new Image();
			if(type == 1){
				_image.src = $(target).parents(".owner_main_img").find(".file_image").attr("src");
			}
			else if(type == 3){
				_image.src = $(target).parents(".productList").find(".file_image").attr("src");
			}
			_imageShow(_image);	
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
			_doImageAjax(target, type, _makeData);
		}
		
		//	이미지 삭제
		function _deleteImageBox(target, type){
			if(type == 1){
				$("#owner_main_img").children().remove()
			}else if(type == 2){
				$("#owner_busi_img").children().remove()
			}else if(type == 3){
				$("#productImsiImg").children().remove();
			}
		}
		
		//	데이터 생성
		function _makeData(res, target, type) {
			if (res.result == false || res.result == 'false') {
				alert("파일 업로드에 문제가 생겼습니다");
				return;
			}
			var _clone = $("#fileClone").children().clone();
			$(_clone).find("input[name=file_type]").val(type == 1?  8 : type == 2? 9 : 10);
			$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
			$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
			$(_clone).find(".file_image").attr('src', res.files[0].file_dtl_url_path);
			if (type == 1) {
				_deleteImageBox(target, type);	//	이미지 삭제
				$("#owner_main_img").append(_clone);
				$(".main_img").val(res.files[0].file_dtl_origin);
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
		
		//	아이디 메세지 이벤트
		function _idMsgShow(){
			$("#idMsg").addClass("on");
			setTimeout(function(){
				$("#idMsg").removeClass("on");	
			}, 8000)
		}
		
		function _pageCheck(pageNum){
			var _result = false;
			if(pageNum == 1){
				var _agree1 = $("#agree1").prop("checked");
				if(!_agree1){
					alert("이용 약관 동의를 선택해주세요");
					return _result;
				}
				var _agree2 = $("#agree2").prop("checked");
				if(!_agree2){
					alert("개인정보 수집 및 이용 약관 동의를 선택해주세요");
					return _result;
				}
				if(_agree1 == true && _agree2 == true){
					$("#allAgree").prop("checked", true);
				}
			}
			else if(pageNum == 2){
				
				var _chkUserName = $("#check_username").val();
				var _userName = $("#username").val();
				var _password = $("#password").val();
				var _passwordCheck = $("#passwordCheck").val();
				var _mobile = $("#phone1").val() + "-" + $("#phone2").val()+ "-" + $("#phone3").val();
				var _email = $("#email_pre").val() + "@" + $("#email_sub").val();
				var _checkEmail = $("#check_email").val();
								
				if(!koreasoft.modules.regex.isId(_userName)){
					$("#idMsg").text("아이디는 영문자로 시작하며 6~20자 영문자 숫자 이어야 합니다");
					$("#check_username").val("");
					_idMsgShow();
					return _result;
				}
				if(_chkUserName != _userName){
					$("#idMsg").text("아이디 중복 체크 해주세요");
					$("#check_username").val("");
					_idMsgShow();
					return _result;					
				}
				if(!koreasoft.modules.regex.isPassword(_password)){
					alert("비밀번호는 8~16자 영문, 숫자, 특수문자 조합입니다")
					return _result;
				}
				if(_passwordCheck == ""){
					alert("패스워드 재확인을 작성해주세요");
					return _result;
				}
				if(_password != _passwordCheck){
					alert("패스워드가 일치하지 않습니다");
					return _result;
				}
				if(!koreasoft.modules.regex.isPhone(_mobile)){
					alert("휴대폰 번호 형식에 맞게 작성해주세요");
					return _result;
				}
				if(!koreasoft.modules.regex.isEmail(_email)){
					alert("이메일 형식에 맞게 작성해주세요");
					return _result;
				}
				if( _email != _checkEmail){
					alert("이메일 중복 체크를 해주세요");
					return _result;
				}
			}else if(pageNum == 3){
				if($("#mainImgTarget").children(".owner_main_img").length == 0){
					alert("매장 대표 사진을 등록해주세요");
					return _result;
				} 
				if($("#owner_busi_img").children().length == 0){
					alert("사업장 등록증을 등록해주세요");
					return _result;
				} 
				var _sido = $("#sidoSelect option:selected").val();
				if(_sido == ""){
					alert("시,도를 선택해주세요");
					return _result;
				}
				var _sigungu = $("#sigunguSelect option:selected").val();
				if(_sigungu == ""){
					alert("군,구를 선택해주세요");
					return _result;
				}
				var _dong = $("#dongSelect option:selected").val();
				if(_dong == ""){
					alert("지역을 선택해주세요");
					return _result;
				}				
				var _floorSelect = $("#floorSelect option:selected").val();
				if(_floorSelect == ""){
					alert("층수를 선택해주세요");
					return _result;
				}
				var _hoSelect = $("#hoSelect option:selected").val();
				if(_hoSelect == ""){
					alert("호실을 선택해주세요");
					return _result;
				}
				var _storeFlag = $("#hoSelect option:selected").attr("data-store-flag");
				if(_storeFlag == 2 || _storeFlag == '2'){
					// 1 미사용(점주가 없음),  2사용:(점주가있음)
					alert("사용할 수 없는 호실입니다");
					return _result;
				}
				var _storeName = $("#storeName").val();
				if(_storeName.trim() == ""){
					alert("상호명을 입력해주세요");
					return;
				}
				var _tel = $("#tel1").val() + "-" + $("#tel2").val() + "-" + $("#tel3").val();
				if(!koreasoft.modules.regex.isTel(_tel)){
					alert("매장 전화번호 형식에 맞게 작성해주세요");
					return _result;
				}
				var _lcate = $('input[name=lcate]:checked').val();
				if(_lcate == undefined){
					alert("대분류를 선택해주세요")
					return _result;
				}
				var _mcateSelect = $("#mcateSelect option:selected").val();
				if(_mcateSelect == ""){
					alert("소분류를 선택해주세요");
					return _result;
				}
				var _storeText = $("#storeText").val();
				if(_storeText ==""){
					alert("가게 상세 소개 및 공지를 입력해주세요");
					return _result;
				}
				var _storeKeyword = $("#storeKeyword").val();
				if(_storeKeyword ==""){
					alert("검색 키워드를 입력해주세요");
					return _result;
				}
				var _arr = $("#storeKeyword").val().split(",")
				if(_arr.length > 5){
					alert("검색키워드는 최대 5개 입니다, 5개이하로 작성해주세요");
					return _result;
				}
			}else if(pageNum == 4){
				//	영업시간 체크
				if($("#openDayList").find(".openDayClass").length == 0){
					alert("영업 시간을 등록해주세요");
					return _result;
				};
				//	휴무일 체크
				if($("#dayOffList").find(".dayOffClass").length == 0){
					alert("휴무일을 등록해주세요");
					return _result;
				};				
				//	휴게 시간 체크
				if($("#breakDayList").find(".breakDayClass").length == 0){
					alert("휴게시간을 등록해주세요");
					return _result;
				};					
				//	휴가 설정(긴급 휴무) 체크
				if($("#holDayList").find(".holDayClass").length == 0){
					alert("휴가를 등록해주세요");
					return _result;
				};					
				//	공휴일 체크
				if($("#phDateList").find(".phDateClass").length == 0){
					alert("공휴일을 등록해주세요");
					return _result;
				};					
				//	상품명 체크
				if($("#target-product").find(".productList").length == 0){
					alert("상품을 등록해주세요");
					return _result;
				};	
			}
			_result = true;
			return _result;
		}//	페이지 체크 이벤트
		
		//	이메일 중복 컬백 이벤트
		function _emailCheckCall(res){
			if(res.result == false || res.result == 'false'){
				alert("에러가 발생하였습니다. 관리자에게 문의바랍니다.");
			}else{
				alert(res.msg);	
				if(res.type == 1 || res.type == '1'){
					$("#check_email").val("");
				}else if(res.type==2 || res.type == '2'){
					//	사용할수 있는 EMAIL인경우
					$("#check_email").val(res.owner_email);
				}
			}
		}
		
		//	아이디 중복 컬백 이벤트
		function _idCheckCall(res){
			if(res.result == false || res.result == 'false'){
				alert("에러가 발생하였습니다. 관리자에게 문의바랍니다.");
			}else{
				$("#idMsg").text(res.msg);
				_idMsgShow();
				if(res.type == 1 || res.type == '1'){
					$("#check_username").val("");
				}else if(res.type==2 || res.type == '2'){
					//	사용할수 있는 ID인경우
					$("#check_username").val(res.member_id);
				}
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
		return {
			init : _init
		}
	}//
	
	new _regForm().init();
});