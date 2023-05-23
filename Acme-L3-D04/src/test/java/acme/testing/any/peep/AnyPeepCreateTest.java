
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String nick, final String message, final String email, final String moreInfo) {

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.signIn("assistant1", "assistant1");
		super.signOut();

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, nick);

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String moment, final String title, final String nick, final String message, final String email, final String moreInfo) {

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

	}

	@Test
	public void test300Hacking() {
		// HINT: there's no hacking test case for this listing, because list of
		// HINT+ peeps are accessible by everyone.
	}

	// Ancillary methods ------------------------------------------------------

}
