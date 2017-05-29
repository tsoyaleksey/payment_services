<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="signup_url" value="/pay/sign-up"/>
<c:url var="signin_url" value="/pay/sign-in"/>

<fmt:bundle basename="i18n">
    <fmt:message key="signin.phone" var="phone"/>
    <fmt:message key="signup.name" var="name"/>
    <fmt:message key="signup.dateofbirth" var="dateofbirth"/>
    <fmt:message key="signin.password" var="password"/>
    <fmt:message key="signup.confirmpassword" var="confirmpass"/>
    <fmt:message key="signup.btn" var="recoveryButton"/>
    <fmt:message key="signup.text" var="signuptxt"/>
    <fmt:message key="signin.text" var="signintxt"/>
    <fmt:message key="signup.err.number" var="numberIsBusy"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${sessionScope.role.equals('admin') || sessionScope.role.equals('user')}">
        <c:redirect url="/pay/main"/>
    </c:if>
    <div class="row">
        <div class="col-sm-4" style="float: none; margin: 0 auto; background-color: #efefef">
            <ul class="nav nav-pills col-sm-offset-3" role="tablist" style="padding-top: 10px">
                <li role="presentation"><a href="${signin_url}">${signintxt}</a></li>
                <li role="presentation" class="active"><a href="#signup" aria-controls="profile" role="tab" data-toggle="tab">${signuptxt}</a></li>
            </ul>
        <div class="tab-content" style="float: none; margin: 0 auto; padding-bottom: 10px">
            <div role="tabpanel" class="tab-pane active" id="signup">
                <form action="${signup_url}" method="post" id="reg_form" name="form">
                    <div class="form-group has-feedback">
                        <div class="inputGroupContainer">

                            <div class="input-group" style="margin-top: 30px">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                <input id="phone_number" type="text" name="phone_number" class="form-control" placeholder="${phone}" required>
                                <span class="input-group-addon" id="phone_status"></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="inputGroupContainer">
                            <div class="input-group" style="margin-top: 15px">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input type="text" maxlength="25" class="form-control" id="name" name="name" placeholder="${name}" required >
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="inputGroupContainer">
                            <div class="input-group" style="margin-top: 15px">
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                <input type="text" id="date_of_birth" name="date_of_birth" class="form-control" placeholder="${dateofbirth}" required>
                            </div>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <div class="inputGroupContainer">
                            <div class="input-group" style="margin-top: 15px">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="password" type="password" data-error="some error" class="form-control" name="password" placeholder="${password}" minlength="8" maxlength="24" required>
                            </div>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <div class="inputGroupContainer">
                            <div class="input-group" style="margin-top: 15px">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="confirm_password" type="password" class="form-control {$borderColor}" name="confirm_password" placeholder="${confirmpass}" data-match="#confirm_password" data-match-error="some error 2" minlength="8" maxlength="24"  required>
                            </div>
                        </div>
                    </div>
                    <div class="input-group" style="margin-top: 20px">
                        <button type="submit" name="submit" class="btn btn-success">${recoveryButton}</button>
                    </div>
                    <c:if test="${not empty phone_number_error}">
                        <div class="alert alert-danger">
                            <button type="button" class="close" data-dismiss="alert">X</button>
                            <strong>${numberIsBusy}</strong>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
        </div>
    </div>
</my:design>
