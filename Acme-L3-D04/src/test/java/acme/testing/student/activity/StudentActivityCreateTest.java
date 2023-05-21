
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String summary, final String type, final String startDate, final String endDate, final String moreInfo) {
		// HINT: this test authenticates as an student, list his or her enrolments, navigates
		// HINT+ to their activities, creates an activity and checks that it has the expected data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.signOut();
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, type);
		super.checkColumnHasValue(activityRecordIndex, 2, startDate);
		super.checkColumnHasValue(activityRecordIndex, 3, endDate);

		super.clickOnListingRecord(activityRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String summary, final String type, final String startDate, final String endDate, final String moreInfo) {
		// HINT: this test attempts to create activities using wrong data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create an activity for an enrolment as a principal without 
		// HINT: the "Student" role.

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("enrolmentId=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create an activity for a not finalised enrolment created by 
		// HINT+ the principal.

		Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				param = String.format("enrolmentId=%d", enrolment.getId());
				super.request("/student/activity/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create activities for enrolments that weren't created 
		// HINT+ by the principal.

		Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("enrolmentId=%d", enrolment.getId());
			super.request("/student/activity/create", param);
			super.checkPanicExists();
		}
	}

}
