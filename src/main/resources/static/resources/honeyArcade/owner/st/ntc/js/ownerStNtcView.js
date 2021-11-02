$(function(){
	var _ntcView = function(){
		var _init = function(){
			$("#listBtn").on("click", function(){
				location.href = "/owner/st/ntc/list";
			})
			
			$("#ntcPrevBtn").on("click", function(){
				_doNtcView(this);
			})
			
			$("#ntcNextBtn").on("click", function(){
				_doNtcView(this);
			})
		}
		
		function _doNtcView(target){
			var _ntcSeq = $(target).attr("data-seq")
			$("#hiddenForm").find("input[name=ntc_seq]").val(_ntcSeq)
			$("#hiddenForm").submit();
		}
		
		return {
			init : _init
		}
	};
	
	
	new _ntcView().init();
})