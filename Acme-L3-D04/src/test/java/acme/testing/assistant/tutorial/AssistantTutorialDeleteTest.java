
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantTutorialTestRepository repository;
	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals, final String course, final String totalTime) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("totalTime", totalTime);
		super.clickOnSubmit("Unpublish");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();
	}

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String goals, final String course) {
	//		// HINT: this test attempts to create jobs with incorrect data.
	//
	//		super.signIn("assistant1", "assistant1");
	//		super.clickOnMenu("Assistant", "Tutorials");
	//		super.sortListing(0, "asc");
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//
	//		super.checkInputBoxHasValue("Code", code);
	//		super.checkInputBoxHasValue("Summary", summary);
	//		super.checkInputBoxHasValue("Title", title);
	//		super.checkInputBoxHasValue("Goals", goals);
	//		super.checkInputBoxHasValue("Course", course);
	//		super.checkInputBoxHasValue("Total time", totalTime);
	//		super.clickOnSubmit("Unpublish");
	//
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//		super.clickOnSubmit("Delete");
	//
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}

	@Test
	public void test300Hacking() {

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode() || !tutorial.isDraftMode()) {
				param = String.format("id=%d", tutorial.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/tutorial/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/assistant/tutorial/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial/delte", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
