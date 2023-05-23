
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Activity;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final String code, final int activityRecordIndex, final String title, final String summary, final String type, final String startDate, final String endDate, final String moreInfo) {
		// HINT: this test signs in as an student, lists his or her activities, selects
		// HINT+ one of them and checks that it's as expected.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an activity of an enrolment that is in finished
		// HINT+ using a principal that didn't create it.

		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student1");
		for (final Activity activity : activities)
			if (!activity.getEnrolment().isDraftMode()) {
				param = String.format("id=%d", activity.getEnrolment().getId());

				super.checkLinkExists("Sign in");
				super.request("/student/activity/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student2", "student2");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
