<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>login</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/widget/bootstrap-3.3.7/css/bootstrap.css">
</head>
<body>
<div class="row" style="width: 100%;">
    <div class="container" style="width: 30%;">
        <form class="form-signin" method="post">
            <h2 class="form-signin-heading" style="text-align: center;">Oauth2.0系统</h2>
            <label for="username" class="sr-only">Email address</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="用户名" required autofocus>
            <br/>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码" required>
            <br/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登陆</button>
        </form>

    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${pageContext.request.contextPath}/static/widget/bootstrap-3.3.7/js/bootstrap.js"></script>
</body>
</html>