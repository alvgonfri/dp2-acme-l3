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

<h2>
	<acme:message code="student.dashboard.form.title.activitiesDuration"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.average"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${averagePeriodOfTheStudentActivities}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.minimum"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${minimumPeriodOfTheStudentActivities}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.maximum"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${maximumPeriodOfTheStudentActivities}"/>
			</jstl:if>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviation"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${deviationOfThePeriodOfTheStudentActivities}"/>
			</jstl:if>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="student.dashboard.form.title.coursesLearningTime"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.average"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${averageLearningTimeOfTheEnrolledCourses}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.minimum"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${minimumLearningTimeOfTheEnrolledCourses}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.maximum"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${maximumLearningTimeOfTheEnrolledCourses}"/>
			</jstl:if>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviation"/>
		</th>
		<td>
			<jstl:if test="${noActivities == true}">
				<acme:print value="-"/>
			</jstl:if>
			<jstl:if test="${noActivities == false}">
				<acme:print value="${deviationLearningTimeOfTheEnrolledCourses}"/>
			</jstl:if>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="student.dashboard.form.title.numberOfActivites"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"THEORY", "HANDS ON"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${numberOfTheoryActivites}"/>, 
						<jstl:out value="${numberOfHandsOnActivites}"/>, 
					]
				}
			]
		};
		var maxValue = 5.0 + Math.max(
				  <jstl:out value="${numberOfHandsOnActivites}"/>,
				  <jstl:out value="${numberOfTheoryActivites}"/>
				);
		var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0.0,
								suggestedMax : maxValue
							}
						}
					]
				},
				legend : {
					display : false
				}
			};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

