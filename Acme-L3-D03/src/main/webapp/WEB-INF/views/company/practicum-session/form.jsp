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
	<jstl:if test="${addendum == true}">
		<h2 style="text-align: center; color: red;">
			<acme:message code="company.practicum-session.form.message.addendum"/>
		</h2>
	</jstl:if>
	
	<acme:input-textbox code="company.practicum-session.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum-session.form.label.summary" path="summary"/>
	<acme:input-moment code="company.practicum-session.form.label.startDate" path="startDate"/>
	<acme:input-moment code="company.practicum-session.form.label.endDate" path="endDate"/>
	<acme:input-textbox code="company.practicum-session.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:input-select code="company.practicum-session.form.label.practicum" path="practicum" choices="${practica}"/>
			<acme:submit code="company.practicum-session.form.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.practicum-session.form.button.delete" action="/company/practicum-session/delete"/>
			<acme:submit code="company.practicum-session.form.button.publish" action="/company/practicum-session/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == false}">
			<acme:input-textbox code="company.practicum-session.form.label.practicum" path="practicum.code"/>			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="company.practicum-session.form.label.practicum" path="practicum" choices="${practica}"/>
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum'}">
			<acme:input-select code="company.practicum-session.form.label.practicum" path="practicum" choices="${practica}"/>
			<acme:input-checkbox code="company.practicum-session.form.label.confirmation" path="confirmation"/>
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create-addendum"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>