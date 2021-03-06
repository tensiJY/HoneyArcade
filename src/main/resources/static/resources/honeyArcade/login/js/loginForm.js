$(function(){
	var _loginForm = function(){
		
		var _init = function(){
			var _cookieData = koreasoft.modules.cookie.getCookie("HoneyArcade_Cookie");
		
			if( _cookieData != null){
				$(".id-onoff").addClass("on");
				$("#username").val(Decrypt(_cookieData, "HoneyArcade_Cookie"));
			}
			
		    const idBtn = document.querySelector(".id-onoff");
		    const passBtn = document.querySelector(".pass-onoff");
		    
		    idBtn.addEventListener('click',function(){
		      	idBtn.classList.toggle('on');
		      	var _result = $(this).hasClass("on")
		      	if(!_result){
					koreasoft.modules.cookie.deleteCookie("HoneyArcade_Cookie");
				}
		    })
		    
		    passBtn.addEventListener('click',function(){
		      	passBtn.classList.toggle('on')
		      	var _result = $(this).hasClass("on")
		      	if(_result){
					$("#password").attr("type", "text");
				}else{
					$("#password").attr("type", "password");
				}
		    })
		
		    const findId = document.querySelector(".find-id");
		    const findPass = document.querySelector('.find-pass');
		    const idModal = document.querySelector('.id-modal');
		    const passModal = document.querySelector('.pass-modal');
		    const modalClose = document.querySelectorAll('.close');
		    
		    findId.addEventListener('click', function(){
				idModal.classList.toggle('on');
				passModal.classList.remove('on')
		    })
		    
		    findPass.addEventListener('click', function(){
		      	passModal.classList.toggle('on');
		      	idModal.classList.remove('on')
		    })
		    
		    modalClose.forEach(function(item){
		      	item.addEventListener('click',function(e){
		        	e.path[1].classList.remove('on')
		      	})
		    })
			
			//	로그인 버튼
			$("#loginBtn").on("click", function(){
				
				var _username = $("#username").val().trim();
				if(_username == ""){
					alert("아이디를 입력해주세요");
					$("#usernmae").focus();
					return;
				}
				
				var _password = $("#password").val().trim();
				
				if(_password == ""){
					alert("비밀번호를 입력해주세요");
					$("#password").focus();
					return;
				}
				
				if($(".id-onoff").hasClass("on")){
					koreasoft.modules.cookie.setCookie("HoneyArcade_Cookie", Encrypt(_username, "HoneyArcade_Cookie"), 14);	
				}else{
					
					koreasoft.modules.cookie.deleteCookie("HoneyArcade_Cookie");
				}
				
				$("#loginForm").submit();
			});
			
			//	회원가입 버튼
			$("#regBtn").on("click", function(){
				location.href = "/reg/form";
			});
			
			//	엔터 EVT
			$("#username").on("keypress", function(e){
				if(e.keyCode == 13){
					$("#password").focus();
				}
			});

			//	엔터 EVT
			$("#password").on("keypress", function(e){
				if(e.keyCode == 13){
					
					$("#loginBtn").click();
				}
			});
			
			//	아이디 찾기
			$("#findId").on("click", function(e){
				var _target = $("#id_email");
				var _ownerEmail = $(_target).val().trim();
				
				if("" == _ownerEmail){
					$(_target).focus();
					return alert("이메일을 입력해주세요");
				}
				
				var _type = 1;
				var _url = "/login/findid";
				var _obj = {};
				_obj.owner_email = _ownerEmail;
				
				_doAjax(_url, _obj, _type);
				
			});		
			
			//	비밀번호 찾기
			$("#findPwd").on("click", function(e){
				var _id = $("#pwd_id");
				var _email = $("#pwd_email");
				
				var _ownerId = $(_id).val().trim();
				var _ownerEmail = $(_email).val().trim();
				
				if(_ownerId == ""){
					$(_id).focus();
					return alert("아이디를 입력해주세요");	
				}
				
				if(_ownerEmail == ""){
					$(_ownerEmail).focus();
					return alert("이메일을 입력해주세요");
				}
				
				var _type = 2;
				var _url = "/login/findpwd";
				var _obj = {};
				_obj.member_id = _ownerId;
				_obj.owner_email = _ownerEmail;
				
				_doAjax(_url, _obj, _type);
			});
			
		}
		
		//	ajax 공통
		function _doAjax(url, obj, type){
			
			$.ajax({
				type: "POST",
				url: url,
				data: JSON.stringify(obj),
				contentType : "application/json; charset=utf-8", 
				beforeSend: function (xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
				},
				success: function (res) {
					if(res.result == false || res.result == 'false'){
						alert(res.msg);
					}else{
						alert(res.msg);
					}
				},
				error: function (e) {
					alert("서버 처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.");
					console.log("error : " + e.toString())
				}
			});
		}
		
		return {
			init : _init
		}
	}//
	
	new _loginForm().init();
});