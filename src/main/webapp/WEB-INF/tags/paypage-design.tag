<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div class="container">
    <div class="row">
        <nav class="navbar yamm navbar-default" role="navigation">
            <ul class="nav navbar-nav">
                <c:forEach items="${categories}" var="category">
                    <li class="dropdown yamm-fw">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${category.name}</a>
                        <ul class="dropdown-menu">
                            <li class="grid-demo">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <table>
                                            <tr>
                                                <c:forEach items="${providers}" var="provider">
                                                    <c:if test="${provider.category.id == category.id}">
                                                        <td style="padding-right: 10px;">
                                                            <a href="provider?id=${provider.id}" id="logo"><img src="logotype?logo=${provider.id}" id="logoimg" width="130" height="60"/></a>
                                                        </td>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>