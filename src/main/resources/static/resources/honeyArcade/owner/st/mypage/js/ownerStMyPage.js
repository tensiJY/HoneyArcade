$(function(){
	var _ownerStMypage = function(){
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
						
			//	데이트 피커
			$("#areaStartDay").datepicker(_dateOption);
			$("#areaEndDay").datepicker(_dateOption);
			$("#buildStartDay").datepicker(_dateOption);
			$("#buildEndDay").datepicker(_dateOption);
			$("#buildStartDay").datepicker(_dateOption);
			$("#buildEndDay").datepicker(_dateOption);
			$("#couponStartDay").datepicker(_dateOption);
			$("#couponEndDay").datepicker(_dateOption);
									
			//	지역광고 현황 조회
			$("#areaSearchBtn").on("click", function(){
				var _sDay = $("#areaStartDay").val();
				var _eDay = $("#areaEndDay").val();
				
				var _check  = _isDateCheck(_sDay, _eDay); 
				if(!_check){
					return;
				}
				
				var _type = 2;
				
				var _obj = {};
				_obj.type = _type;
				_obj.banner_start_day = _sDay;
				_obj.banner_end_day  = _eDay;
				
				var _url = "/owner/st/getAdData";
				_doAjax(_url, _obj, _type, _searchCallback)
			})
			
			//	건물광고 현황 조회
			$("#buildSearchBtn").on("click", function(){
				var _sDay = $("#buildStartDay").val();
				var _eDay = $("#buildEndDay").val();
				
				var _check  = _isDateCheck(_sDay, _eDay); 
				if(!_check){
					return;
				}
								
				var _type = 1;
				var _obj = {};
				_obj.type = _type;
				_obj.banner_start_day = _sDay;
				_obj.banner_end_day  = _eDay;
				
				var _url = "/owner/st/getAdData";
				_doAjax(_url, _obj, _type, _searchCallback)
			})
			
			//	쿠폰광고 현황 조회
			$("#couponSearchBtn").on("click", function(){
				var _sDay = $("#couponStartDay").val();
				var _eDay = $("#couponEndDay").val();
				
				var _check  = _isDateCheck(_sDay, _eDay); 
				if(!_check){
					return;
				}
								
				var _type = 3;
				var _obj = {};
				_obj.type = _type;
				_obj.coupon_start_day = _sDay; 
				_obj.coupon_end_day   = _eDay;
				
				var _url = "/owner/st/getAdData";
				_doAjax(_url, _obj, _type, _searchCallback)
			})			
			
			//	혜택 보기
			$(".beneBtn").on("click", function(){
				location.href = "/owner/ad/bene";
			})
			
			//	지역광고 : 쿠폰 신청
			$("#areaAdReqBtn").on("click", function(){
				location.href = "/owner/ad/area";
			});
			
			//	건물광고 : 쿠폰 신청
			$("#buildAdReqBtn").on("click", function(){
				location.href = "/owner/ad/build";
			});
			
			//	쿠폰광고 : 쿠폰 신청
			$("#couponAdReqBtn").on("click", function(){
				location.href = "/owner/ad/coupon";
			});
						
			//	차트 조회 버튼
			$("#searchChart").on("click", function(){
				_getChartData();
			})
			
			$("#searchChart").click();
			
			var startDate;
    		var endDate;
						
			$('#weekDate').datepicker({
				showOtherMonths: true,
				selectOtherMonths: true,
				selectWeek: true,
				changeYear: true,
				dateFormat: 'yy-mm-dd',
				onSelect: function(dateText, inst) {
					
					var date = $(this).datepicker('getDate');
					startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() +1);
					endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 7);
					
					var dateFormat = 'yy-mm-dd'
					startDate = $.datepicker.formatDate(dateFormat, startDate, inst.settings);
					endDate = $.datepicker.formatDate(dateFormat, endDate, inst.settings);

					$('#weekDate').val(startDate + ' ~ ' + endDate);

					setTimeout(applyWeeklyHighlight(), 100);
				},
				beforeShow: function() {
					setTimeout(applyWeeklyHighlight(), 100);
				}
			});
		}
		
		function applyWeeklyHighlight() {
			
			$('.ui-datepicker-calendar tr').each(function() {

				if ($(this).parent().get(0).tagName == 'TBODY') {
					$(this).mouseover(function() {
						$(this).find('a').css({
							'background': '#ffffcc',
							'border': '1px solid #dddddd'
						});
						$(this).find('a').removeClass('ui-state-default');
						$(this).css('background', '#ffffcc');
					});

					$(this).mouseout(function() {
						$(this).css('background', '#ffffff');
						$(this).find('a').css('background', '');
						$(this).find('a').addClass('ui-state-default');
					});
				}

			});
		}
		
		//	차트 데이터 가져오기
		function _getChartData(){
			var _date = $("#weekDate").val();
			
			if(_date.indexOf("~")< 0){
				alert("날자 형식에 맞게 입력해주세요");
				return;
			}
			
			var _arr = _date.split("~");
			
			if(_arr.length != 2){
				alert("날자 형식에 맞게 입력해주세요");
				return;				
			}
			
			var _sDay = _arr[0].trim();
			var _eDay = _arr[1].trim();
			
			var _check  = _isDateCheck(_sDay, _eDay); 
			if(!_check){
				return;
			}
			
			var _dateDiff = koreasoft.modules.stringUtil.dateDiff(_sDay, _eDay);
			if(_dateDiff > 30){
				alert("조회기간은 최대 7일 입니다");
				return;
			}
						
			var _obj = {};
			_obj.search_start_day = _sDay;
			_obj.search_end_day = _eDay;							
			var _url = "/owner/st/getChartData";
			
			_doAjax(_url, _obj, null, _drawChart);
		}
		
		var _ctx = null;
		var _myChart = null;
		
		function _drawChart(res){
			if(res.result == false || res.result == 'false'){
				alert("데이터를 처리하는 중 오류가 발생하였습니다 관리자에게 문의 바랍니다");
			}else{
				console.log(res);
				
				_ctx = document.getElementById('myChart');
				if(_myChart != null){
					_myChart.destroy();
				}
				
				var _min = 0;
				var _max = 0;
				
				var _backgroundColor = [
										'rgba(255, 99, 132, 0.2)',
						                'rgba(54, 162, 235, 0.2)',
						                'rgba(255, 206, 86, 0.2)',
						                'rgba(75, 192, 192, 0.2)',
						                'rgba(153, 102, 255, 0.2)',
						                
										];
				var _borderColor = 	[
										'rgba(255, 99, 132, 1)',
						                'rgba(54, 162, 235, 1)',
						                'rgba(255, 206, 86, 1)',
						                'rgba(75, 192, 192, 1)',
						                'rgba(153, 102, 255, 1)',
						                'rgba(255, 159, 64, 1)',
									]
									
				var _date = ["일", "월", "화", "수", "목", "금", "토"];					
				
				var _data = {};
				_data.labels = [];
				_data.datasets = [];
				var _dataObj = {};
				_dataObj.label = '쿠폰 이용 현황';
				_dataObj.data = [];
				_dataObj.backgroundColor = [];
				_dataObj.borderColor = [];
				_dataObj.borderWidth= 1;
				
				var _list = res.dataList;
				for(var i=0; i<_list.length; i++){
					_dataObj.data.push(_list[i].used_count);
					_dataObj.backgroundColor.push(_backgroundColor[0]);
					_dataObj.borderColor.push(_borderColor[0]);
					
					var _l = _list[i].month + "/" + _list[i].day + " " + _date[_list[i].date - 1]					
					_data.labels.push(_l)
					
					if(i==0){
						_max = _list[i].used_count
					}else{
						if(_max < _list[i].used_count){
							_max = _list[i].used_count;
						}
					}
					
					
				}
				
				_data.datasets.push(_dataObj);
				
				_myChart = new Chart(_ctx, {
						    type: 'line',
						    data: _data
						    ,
					options: {
						responsive: true,
						plugins: {
							legend: {
								position: 'top',
							},
							title: {
								display: false,
							}
						},
						sclaes : {
							yAxes :[
								{
									ticks : {
										min : _min,
										max : _max
									}
								}
							]
						}
					}
				});
			}
		}
		
		function _searchCallback(res, type){
			if(res.result == false || res.result == 'false'){
				alert("데이터를 처리하는 중 오류가 발생하였습니다 관리자에게 문의 바랍니다");
			}else{
				///debugger;
				var _list = res.dataList;
				
				if(type == 1){
					var _target = $("#buildUl");
					$(_target).children().remove();
					
					if(_list.length == 0){	
						_noDataAppend(_target);
					}else{
						_appendBlank(_target);
						
						for(var i=0; i<_list.length; i++){
							var _data = _list[i];
							var _clone = $("#clone1").find("li").clone();							
							$(_clone).find(".evtTitle").text(_data.event_title);
							$(_clone).find(".evtDate").text(_data.pay_dtl_use == 5? _data.banner_start_day + " ~ " + _data.banner_end_day : "-");
							$(_clone).find(".useText").text(_data.use_text);
							$(_target).append(_clone);
						}
						_appendBlank(_target);
					}
					
				}else if(type == 2){
					var _target = $("#areaUl");
					$(_target).children().remove();
					
					if(_list.length == 0){	
						_noDataAppend(_target);
					}else{
						_appendBlank(_target);
						
						for(var i=0; i<_list.length; i++){
							var _data = _list[i];
							var _clone = $("#clone1").find("li").clone();							
							$(_clone).find(".evtTitle").text(_data.event_title);
							$(_clone).find(".evtDate").text(_data.pay_dtl_use == 5? _data.banner_start_day + " ~ " + _data.banner_end_day : "-");
							$(_clone).find(".useText").text(_data.use_text);
							$(_target).append(_clone);
						}
						_appendBlank(_target);
					}
				}else if(type == 3){
					var _target = $("#couponUl");
					$(_target).children().remove();
					
					if(_list.length == 0){	
						_noDataAppend(_target);
					}else{
						_appendBlank(_target);
						
						for(var i=0; i<_list.length; i++){
							var _data = _list[i];
							var _clone = $("#clone1").find("li").clone();							
							$(_clone).find(".evtTitle").text(_data.event_title);
							$(_clone).find(".evtDate").text(_data.pay_dtl_use == 5? _data.coupon_start_day + " ~ " + _data.coupon_end_day : "-");
							$(_clone).find(".useText").text(_data.use_text);
							$(_target).append(_clone);
						}
						_appendBlank(_target);
					}
				}
			}	
		}
		
		function _appendBlank(target){
			var _clone = $("#clone2").find("li").clone();
			$(target).append(_clone);
		}
		
		function _noDataAppend(target){
			var _clone = $("#noDataClone").children().clone()
			$(target).append(_clone);
		}
	
		function _isDateCheck(sDay, eDay){
			if(sDay == ""){
				alert("검색 시작일을 입력해주세요");
				return false;
			}
			
			if(eDay == ""){
				alert("검색 종료일을 입력해주세요");
				return false;
			}
			
			if(sDay > eDay){
				alert("검색 시작일은 종료일보다 작아야 합니다");
				return false;
			}
			
			return true;
		}		
		
		function _doAjax(url, obj,  type , callback){
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
					callback(res, type);
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
		
		
		return {
			init : _init
		}
	}
	
	new _ownerStMypage().init();
	
})