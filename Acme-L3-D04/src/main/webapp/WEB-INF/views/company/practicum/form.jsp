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
	<acme:input-textbox code="company.practicum.form.label.code" path="code"/>
	<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="company.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.summary" path="summary"/>
	<acme:input-textarea code="company.practicum.form.label.goals" path="goals"/>
	<acme:input-textbox code="company.practicum.form.label.estimatedTime" path="estimatedTime" readonly = "true"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
			<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
			<acme:button code="company.practicum.form.button.session.list" action="/company/practicum-session/list?practicumId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="company.practicum.form.button.session.list" action="/company/practicum-session/list?practicumId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>

