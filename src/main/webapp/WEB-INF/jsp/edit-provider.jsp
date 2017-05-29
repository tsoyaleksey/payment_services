<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:url var="editprv_url" value="/pay/edit-provider"/>

<fmt:bundle basename="i18n">
    <fmt:message key="prvdr.btn.edit" var="editProvider"/>
    <fmt:message key="prvdr.plholder.ent.name" var="plHolderEnterName"/>
    <fmt:message key="prvdr.select.logo" var="selectLogo"/>
    <fmt:message key="prvdr.select.category" var="selectCategory"/>
    <fmt:message key="prvdr.select.region" var="selectRegion"/>
    <fmt:message key="prvdr.label" var="providerLabel"/>
    <fmt:message key="btn.edit" var="editBtn"/>
    <fmt:message key="error.already.exist" var="errorProviderName"/>
    <fmt:message key="prvdr.current.region" var="currentRegions"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin')}">
        <c:redirect url="/pay/error"/>
    </c:if>
    <div class="container">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <form action="${editprv_url}" method="post" enctype="multipart/form-data">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3>${editProvider}</h3></div>
                        <div class="panel-body">
                            <h3><input type="text" min="1" max="30" name="provider_name" value="${provider.name}" placeholder="${plHolderEnterName}" required pattern="[a-zA-Zа-яА-Я0-9]+$"/></h3>
                            <input hidden="hidden" name="provider_id" value="${provider.id}" style="display: none">
                            <div class="input-group" style="margin-top: 15px">
                                <table style="table-layout: fixed;">
                                    <tr>
                                        <td>
                                            <h3>${selectLogo}:</h3>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="logotype?logo=${provider.id}" width="130" height="60" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="file" name="new_logotype"/>
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
                                                    <c:if test="${provider.category.id != category.id}">
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:if>
                                                    <c:if test="${provider.category.id == category.id}">
                                                        <option value="${category.id}" selected>${category.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <h3>${selectRegion}:</h3>
                                        </td>
                                        <td>
                                            <h3>${currentRegions}</h3>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select class="selectpicker" title="${selectRegion}" multiple data-width="300px" name="region_id">
                                                <c:forEach items="${regions}" var="region">
                                                    <option value="${region.id}">${region.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="selectpicker" title="${currentRegions}">
                                                <c:forEach items="${providers_regions}" var="providerRegion">
                                                    <option value="${providerRegion.id}" selected disabled>${providerRegion.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div>
                                <hr />
                                <button type="submit" class="btn btn-primary">${editBtn}</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</my:design>