<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.lecture.form.label.title" path="title"/>	
	<acme:input-textbox code="student.lecture.form.label.summary" path="summary"/>
	<acme:input-integer code="student.lecture.form.label.learningTime" path="learningTime"/>
	<acme:input-textarea code="student.lecture.form.label.body" path="body"/>
	<acme:input-textbox code="student.lecture.form.label.lectureType" path="lectureType"/>
	<acme:input-url code="student.lecture.form.label.moreInfo" path="moreInfo"/>
</acme:form>