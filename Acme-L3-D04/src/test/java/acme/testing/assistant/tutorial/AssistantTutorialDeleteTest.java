
package acme.testing.assistant.tutorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

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
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, course);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.signOut();
	}

}
