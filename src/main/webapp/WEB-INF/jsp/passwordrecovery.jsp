<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="passwordrec" value="/pay/passwordrecovery"/>

<fmt:bundle basename="i18n">
    <fmt:message key="signup.dateofbirth" var="dateofbirth"/>
    <fmt:message key="signin.password" var="password"/>
    <fmt:message key="signup.confirmpassword" var="confirmpass"/>
    <fmt:message key="password.recovery.btn" var="recoveryButton"/>
    <fmt:message key="password.recovery" var="passwordRecovery"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${sessionScope.role.equals('admin') || sessionScope.role.equals('user')}">
        <c:redirect url="/pay/main"/>
    </c:if>
    <div class="row">
        <div class="col-sm-3" style="float: none; margin: 0 auto; background-color: #efefef">
            <div class="col-sm-offset-2" style="padding-top: 20px">
                <h4><span class="page-information">${passwordRecovery}</span></h4>
            </div>
            <div style="float: none; margin: 0 auto; padding-bottom: 10px">
                <div>
                    <form action="${passwordrec}" method="POST" id="reg_form" name="form">
                        <div class="form-group has-feedback">
                            <div class="inputGroupContainer">
                                <div class="input-group" style="margin-top: 30px">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                    <input id="phone" type="text" name="phone_number" class="form-control" required>
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
                                    <input id="confirm_password" type="password" class="form-control {$borderColor}" name="confirm_password" placeholder="${confirmpass}" required>
                                </div>
                            </div>
                        </div>
                        <div class="input-group" style="margin-top: 20px">
                            <button type="submit" name="submit" class="btn btn-success">${recoveryButton}</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</my:design>
