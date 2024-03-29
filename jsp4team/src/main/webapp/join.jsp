<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/index.css">
<link rel="stylesheet" href="./css/join.css">
<link rel="stylesheet" href="./css/category.css">
<!-- <link rel="stylesheet" href="./css/menu.css"> -->
<script type="text/javascript" src="./js/join.js"></script>
<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
	crossorigin="anonymous"></script>
<!-- Jquery -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript">

</script>
<script type="text/javascript">
	//글로벌 변수
	let idCheckBool = false;
	
	$(function (){ //제이쿼리 시작
		$('.id-alert, .name-alert, .pw-alert').hide();
		
		//검색 버튼
		$('.searchBtn').click(function (){
			let searchItem = $('.searchText').val();
			window.location.href="./search?keyword=" + searchItem;
		});
		
		//onchange()
		//$("#id").change(function (){
		//	alert("아이디입력창 값이 변경되었습니다.");
		//});
		$('#id').on("change keyup paste", function(){
			//alert("아이디입력창 값이 변경되었습니다.");
			$('.id-alert').show();
			$('.id-alert').html('<p class="alert">당신이 입력한 ID는 ' + $('#id').val() +  '</p>');
			if($('#id').val().length > 5){
				idCheck();
			}
		});
	});

	function check() {
		// $(선택자).할일();
		let id = $("#id").val();
		if(id.length < 3 || id == ""){
			$('.id-alert').show();
			$("#id").focus();
			return false;
		} else {
			$('.id-alert').hide();
		}
		if(!idCheckBool){
			alert("ID 검사를 먼저 실행해주세요.");
			return false;
		}
		
		let name = $('#name').val();
		if(name.length < 3){
			$('.name-alert').show();
			$('#name').focus();
			return false;
		}
		$('.name-alert').hide();
		
		let pw1 = $('#pw1').val();
		let pw2 = $('#pw2').val();
		if(pw1.length < 8){
			$('.pw-alert').show();
			$('#pw1').focus();
			return false;
		}
		if(pw2.length < 8){
			alert("암호는 8글자 이상이어야 합니다.");
			$('#pw2').focus();
			return false;
		}
		if(pw1 != pw2){
			alert("암호가 일치하지 않습니다.");
			$('#pw2').val("");
			$('#pw2').focus();
			return false;
		}
		$('.pw-alert').hide();
	}
	function idCheck(){
		let id = $('#id').val();
		const regExp = /^[a-z0-9]{5,15}$/;
		if(id.length < 5 || !regExp.test(id)) {
		$('.id-alert').html('<p class "alert">아이디는 영문자 5글자 이상이고 특수문자가 없어야합니다.</p>')
		$('#id').focus();
		} else {
				$.ajax({
				url : './idCheck',			//이동할 주소
				type : 'post',				//post / get
				dataType : 'text',			//수신 타입
				data : {'id' : id},			//보낼 값
				success : function(result){	//성공시
					//alert("통신에 성공했습니다");
					if(result == 1){
						//alert("이미 가입되어있습니다.");
						$('.id-alert').append('<p class="alert">이미 가입되어있습니다.</p>');
						idCheckBool = false;
						$("#joinBtn").attr("disabled", "disabled");//비활성화 시키기
						$('#id').focus();
					} else {
						//alert("가입할 수 있습니다. 다음을 계속 진행하세요.");
						$('.id-alert').append('<p class="alert">가입할 수 있습니다.</p>');
						$('.id-alert .alert').css("color","green");
						idCheckBool = true;
						$("#joinBtn").removeAttr("disabled");//비활성화 제거하기 = 활성화 시키기
						//$('#name').focus();
					}
				},
				error : function(request, status, error){//접속불가, 문제발생 등
					alert("문제가 발생했습니다");
				}
			});
		}
		return false;
	}
</script>


</head>
<body> 
		<header>
			<div class="top1">
				<img class="logoImg" alt="logo" src="./img/logo.png" onclick="location.href='./'" width="300px;">
				<div class="search">
					<div class="input-group mb-3 searchInputGroup">
					  <input type="text" class="form-control searchText" placeholder="검색어를 입력하세요" aria-label="Recipient's username" aria-describedby="button-addon2">
					  <button class="btn btn-outline-secondary btn-jelly searchBtn" type="button" id="button-addon2">검색</button>
					</div>
				</div>
				<div class="joinLogin"> <c:choose> <c:when test="${sessionScope.mname eq null }">
					<button class="btn btn-jelly" onclick ="window.location.href='./join'">회원가입</button>
					<button class="btn btn-jelly" onclick ="window.location.href='./login'">로그인</button> </c:when> <c:otherwise>
					<button class="btn btn-jelly" onclick ="window.location.href='./basket'">장바구니</button>
					<button class="btn btn-jelly" onclick ="window.location.href='./logout'">로그아웃</button> </c:otherwise> </c:choose>
				</div>
			</div>
			<div class="bhItem">
			<nav class="nav nav-pills bhNav">
			  <a class="nav-link menuItem active" aria-current="page" href="./newBooks">신간</a>
			  <a class="nav-link menuItem" href="./domestic">국내</a>
			  <a class="nav-link menuItem" href="./foreign">해외</a> <c:choose> <c:when test="${sessionScope.mname eq null }">
			  <a class="nav-link menuItem" href="./login">로그인</a> </c:when> <c:otherwise>
			  <a class="nav-link menuItem" href="./basket">장바구니</a>
			  <a class="nav-link menuItem" href="./logout">로그아웃</a> </c:otherwise> </c:choose>
			</nav>
		</div>
			<%-- <div class="top2">
				<div class="menu">
				<%@ include file="menu.jsp"%>
				</div>
			</div> --%>
		</header>
	<div class="container2">
	<!-- <h1>회원가입 페이지</h1> -->
		<div class="main">
			<div class="mainStyle">
				<article>

					<div class="join-form">
						<div class="mx-5 p-5 bg-warning rounded">
							<h1>회원가입</h1>
							<form action="./join" method="post" onsubmit="return check()">
								<div class="input-group mb-2">
									<label class="input-group-text">아이디</label>
									<input type="text"  id="id" name="id" class="form-control" placeholder="아이디를 입력하세요">
									<button class="btn btn-success input-group-text" onclick="return idCheck()">ID검사</button>
								</div>
								<div class="input-group mb-2 id-alert">
									<p class="alert">올바른 아이디를 입력하세요</p>
								</div>
								<div class="input-group mb-2">
									<label class="input-group-text">이&ensp;&ensp;름</label> <input
										type="text" id="name" name="name" class="form-control"
										placeholder="이름을 입력하세요">
								</div>
								<div class="input-group mb-2 name-alert">
									<p class="alert">올바른 이름을 입력하세요</p>
								</div>
								<div class="input-group mb-2">
									<label class="input-group-text">암&ensp;&ensp;호</label> <input
										type="password" id="pw1" name="pw1" class="form-control"
										placeholder="암호를 입력하세요"> <label
										class="input-group-text">재입력</label> <input type="password"
										id="pw2" name="pw2" class="form-control"
										placeholder="한번 더 입력하세요">
								</div>
								<div class="input-group mb-2 pw-alert">
									<p class="alert">올바른 암호를 입력하세요</p>
								</div>
								<div class="input-group mb-2">
									<button type="reset" class="btn btn-danger col-6">초기화</button>
									<button id="joinBtn" type="submit" disabled="disabled" class="btn btn-primary col-6">가입하기</button>
								</div>
							</form>
						</div>
					</div>
				</article>
			</div>
		</div>
	</div>
	
	
</body>
</html>