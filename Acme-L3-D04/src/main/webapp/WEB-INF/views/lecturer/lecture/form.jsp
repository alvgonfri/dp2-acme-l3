<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.form.label.summary" path="summary"/>
		<acme:input-textbox code="lecturer.lecture.form.label.learningTime" path="learningTime"/>
		<acme:input-textbox code="lecturer.lecture.form.label.body" path="body"/>
		<acme:input-select code="lecturer.lecture.form.label.lectureType" path="lectureType" choices="${lectureTypes}"/>
		<acme:input-url code="lecturer.lecture.form.label.moreInfo" path="moreInfo"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
			<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
			<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>