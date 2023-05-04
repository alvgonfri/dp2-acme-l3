
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="assistant.tutorial.list.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.list.label.title" path="title"/>	
	<acme:input-textbox code="assistant.tutorial.list.label.summary" path="summary"/>
	<acme:input-textbox code="assistant.tutorial.list.label.goals" path="goals"/>
	<acme:input-select code="assistant.tutorial.list.label.course" path="course" choices="${courses}"/>
	<acme:input-integer code="assistant.tutorial.list.label.totalTime" path="totalTime" readonly="true"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'&& draftMode == false}">
			<acme:button code="student.enrolment.form.button.tutorialSession" action="/assistant/tutorial-session/list?tutorialId=${id}"/>		
			<acme:submit code="assistant.tutorial.button.unpublish" action="/assistant/tutorial/unpublish"/>	
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorial.button.update" action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.button.delete" action="/assistant/tutorial/delete"/>
			<acme:submit code="assistant.tutorial.button.publish" action="/assistant/tutorial/publish"/>
			<acme:button code="student.enrolment.form.button.tutorialSession" action="/assistant/tutorial-session/list?tutorialId=${id}"/>	

		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>		
	</jstl:choose>
			

</acme:form>