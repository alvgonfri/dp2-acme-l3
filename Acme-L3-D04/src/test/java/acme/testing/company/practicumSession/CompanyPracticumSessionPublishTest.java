/*
 * AuthenticatedAnnouncementListTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionPublishTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sessionRecordIndex, final String title, final String startDate, final String endDate) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("View sessions");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(sessionRecordIndex, 0, title);
		super.checkColumnHasValue(sessionRecordIndex, 1, startDate);
		super.checkColumnHasValue(sessionRecordIndex, 2, endDate);

		super.clickOnListingRecord(sessionRecordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int sessionRecordIndex, final String title, final String startDate, final String endDate) {
		// HINT: this test attempts to publish a practicum-session that cannot be published, yet.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("View sessions");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(sessionRecordIndex, 0, title);
		super.checkColumnHasValue(sessionRecordIndex, 1, startDate);
		super.checkColumnHasValue(sessionRecordIndex, 2, endDate);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a practicum-session with a role other than "Employer".

		final Collection<PracticumSession> sessions;
		String params;

		sessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession session : sessions)
			if (session.isDraftMode()) {
				params = String.format("id=%d", session.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/publish", params);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/practicum-session/publish", params);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published practicum-session that was registered by the principal.

		Collection<PracticumSession> sessions;
		String params;

		super.signIn("company1", "company1");
		sessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession session : sessions)
			if (!session.isDraftMode()) {
				params = String.format("id=%d", session.getId());
				super.request("/company/practicum-session/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a practicum-session that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		Collection<PracticumSession> sessions;
		String params;

		super.signIn("company2", "company2");
		sessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession session : sessions) {
			params = String.format("id=%d", session.getId());
			super.request("/company/practicum-session/publish", params);
		}
		super.signOut();
	}

}
