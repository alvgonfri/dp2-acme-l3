<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="administrator.system-configuration.form.label.defaultSystemCurrency" path="defaultSystemCurrency"/>	
	<acme:input-textbox code="administrator.system-configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|update')}">
		<acme:submit code="administrator.system-configuration.form.update" action="/administrator/system-configuration/update"/>
	</jstl:if>

	
</acme:form>