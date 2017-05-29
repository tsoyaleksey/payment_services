<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>

<c:url var="signinurl" value="/pay/sign-in"/>
<c:url var="main_url" value="/pay/main"/>
<c:url var="top-up" value="/pay/top-up"/>
<c:url var="payments_url" value="/pay/payments"/>
<c:url var="catset_url" value="/pay/edit-categories"/>
<c:url var="regset_url" value="/pay/edit-regions"/>
<c:url var="prvdr_url" value="/pay/edit-providers"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8"/>
    <fmt:bundle basename="i18n">
        <fmt:message key="title" var="title"/>
        <fmt:message key="navbar.payments" var="payments"/>
        <fmt:message key="navbar.mobile" var="mobile"/>
        <fmt:message key="navbar.signout" var="signout"/>
        <fmt:message key="navbar.lang.en" var="enLang"/>
        <fmt:message key="navbar.lang.ru" var="ruLang"/>
        <fmt:message key="navbar.balance" var="balance"/>
        <fmt:message key="navbar.topup" var="topUpTheBalance"/>
        <fmt:message key="navbar.topup.btn" var="btnTopUp"/>
        <fmt:message key="navbar.enter.amount" var="enterAmount"/>
        <fmt:message key="navbar.amount.info" var="amountInfo"/>
        <fmt:message key="navbar.amount.error" var="amountError"/>
        <fmt:message key="cat.label" var="categorySettings"/>
        <fmt:message key="reg.label" var="regionSettings"/>
        <fmt:message key="prvdr.label" var="providerSettings"/>
        <fmt:message key="navbar.myregion" var="myRegion"/>
    </fmt:bundle>
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css"/>">
    <style>
        <jsp:directive.include file="/WEB-INF/css/style.css"/>
        <jsp:directive.include file="/WEB-INF/css/bootstrapValidator.css"/>
        <jsp:directive.include file="/WEB-INF/css/bootstrap-select.min.css"/>
    </style>
</head>
<body>
    <nav class="navbar navbar-default ">
        <div class="container">
            <c:if test="${!sessionScope.role.equals('admin') && !sessionScope.role.equals('user')}">
                <div class="navbar-header">
                    <a href="${signinurl}" class="navbar-brand">EPAY</a>
                </div>
            </c:if>
            <c:if test="${role.equals('admin') || role.equals('user')}">
                <div class="navbar-header">
                    <a href="${main_url}" class="navbar-brand">EPAY</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="${payments_url}">${payments}<span class="sr-only"></span></a></li>
                    </ul>
            </c:if>
                <div>
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${sessionScope.lang.equals('ru')}">
                            <li role="presentation" class="active"><a href="set-locale?lang=en">${enLang}</a></li>
                        </c:if>
                        <c:if test="${sessionScope.lang.equals('en')}">
                            <li role="presentation" class="active"><a href="set-locale?lang=ru">${ruLang}</a></li>
                        </c:if>
                        <c:if test="${role.equals('user') || role.equals('admin')}">
                            <li role="presentation"><a aria-selected="false" >${myRegion}</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-map-marker"></span> ${sessionScope.region}</a>
                                <ul class="dropdown-menu" style="text-align: center">
                                    <c:forEach items="${regions}" var="region">
                                        <li><a href="set-region?region=${region.id}">${region.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <li><div class="vertical_line" style="height: 50px; width: 1px; background: #DBDBDB;"></div></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> ${sessionScope.name}</a>
                                <ul class="dropdown-menu" style="text-align: center">
                                    <li><strong>${sessionScope.number}</strong></li>
                                    <li role="separator" class="divider"></li>
                                    <li><p>${balance} ${sessionScope.balance} ₸</p></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="#" data-toggle="modal" data-target="#myModal">${topUpTheBalance}</a></li>
                                    <c:if test="${role.equals('admin')}">
                                        <li><a href="${catset_url}">${categorySettings}</a></li>
                                        <li><a href="${regset_url}">${regionSettings}</a></li>
                                        <li><a href="${prvdr_url}">${providerSettings}</a></li>
                                    </c:if>
                                    <li role="separator" class="divider"></li>
                                    <li>
                                        <form action="sign-out" method="post">
                                            <button type="submit" class="btn btn-default btn-sm outline btn-group-justified" id="sign-out" name="submit"><span class="glyphicon glyphicon-log-out"></span> ${signout}</button>
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
        <jsp:invoke fragment="header"/>
    </nav>


    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">${topUpTheBalance}</h4>
                </div>
                <form action="top-up" method="post">
                    <div class="modal-body">
                        <h5 class="modal-title">${amountInfo}</h5>
                        <div class="input-group" style="margin-top: 15px">
                            <span class="input-group-addon"><i class="glyphicon" style="font-size: large">₸</i></span>
                            <input type="number" min="1" max="150000" class="form-control" id="balance_field" name="balance_field" placeholder="${enterAmount}" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">${btnTopUp}</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<div id="body" style="height: auto">
    <c:if test="${not empty topup_error}">
        <div class="right" style="text-align: right">
            <span class="alert alert-danger">${amountError}</span>
        </div>
    </c:if>
    <jsp:doBody/>
</div>
    <script src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"/>"></script>
    <c:if test="${sessionScope.lang.equals('en')}">
        <script src="<c:url value="/webjars/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.en-GB.min.js"/>"></script>
    </c:if>
    <c:if test="${sessionScope.lang.equals('ru')}">
        <script src="<c:url value="/webjars/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.ru.min.js"/>"></script>
    </c:if>
    <script charset="UTF-8">
        <jsp:directive.include file="/WEB-INF/js/phone.js"/>
        <jsp:directive.include file="/WEB-INF/js/datepicker.js"/>
        <jsp:directive.include file="/WEB-INF/js/bootstrapValidator.min.js"/>
        <jsp:directive.include file="/WEB-INF/js/ajax.js"/>
        <jsp:directive.include file="/WEB-INF/js/bootstrap-select.min.js"/>
        <c:if test="${sessionScope.lang.equals('en')}">
        <jsp:directive.include file="/WEB-INF/js/formValidator.js"/>
        </c:if>
        <c:if test="${sessionScope.lang.equals('ru')}">
        <jsp:directive.include file="/WEB-INF/js/formValidator_ru.js"/>
        </c:if>
    </script>
<div>
<footer class="footer text-center">
    <div class="container">
        <div class="copyrights">
            <p>EPAM SYSTEMS. Lab 21 © 2017
            <br>
            <span>made by Aleksey Tsoy</span>
            </p>
        </div>
    </div>
    <jsp:invoke fragment="footer"/>
</footer>
</div>
</body>
</html>