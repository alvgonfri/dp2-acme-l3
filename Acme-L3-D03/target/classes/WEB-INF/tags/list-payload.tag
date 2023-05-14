<%--
- list-payload.tag
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty" import="
	java.util.Map, 
	java.util.HashMap, 
	acme.framework.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="format" required="false" type="java.lang.String"%>

<jstl:if test="${format == null}">
	<jstl:set var="format" value="{0}"/>
</jstl:if>

<%
	Map<String, Object> column;
	Boolean sortable;

	column = new HashMap<String, Object>();
	column.put("type", "payload");
	column.put("path", jspContext.getAttribute("path"));
	column.put("format", jspContext.getAttribute("format"));
	
	JspHelper.updateDatatableColumns(request, column);
%>


