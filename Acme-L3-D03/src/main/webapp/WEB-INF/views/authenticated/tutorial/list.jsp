<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.tutorial.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="authenticated.tutorial.list.label.summary" path="summary"  width="60%"/>
</acme:list>