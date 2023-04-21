<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.course.form.label.code" path="code" readonly="true"/>	
	<acme:input-textbox code="authenticated.course.form.label.title" path="title" readonly="true"/>	
	<acme:input-textbox code="authenticated.course.form.label.summary" path="summary" readonly="true"/>	
	<acme:input-textbox code="authenticated.course.form.label.retailPrice" path="retailPrice" readonly="true"/>	
	<acme:input-textbox code="authenticated.course.form.label.moreInfo" path="moreInfo" readonly="true"/>	
	<acme:input-textbox code="authenticated.course.form.label.lecturer" path="lecturerFullName" readonly="true"/>
	
	<acme:button code="authenticated.course.form.button.audit" action="/authenticated/audit/list?courseId=${id}"/>
	<acme:button code="authenticated.tutorial.form.button" action="/authenticated/tutorial/list?courseId=${id}"/>
	<acme:button code="authenticated.practica.form.button" action="/authenticated/practicum/list?courseId=${id}"/>

</acme:form>