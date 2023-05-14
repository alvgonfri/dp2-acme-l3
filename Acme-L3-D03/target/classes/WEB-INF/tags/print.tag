<%--
- print.tag
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag 
	language="java" body-content="empty" trimDirectiveWhitespaces="true"
	import="java.util.Locale, java.text.MessageFormat, acme.framework.helpers.ConversionHelper"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<%@attribute name="var" required="false" type="java.lang.String"%>
<%@attribute name="value" required="true" type="java.lang.Object"%>
<%@attribute name="format" required="false" type="java.lang.Object"%>

<%-- HINT: Consult https://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html --%>

<%
	String variable;
	Object value;	
	String format;
	Locale locale;
	MessageFormat formatter;
	String text;
		
	variable = (String)jspContext.getAttribute("var");
	value = jspContext.getAttribute("value");
	format = (String) jspContext.getAttribute("format");

	if (format == null) 
		text = ConversionHelper.convert(value, String.class);
	else {
		locale = response.getLocale();
		formatter = new MessageFormat(format, locale);
		text = formatter.format(new Object[] {value});		
	}
	
	text = (text == null ? "" : text.trim());
	
	jspContext.setAttribute("text", text);
	if (variable != null)
		request.setAttribute(variable, text);
%>

<jstl:if test="${var == null}">
	<jstl:out value="${text}"/>
</jstl:if>
