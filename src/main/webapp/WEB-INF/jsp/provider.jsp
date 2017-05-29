<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="pay_url" value="/pay/payment-transaction"/>
<c:url var="main_url" value="/pay/main"/>

<fmt:bundle basename="i18n">
    <fmt:message key="success.payment" var="successPayment"/>
    <fmt:message key="prvdr.service" var="providerService"/>
    <fmt:message key="prvdr.phone.number" var="phoneNumber"/>
    <fmt:message key="prvdr.number" var="simpleNumber"/>
    <fmt:message key="prvdr.sum" var="providerSum"/>
    <fmt:message key="prvdr.pay" var="pay"/>
    <fmt:message key="fail.payment" var="error"/>
    <fmt:message key="number" var="checkNumber"/>
    <fmt:message key="recipient" var="recipient"/>
    <fmt:message key="date" var="PaymentDate"/>
    <fmt:message key="amount.paid" var="amount"/>
    <fmt:message key="payer.name" var="payerName"/>
    <fmt:message key="to.main.btn" var="toMain"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin') && !sessionScope.role.equals('user')}">
        <c:redirect url="/pay/sign-in"/>
    </c:if>
    <c:if test="${balance_error == null && success == null}">
    <my:paypage-design/>
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3>${provider.name}</h3></div>
                        <div class="panel-body">
                            <form action="${pay_url}" method="post">
                                <table>
                                    <tr>
                                        <td width="50%">
                                            <h4>${providerService}</h4>
                                        </td>
                                        <td>
                                            <img src="logotype?logo=${provider.id}" id="logoimg" width="130" height="60"/>
                                            <input hidden="hidden" name="provider_id" value="${provider.id}" style="display: none">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <br />
                                        </td>
                                    </tr>
                                        <c:if test="${provider.category.id == 1}">
                                            <tr>
                                                <td width="50%">
                                                    <h4>${phoneNumber}</h4>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="phone" type="text" name="phone_number" class="form-control" pattern="^\+7(\s+)?\(?([0-9]{3})\)?(\s+)?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$" required>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${provider.category.id != 1}">
                                            <tr>
                                                <td width="50%">
                                                    <h4>${simpleNumber}</h4>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" min="1" max="20" name="number" class="form-control" required>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                    <tr>
                                        <td>
                                            <br />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="50%">
                                            <h4>${providerSum}</h4>
                                        </td>
                                        <td>
                                            <div class="input-group">
                                                <input type="number" min="1" max="100000" class="form-control" id="sum" name="sum" placeholder="0" required>
                                                <span class="input-group-addon"><i class="glyphicon" style="font-size: large">₸</i></span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <br />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="50%">
                                        </td>
                                        <td>
                                            <button type="submit" name="submit" class="btn btn-success">${pay}</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${balance_error != null}">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2 class="alert alert-danger">${error}</h2>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${success != null}">
        <div class="container">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-6">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3><span class="glyphicon glyphicon-ok-circle"></span>${successPayment}</h3>
                            <h4><a href="${main_url}">${toMain}</a></h4>
                        </div>
                        <div class="panel-body">
                            <table>
                                <tr>
                                    <td>
                                        <h3>${payerName} ${sessionScope.name}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h3>${PaymentDate} ${payment_date}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h3>${recipient} ${provider_name}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h3>${checkNumber} ${number}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <hr />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h2>${amount} ${sum} ₸</h2>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</my:design>