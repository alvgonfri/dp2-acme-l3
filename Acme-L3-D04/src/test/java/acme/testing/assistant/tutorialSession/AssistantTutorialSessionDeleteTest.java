
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final int sessionRecordIndex, final String title, final String summary, final String sessionType, final String startDate, final String endDate, final String moreInfo) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnSubmit("Unpublish");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("Tutorial Session");

		super.checkListingExists();
		super.checkColumnHasValue(sessionRecordIndex, 0, title);
		super.clickOnListingRecord(sessionRecordIndex);
		super.clickOnSubmit("Delete");

		super.checkListingEmpty();

		super.signOut();
	}

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/assistant/tutorialSession/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int tutorialRecordIndex, final String code, final int sessionRecordIndex, final String title, final String summary, final String sessionType, final String startDate, final String endDate, final String moreInfo) {
	//		// HINT: this test attempts to create jobs with incorrect data.
	//
	//		super.signIn("assistant1", "assistant1");
	//
	//		super.clickOnMenu("Assistant", "Tutorials");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
	//		super.clickOnListingRecord(tutorialRecordIndex);
	//		super.checkInputBoxHasValue("code", code);
	//		//		super.clickOnSubmit("Unpublish");
	//		//
	//		//		super.clickOnMenu("Assistant", "Tutorials");
	//		//		super.checkListingExists();
	//		//		super.sortListing(0, "asc");
	//		//
	//		//		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
	//		//		super.clickOnListingRecord(tutorialRecordIndex);
	//		//		super.checkInputBoxHasValue("code", code);
	//		super.clickOnButton("Tutorial Session");
	//
	//		super.checkListingExists();
	//		super.checkColumnHasValue(sessionRecordIndex, 0, title);
	//		super.clickOnListingRecord(sessionRecordIndex);
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("summary", summary);
	//		super.fillInputBoxIn("sessionType", sessionType);
	//		super.fillInputBoxIn("startDate", startDate);
	//		super.fillInputBoxIn("endDate", endDate);
	//		super.fillInputBoxIn("moreInfo", moreInfo);
	//		super.clickOnSubmit("Update");
	//
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}

	@Test
	public void test300Hacking() {

		final Collection<Tutorial> tutorials;
		String param;
		Collection<TutorialSession> sessions;

		tutorials = this.repository.findManyTutorialByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode() || !tutorial.isDraftMode()) {
				sessions = this.repository.findManySessionsByTutorial(tutorial);
				for (final TutorialSession session : sessions) {
					param = String.format("tutorialId=%d", session.getId());

					super.checkLinkExists("Sign in");
					super.request("/assistant/tutorial-session/deletee", param);
					super.checkPanicExists();

					super.signIn("administrator", "administrator");
					super.request("/assistant/tutorial-session/delete", param);
					super.checkPanicExists();
					super.signOut();

					super.signIn("lecturer1", "lecturer1");
					super.request("/assistant/tutorial-session/delete", param);
					super.checkPanicExists();
					super.signOut();

				}

			}

	}

}
