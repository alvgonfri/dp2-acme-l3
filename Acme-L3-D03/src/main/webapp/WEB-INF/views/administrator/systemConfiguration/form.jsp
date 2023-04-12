<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="administrator.system-configuration.form.label.systemCurrency" path="systemCurrency"/>	
	<acme:input-textbox code="administrator.system-configuration.form.label.aceptedCurrencies" path="aceptedCurrencies"/>
	
		<jstl:when test="${acme:anyOf(_command, update)}">
			<acme:submit code="administrator.system-configuration.form.button.update" action="/administrator/banner/update"/>
		</jstl:when>

</acme:form>

