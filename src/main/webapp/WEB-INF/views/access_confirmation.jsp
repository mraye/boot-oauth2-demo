<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/widget/bootstrap-3.3.7/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
    <style type="text/css">
        body {
            padding-top: 50px;
        }

        .starter-template {
            padding: 40px 15px;
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        $(function () {
           $("#accept").click(function () {
              $("#confirmationForm").submit();
           });
            $("#deny").click(function () {
                $("#user_oauth_approval").val("false");
                $("#confirmationForm").submit();
            });
        });
    </script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Oauth2.0</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <p class="lead">
        <h1>Oauth Approval</h1>
        <%
            if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null
                    && !(session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) instanceof UnapprovedClientAuthenticationException)) {
        %>
        <div class="error">
            <h2>Woops!</h2>
            <p>
                Access could not be granted. (<%=((AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
                    .getMessage()%>)
            </p>
        </div>
        <%
            }
        %>
        <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

        <security:authorize access="hasRole('ROLE_ADMIN')">
            <form id="confirmationForm" name="confirmationForm"
                  action="${pageContext.request.contextPath}/oauth/authorize" method="post">
                <input id="user_oauth_approval" name="user_oauth_approval" value="true" type="hidden"/>
                <div>
                    <h4>Dear ${authorizationRequest.clientId}</h4>
                    <h3>我们将获取您的头像以及昵称</h3>
                </div>
                <br/>
                <input type="button" class="btn btn-success btn-lg btn-block" id="accept" name="accept" value="同意">
                <input type="button" class="btn btn-danger btn-lg btn-block" id="deny" name="deny" value="拒绝">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </form>
        </security:authorize>
        </p>
    </div>

</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${pageContext.request.contextPath}/static/widget/bootstrap-3.3.7/js/bootstrap.js"></script>
</body>
</html>