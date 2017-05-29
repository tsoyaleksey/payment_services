<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="addprv_url" value="/pay/add-provider"/>
<c:url var="update_url" value="/pay/edit-providers"/>

<fmt:bundle basename="i18n">
    <fmt:message key="prvdr.btn.edit" var="editProvider"/>
    <fmt:message key="prvdr.plholder.ent.name" var="plHolderEnterName"/>
    <fmt:message key="prvdr.select.logo" var="selectLogo"/>
    <fmt:message key="prvdr.select.category" var="selectCategory"/>
    <fmt:message key="prvdr.select.region" var="selectRegion"/>
    <fmt:message key="prvdr.btn.new" var="addNew"/>
    <fmt:message key="prvdr.label" var="providerLabel"/>
    <fmt:message key="prvdr.notice" var="notice"/>
    <fmt:message key="btn.edit" var="editBtn"/>
    <fmt:message key="btn.add" var="addBtn"/>
    <fmt:message key="error.already.exist" var="errorProviderName"/>
    <fmt:message key="update.page" var="updatePage"/>
    <fmt:message key="prvdr.error.message" var="errorProviderAddNew"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin')}">
        <c:redirect url="/pay/error"/>
    </c:if>
    <div class="container">
        <div class="row">
            <div>
                <table style="table-layout: fixed;">
                    <tr>
                        <td>
                            <h1>${providerLabel}</h1>
                        </td>
                        <td>
                            <button type="submit" name="submit" class="btn btn-success" data-toggle="modal" data-target="#addNewProvider" style="margin-top: 10px">${addNew}</button>
                        </td>
                        <td>
                            <a href="${update_url}" title="${updatePage}"> <span class="glyphicon glyphicon-refresh"></span></a>
                        </td>
                        <td>
                            <c:if test="${not empty provider_error}">
                                <span class="alert alert-danger">${errorProviderAddNew}</span>
                            </c:if>
                            <c:if test="${not empty provider_already_exist}">
                                <span class="alert alert-danger">${errorProviderName}</span>
                            </c:if>
                            <c:if test="${not empty provider_edit_error}">
                                <span class="alert alert-danger">${errorProviderAddNew}</span>
                            </c:if>
                            <c:if test="${not empty provider_error}">
                                <span class="alert alert-danger" style="padding-top: 5px">${errorProviderName}</span>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="panel-group col-sm-5" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">${editProvider}</a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <table style="table-layout: fixed">
                                <c:forEach items="${providers}" var="provider">
                                    <tr>
                                        <td width="60%">
                                            <h4>${provider.name}</h4>
                                            <input hidden="hidden" name="provider_id" value="${provider.id}" style="display: none"><br>
                                        </td>
                                        <td>
                                            <a href="edit-provider?id=${provider.id}" name="submit" class="btn btn-success">${editBtn}</a><br>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="addNewProvider" tabindex="-1" role="dialog" aria-labelledby="addNewProviderLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="addNewProviderLabel">${providerLabel}</h4>
                        </div>
                        <form action="${addprv_url}" method="POST" enctype="multipart/form-data">
                            <div class="modal-body">
                                <div>
                                    <p class="alert alert-info">${notice}</p>
                                </div>
                                <h3 class="modal-title"><input type="text" min="1" max="30" name="provider_name" placeholder="${plHolderEnterName}" required pattern="[a-zA-Zа-яА-Я0-9]+$"/></h3>
                                <div class="input-group" style="margin-top: 15px">
                                    <table style="table-layout: fixed;">
                                        <tr>
                                            <td>
                                                <h3>${selectLogo}:</h3>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <input type="file" name="logotype" required size="50"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h3>${selectCategory}:</h3>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <select class="selectpicker" title="${selectCategory}" name="category_id" required>
                                                    <c:forEach items="${categories}" var="category">
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" name="submit" class="btn btn-primary">${addBtn}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</my:design>