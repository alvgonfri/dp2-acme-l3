<%--
- panic.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" isErrorPage="true"
	import="
		javax.servlet.jsp.ErrorData,
		org.apache.commons.logging.Log,
		org.apache.commons.logging.LogFactory,
		org.springframework.http.HttpStatus,		
		acme.framework.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<%
	Log log;
	Throwable oops;
	ErrorData errorData;
	String reason;
	
	oops = (Throwable)pageContext.getAttribute("oops", 2);
	errorData = pageContext.getErrorData();
	if (oops == null && errorData != null)
		oops = errorData.getThrowable();
	reason = HttpStatus.resolve(response.getStatus()) != null ? HttpStatus.valueOf(response.getStatus()).getReasonPhrase() : "Unknown failure";
			
	log = LogFactory.getLog(getClass());
	log.error(String.format("HTTP %s %s %s", request.getMethod(), JspHelper.getRequestUrl(request), request.getProtocol()));
	log.error(String.format("Status: %d %s", response.getStatus(), reason));
	if (oops != null)
		log.error(String.format("Exception: %s", JspHelper.format(oops)));
	
	if (request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1") || request.getParameter("debug") != null)
		pageContext.setAttribute("oops", oops, 2);
	else
		pageContext.setAttribute("oops", null, 2);
	pageContext.setAttribute("method", request.getMethod());
	pageContext.setAttribute("url", JspHelper.getRequestUrl(request));
	pageContext.setAttribute("protocol", request.getProtocol());
	pageContext.setAttribute("status", response.getStatus());
	pageContext.setAttribute("reason", reason);
%>

<h1>
	<acme:message code="master.panic.title"/>			
</h1>

<p>
	<acme:message code="master.panic.text"/>			
</p>

<dl>
	<dt>
		<acme:message code="master.panic.label.request"/>
	</dt>
	<dd>
		<jstl:out value="${method}"/>
		&nbsp;
		<jstl:out value="${url}"/>
		&nbsp;
		<jstl:out value="${protocol}"/>
	</dd>
	
	<dt>
		<acme:message code="master.panic.label.status"/>
	</dt>
	<dd>
		<jstl:out value="${status}"/>
		&nbsp;
		<jstl:out value="${reason}"/> 
	</dd>	
	<jstl:if test="${oops != null}">
		<dt>
			<acme:message code="master.panic.label.exceptions"/>
		</dt>
		<dd>
			<jstl:set var="current" value="${oops}"/>			
			<jstl:forEach var="i" begin="1" end="100">
				<jstl:if test="${current != null}">
 					<jstl:set var="oopsTitle" value="${JspHelper.format(current.stackTrace[0].toString())}"/>
					<jstl:set var="oopsDescription" value="${JspHelper.format(current.toString())}"/> 
 					<div>
  						<div class="text-danger" style="word-wrap: break-word;"><strong><jstl:out value="${oopsTitle}"/></strong></div>
  						<div>
  							<div style="float: left;">&#x2937;</div>
  							<div class="text-info" style="white-space: pre-wrap; margin-left: 2em;"><jstl:out value="${oopsDescription}"/></div>
  						</div>
					</div>
					<jstl:set var="current" value="${current.cause}"/>
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${current != null}">
				<acme:message code="master.panic.text.consult-log"/>
			</jstl:if>
		</dd>
	</jstl:if>
</dl>
