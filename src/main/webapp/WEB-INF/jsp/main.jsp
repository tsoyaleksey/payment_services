<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="slider.text1" var="slide1"/>
    <fmt:message key="slider.text2" var="slide2"/>
    <fmt:message key="slider.text3" var="slide3"/>
</fmt:bundle>

<my:design role="${sessionScope.role}">
    <c:if test="${!sessionScope.role.equals('admin') && !sessionScope.role.equals('user')}">
        <c:redirect url="/pay/sign-in"/>
    </c:if>

    <div class="container">
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="../../images/slide1.jpeg" alt="">
                            <div class="carousel-caption">
                                <h1 style="color: #2f3640">${slide1}</h1>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../../images/slide2.jpg" alt="">
                            <div class="carousel-caption">
                                <h1 style="color: #2f3640">${slide2}</h1>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../../images/slide3.jpg" alt="">
                            <div class="carousel-caption">
                                <h1 style="color: #2f3640">${slide3}</h1>
                            </div>
                        </div>
                    </div>
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</my:design>