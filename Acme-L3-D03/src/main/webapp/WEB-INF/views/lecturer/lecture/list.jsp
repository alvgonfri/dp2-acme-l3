<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="70%"/>
	<acme:list-column code="lecturer.lecture.list.label.learningTime" path="learningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureType" path="lectureType" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list-all'}">
	<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create"/>
</jstl:if>