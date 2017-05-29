<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="number" var="number"/>
    <fmt:message key="amount.paid" var="amount"/>
    <fmt:message key="recipient" var="recipient"/>
    <fmt:message key="date" var="paymentDate"/>
    <fmt:message key="payment.history" var="history"/>
    <fmt:message key="no.operation" var="noOperation"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin') && !sessionScope.role.equals('user')}">
        <c:redirect url="/pay/sign-in"/>
    </c:if>
    <my:paypage-design/>
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="page-header" style="text-align: right">
                    <h1>${history}</h1>
                </div>
                <table class="table table-hover">
                    <tr style="text-align: right;" class="active">
                        <td width="200px">
                            <h4>${recipient}</h4>
                        </td>
                        <td width="200px">
                            <h4>${number}</h4>
                        </td>
                        <td width="200px">
                            <h4>${amount}</h4>
                        </td>
                        <td width="200px">
                            <h4>${paymentDate}</h4>
                        </td>
                    </tr>
                    <c:forEach items="${payments_records}" var="record">
                        <tr style="text-align: right">
                            <td width="200px">
                                ${record.provider.name}
                            </td>
                            <td width="200px">
                                ${record.number}
                            </td>
                            <td width="200px">
                                ${record.sum} ₸
                            </td>
                            <td width="200px">
                                ${record.dateTime}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${no_data != null}">
                    <div class="page-header" style="text-align: center">
                        <h3>${noOperation}</h3>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</my:design>
