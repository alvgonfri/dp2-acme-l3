<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.assistant.form.label.supervisor" path="supervisor"/>
	<acme:input-textbox code="authenticated.assistant.form.label.expertiseFields" path="expertiseFields"/>
	<acme:input-textbox code="authenticated.assistant.form.label.resume" path="resume"/>
	<acme:input-url code="authenticated.assistant.form.label.moreInfo" path="moreInfo"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.assistant.form.button.create" action="/authenticated/assistant/create"/>
		<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.assistant.form.button.update" action="/authenticated/assistant/update"/>
	</jstl:if>
</acme:form>