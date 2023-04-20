
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="assistant.tutorial.list.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.list.label.title" path="title"/>	
	<acme:input-textbox code="assistant.tutorial.list.label.summary" path="summary"/>
	<acme:input-textbox code="assistant.tutorial.list.label.goals" path="goals"/>
	<acme:input-select code="assistant.tutorial.list.label.course" path="course" choices="${courses}"/>


	<jstl:if test="${acme:anyOf(_command, 'show|create')}">
		<acme:submit code="assistant.tutorial.button.create" action="/assistant/tutorial/create"/>
	</jstl:if>
		

			

</acme:form>