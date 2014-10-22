<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="assets/js/BigInt.js"></script>
<script type="text/javascript" src="assets/js/Barrett.js"></script>
<script type="text/javascript" src="assets/js/RSA.js"></script>
<script type="text/javascript" src="assets/js/jquery-1.11.0.min.js"></script>
<title>Login</title>
<%
	String module = (String)request.getAttribute("module");
   String empoent = (String)request.getAttribute("empoent");
%>
<script type="text/javascript">
	var pwd;
	$(function(){
		$("#loginbtn").click(function(){
			
			var module = "<%=module%>";
			var empoent = "<%=empoent%>";
			setMaxDigits(130);
			var key = new RSAKeyPair(empoent,"",module); 
			pwd = $("#pwd").val();
			var result = encryptedString(key, encodeURIComponent(pwd));
			
			if(pwd==null||pwd==""){
				alert("请填写密码");
				return false;
			}
			$("#pwd").val(result);
			var str ="原始："+pwd+",加密后："+result;
			alert(str);
			$("#resultinfo").html("原始："+pwd+",加密后："+result);
			return true;
		});
	})

</script>
</head>
<body>
	<form id="loginform"  action="login" method="POST">
		<fieldset>
			<legend>登陆</legend>
			密码：<input type="password" name="pwd" id="pwd" /> 
			<button id="loginbtn">登陆</button>
			<div id="resultinfo"></div>
		</fieldset>
	</form>
</body>
</html>