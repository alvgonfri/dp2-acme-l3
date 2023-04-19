
package acme.features.student.dashboard;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.courses.LectureType;
import acme.entities.enrolments.Activity;
import acme.forms.StudentDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		StudentDashboard dashboard;
		int studentId;
		Collection<Activity> activities;
		List<Double> periods;
		List<Double> learningTimes;
		int numberOfTheoryActivites;
		int numberOfHandsOnActivites;
		double averagePeriodOfTheStudentActivities;
		double minimumPeriodOfTheStudentActivities;
		double maximumPeriodOfTheStudentActivities;
		double deviationOfThePeriodOfTheStudentActivities;
		double averageLearningTimeOfTheEnrolledCourses;
		double minimumLearningTimeOfTheEnrolledCourses;
		double maximumLearningTimeOfTheEnrolledCourses;
		double deviationLearningTimeOfTheEnrolledCourses;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();

		activities = this.repository.findActivitiesByStudentId(studentId);
		numberOfTheoryActivites = 0;
		numberOfHandsOnActivites = 0;
		for (final Activity a : activities)
			if (a.getType().equals(LectureType.THEORY))
				numberOfTheoryActivites++;
			else
				numberOfHandsOnActivites++;

		periods = this.getPeriodsOfTheStudentActivities(studentId);
		learningTimes = this.getLearningTimesOfTheStudentCourses(studentId);

		averagePeriodOfTheStudentActivities = periods.stream().mapToDouble(Double::doubleValue).sum() / periods.size();
		minimumPeriodOfTheStudentActivities = periods.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
		maximumPeriodOfTheStudentActivities = periods.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		deviationOfThePeriodOfTheStudentActivities = this.standardDeviation(periods);
		averageLearningTimeOfTheEnrolledCourses = learningTimes.stream().mapToDouble(Double::doubleValue).sum() / learningTimes.size();
		minimumLearningTimeOfTheEnrolledCourses = learningTimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
		maximumLearningTimeOfTheEnrolledCourses = learningTimes.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		deviationLearningTimeOfTheEnrolledCourses = this.standardDeviation(learningTimes);

		dashboard = new StudentDashboard();
		dashboard.setNumberOfTheoryActivites(numberOfTheoryActivites);
		dashboard.setNumberOfHandsOnActivites(numberOfHandsOnActivites);
		dashboard.setAveragePeriodOfTheStudentActivities(averagePeriodOfTheStudentActivities);
		dashboard.setMinimumPeriodOfTheStudentActivities(minimumPeriodOfTheStudentActivities);
		dashboard.setMaximumPeriodOfTheStudentActivities(maximumPeriodOfTheStudentActivities);
		dashboard.setDeviationOfThePeriodOfTheStudentActivities(deviationOfThePeriodOfTheStudentActivities);
		dashboard.setAverageLearningTimeOfTheEnrolledCourses(averageLearningTimeOfTheEnrolledCourses);
		dashboard.setMinimumLearningTimeOfTheEnrolledCourses(minimumLearningTimeOfTheEnrolledCourses);
		dashboard.setMaximumLearningTimeOfTheEnrolledCourses(maximumLearningTimeOfTheEnrolledCourses);
		dashboard.setDeviationLearningTimeOfTheEnrolledCourses(deviationLearningTimeOfTheEnrolledCourses);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"numberOfTheoryActivites", "numberOfHandsOnActivites", // 
			"averagePeriodOfTheStudentActivities", "minimumPeriodOfTheStudentActivities", //
			"maximumPeriodOfTheStudentActivities", "deviationOfThePeriodOfTheStudentActivities", //
			"averageLearningTimeOfTheEnrolledCourses", "minimumLearningTimeOfTheEnrolledCourses", //
			"maximumLearningTimeOfTheEnrolledCourses", "deviationLearningTimeOfTheEnrolledCourses");

		super.getResponse().setData(tuple);
	}

	// Aux --------------------------------------------------------------------

	private List<Double> getPeriodsOfTheStudentActivities(final int studentId) {
		final List<Double> result = new ArrayList<>();
		final Collection<Activity> activities = this.repository.findActivitiesByStudentId(studentId);
		for (final Activity activity : activities) {
			final Duration duration = MomentHelper.computeDuration(activity.getStartDate(), activity.getEndDate());
			final double diffInMinutes = duration.toMinutes();
			final double diffInHours = diffInMinutes / 60;
			result.add(diffInHours);
		}
		return result;
	}

	private List<Double> getLearningTimesOfTheStudentCourses(final int studentId) {
		final List<Double> result = new ArrayList<>();
		final Collection<Course> courses = this.repository.findEnrolledCoursesByStudentId(studentId);
		for (final Course course : courses) {
			final Collection<Lecture> lectures = this.repository.findLecturesByCourseId(course.getId());
			double learningTime = 0;
			for (final Lecture lecture : lectures)
				learningTime += lecture.getLearningTime();
			result.add(learningTime);
		}
		return result;
	}

	private double standardDeviation(final List<Double> list) {
		final double avg = list.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
		final double summation = list.stream().mapToDouble(numero -> Math.pow(numero - avg, 2)).sum();
		return Math.sqrt(summation / (list.size() - 1));
	}

}
