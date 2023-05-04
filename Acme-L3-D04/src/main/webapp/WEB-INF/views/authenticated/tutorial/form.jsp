<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly = "true">
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.summary" path="summary"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.goals" path="goals"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.company" path="assistant"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.estimatedTime" path="totalTime"/>
</acme:form>