<#assign base=rc.contextPath />
<#assign baseSrc="http://localhost:8082" />
<html>
<head>
    <title>登录</title>
    <style>.error{color:red;}</style>
</head>
<body>

<div>localhost->port:${localPort!""}</div>
<div class="error">${error!""}</div>
<form action="" method="post">
	用户名：<input type="text" name="username"><br/>
	密码：<input type="password" name="password"><br/>
	验证码：<input type="text" name="jcaptchaCode">
	<img class="jcaptcha-btn jcaptcha-img" src="${base}/jcaptcha" title="点击更换验证码">
	<a class="jcaptcha-btn" href="javascript:void(0);">换一张</a>
	<br/>
	<input type="submit" value="登录">
	记住我：<input type="checkbox" name="rememberMe" value="true">
</form>
<script src="${baseSrc}/static/js/jquery-3.3.1.min.js"></script>
<script>
    $(function() {
        $(".jcaptcha-btn").click(function() {
            $(".jcaptcha-img").attr("src", '${base}/jcaptcha?'+new Date().getTime());
        });
    });
</script>
</body>
</html>