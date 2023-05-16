
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String summary, final String title, final String goals, final String course, final String totalTime) {
		// HINT: this test logs in as an employer, lists his or her jobs, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("Code", code);
		super.checkInputBoxHasValue("Summary", summary);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Goals", goals);
		super.checkInputBoxHasValue("Course", course);
		super.checkInputBoxHasValue("Total time", totalTime);
		super.clickOnSubmit("Unpublish");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("Code", code);
		super.checkInputBoxHasValue("Summary", summary);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Goals", goals);
		super.checkInputBoxHasValue("Course", course);
		super.checkInputBoxHasValue("Total time", totalTime);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, course);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String summary, final String title, final String goals, final String course, final String totalTime) {
		// HINT: this test attempts to update a job with wrong data.

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("Code", code);
		super.checkInputBoxHasValue("Summary", summary);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Goals", goals);
		super.checkInputBoxHasValue("Course", course);
		super.checkInputBoxHasValue("Total time", totalTime);
		super.clickOnSubmit("Unpublish");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("Code", code);
		super.checkInputBoxHasValue("Summary", summary);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Goals", goals);
		super.checkInputBoxHasValue("Course", course);
		super.checkInputBoxHasValue("Total time", totalTime);
		super.clickOnSubmit("Update");
		;

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a job with a role other than "Employer",
		// HINT+ or using an employer who is not the owner.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode()) {
				param = String.format("id=%d", tutorial.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/tutorial/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/assistant/tutorial/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
