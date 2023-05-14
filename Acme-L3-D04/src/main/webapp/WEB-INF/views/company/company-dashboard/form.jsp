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
	<acme:message code="company.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="company.dashboard.form.title.practica-length"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.numberOfPracticas"/>
		</th>
		<td>
			<acme:print value="${numberOfPracticas == null ? '--' : numberOfPracticas}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.averagePeriodLengthPractica"/>
		</th>
		<td>
			<acme:print value="${minimumPeriodLengthPractica == null ? '--' : minimumPeriodLengthPractica}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.minimumPeriodLengthPractica"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodLengthPractica == null ? '--' : deviationPeriodLengthPractica}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practica-length.max"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodLengthPractica == null ? '--' : maximumPeriodLengthPractica}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviationPeriodLengthPractica"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodLengthPractica == null ? '--' : deviationPeriodLengthPractica}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.session-length"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.numberOfSessions"/>
		</th>
		<td>
			<acme:print value="${numberOfSessions == null ? '--' : numberOfSessions}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.averagePeriodLengthSession"/>
		</th>
		<td>
			<acme:print value="${averagePeriodLengthSession == null ? '--' : averagePeriodLengthSession}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.minimumPeriodLengthSession"/>
		</th>
		<td>
			<acme:print value="${minimumPeriodLengthSession == null ? '--' : minimumPeriodLengthSession}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.maximumPeriodLengthSession"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodLengthSession == null ? '--' : maximumPeriodLengthSession}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviationPeriodLengthSession"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodLengthSession == null ? '--' : deviationPeriodLengthSession}"/>
		</td>
	</tr>
</table>


<h2>
	<acme:message code="company.dashboard.form.title.practicaNumberPerMonth"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
	Total ${practicaNumberPerMonth.get('FEBRUARY')}
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${practicaNumberPerMonth.get('JANUARY')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('FEBRUARY')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('MARCH')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('APRIL')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('MAY')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('JUNE')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('JULY')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('AUGUST')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('SEPTEMBER')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('OCTOBER')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('NOVEMBER')}"/>,
						<jstl:out value="${practicaNumberPerMonth.get('DECEMBER')}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
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
