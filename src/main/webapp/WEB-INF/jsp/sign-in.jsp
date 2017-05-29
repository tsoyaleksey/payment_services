<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="signup_url" value="/pay/sign-up"/>
<c:url var="signin_url" value="/pay/sign-in"/>
<c:url var="main_url" value="/pay/main"/>
<c:url var="passwordrecoveryurl" value="/pay/passwordrecovery"/>

<fmt:bundle basename="i18n">
    <fmt:message key="signin.phone" var="phone"/>
    <fmt:message key="signup.name" var="name"/>
    <fmt:message key="signup.dateofbirth" var="dateofbirth"/>
    <fmt:message key="signin.password" var="password"/>
    <fmt:message key="signup.confirmpassword" var="confirmpass"/>
    <fmt:message key="signin.forgot.password" var="forgotpassword"/>
    <fmt:message key="signin.btn" var="signinbtn"/>
    <fmt:message key="signup.btn" var="recoveryButton"/>
    <fmt:message key="signup.text" var="signuptxt"/>
    <fmt:message key="signin.text" var="signintxt"/>
    <fmt:message key="signin.err" var="signinError"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${sessionScope.role.equals('admin') || sessionScope.role.equals('user')}">
        <c:redirect url="/pay/main"/>
    </c:if>
        <div class="row">
            <div class="col-sm-4" style="float: none; margin: 0 auto; background-color: #efefef">
            <ul class="nav nav-pills col-sm-offset-3" role="tablist" style="padding-top: 10px">
                <li role="presentation" class="active"><a href="#signin" aria-controls="home" role="tab" data-toggle="tab">${signintxt}</a></li>
                <li role="presentation"><a href="${signup_url}">${signuptxt}</a></li>
            </ul>
            <div class="tab-content" style="float: none; margin: 0 auto; padding-bottom: 10px">
                <div role="tabpanel" class="tab-pane active" id="signin">
                    <form action="${signin_url}" method="POST" id="reg_form" name="form">
                        <div class="form-group has-feedback">
                            <div class="inputGroupContainer">
                                <c:if test="${not empty user_error}">
                                    <span class="alert alert-danger" style="padding-top: 5px">${signinError}</span>
                                </c:if>
                                <div class="input-group" style="margin-top: 30px">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                    <input id="phone" type="tel" name="phone_number" class="form-control" placeholder="${phone}" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group has-feedback">
                            <div class="inputGroupContainer">
                                <div class="input-group" style="margin-top: 15px">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="pass" type="password" class="form-control" name="password" placeholder="${password}">
                                </div>
                            </div>
                        </div>
                        <div class="input-group" style="margin-top: 20px">
                            <button type="submit" name="submit" class="btn btn-success" id="sign-in" style="margin-right: 50px">${signinbtn}</button>
                            <form action="${passwordrecoveryurl}" method="get">
                                <a href="${passwordrecoveryurl}">${forgotpassword}</a>
                            </form>
                        </div>
                    </form>
                </div>
            </div>
            </div>
        </div>
</my:design>
