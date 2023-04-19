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
	<acme:input-textbox code="auditor.auditingRecord.form.label.audit" path="audit.code" readonly="true"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditingRecord.form.label.startDate" path="startDate"/>
	<acme:input-moment code="auditor.auditingRecord.form.label.endDate" path="endDate"/>
	<acme:input-select code="auditor.auditingRecord.form.label.mark" path="mark" choices="${mark}"/>
	<acme:input-url code="auditor.auditingRecord.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.subject" path="subject"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditingRecord.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditingRecord.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-correction'}">
			<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create-correction?auditId=${auditId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>