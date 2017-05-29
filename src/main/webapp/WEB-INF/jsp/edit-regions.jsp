<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="editreg_url" value="/pay/edit-region"/>
<c:url var="addreg_url" value="/pay/add-region"/>
<c:url var="update_url" value="/pay/edit-regions"/>

<fmt:bundle basename="i18n">
    <fmt:message key="reg.btn.new" var="newRegion"/>
    <fmt:message key="reg.label" var="regionLabel"/>
    <fmt:message key="reg.btn.edit" var="editRegion"/>
    <fmt:message key="btn.edit" var="editBtn"/>
    <fmt:message key="btn.add" var="addBtn"/>
    <fmt:message key="error.already.exist" var="errorRegion"/>
    <fmt:message key="update.page" var="updatePage"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin')}">
        <c:redirect url="/pay/error"/>
    </c:if>
    <div class="container">
        <div class="row">
            <div>
                <table>
                    <tr>
                        <td>
                            <h1>${regionLabel}</h1>
                        </td>
                        <td>
                            <a href="${update_url}" title="${updatePage}"> <span class="glyphicon glyphicon-refresh"></span></a>
                        </td>
                    </tr>
                </table>
            </div>
            <c:if test="${not empty region_error}">
                <span class="alert alert-danger" style="padding-top: 5px">${errorRegion}</span>
            </c:if>
            <div class="panel-group col-sm-5" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            ${newRegion}
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul style="list-style-type: none">
                                <form action="${addreg_url}" method="POST">
                                    <li>
                                        <h4><input type="text" min="1" max="30" name="region_name" required pattern="[a-zA-Zа-яА-Я]+$"/>
                                            <button type="submit" name="submit" class="btn btn-success">${addBtn}</button>
                                        </h4>
                                    </li>
                                </form>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            ${editRegion}
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <ul style="list-style-type: none">
                                <c:forEach items="${regions}" var="region">
                                    <li>
                                        <form action="${editreg_url}" method="POST" accept-charset="UTF-8">
                                            <h4><input hidden="hidden" name="region_id" value="${region.id}" style="display: none">
                                                <input type="text" min="1" max="30" maxlength="30" name="region_name" value="${region.name}" required pattern="[a-zA-Zа-яА-Я]+$"/>
                                                <button type="submit" name="submit" class="btn btn-success" id="edit-categories">${editBtn}</button>
                                            </h4>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</my:design>