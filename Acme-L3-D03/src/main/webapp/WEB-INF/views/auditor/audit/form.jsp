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
	<acme:input-textbox code="authenticated.audits.list.label.code" path="code"/>
	<acme:input-textbox code="authenticated.audits.list.label.conclusion" path="conclusion"/>
	<acme:input-textbox code="authenticated.audits.list.label.strong-points" path="strongPoints"/>
	<acme:input-textbox code="authenticated.audits.list.label.weak-points" path="weakPoints"/>
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courses}"/>	
	
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="employer.job.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>