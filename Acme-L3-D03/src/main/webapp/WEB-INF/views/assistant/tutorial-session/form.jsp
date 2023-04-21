<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialSession.list.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorialSession.list.label.summary" path="summary"/>
	<acme:input-select code="assistant.tutorialSession.list.label.sessionType" path="sessionType" choices="${types}"/>
	<acme:input-moment code="assistant.tutorialSession.list.label.startDate" path="startDate"/>
	<acme:input-moment code="assistant.tutorialSession.list.label.endDate" path="endDate"/>	
	<acme:input-url code="assistant.tutorialSession.list.label.moreInfo" path="moreInfo"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorialSession.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorialSession.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorialSession.button.create2" action="/assistant/tutorial-session/create?tutorialId=${tutorialId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>