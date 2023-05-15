/*
 * AuthenticatedAnnnouncementShowTest.java
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

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sessionRecordIndex, final String title, final String summary, final String startDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("View sessions");

		super.clickOnButton("Create a practicum session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(sessionRecordIndex, 0, title);
		super.checkColumnHasValue(sessionRecordIndex, 1, startDate);

		super.clickOnListingRecord(sessionRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String startDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("View sessions");

		super.clickOnButton("Create a practicum session");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a practicum using principals with
		// HINT+ inappropriate roles.

		final Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("employer1");
		for (final Practicum practicum : practicums) {
			param = String.format("practicumId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a duty for a published practicum created by 
		// HINT+ the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("employer1");
		for (final Practicum practicum : practicums)
			if (!practicum.isDraftMode()) {
				param = String.format("practicumId=%d", practicum.getId());
				super.request("/company/practicum-session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create duties for jobs that weren't created 
		// HINT+ by the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("employer2");
		for (final Practicum practicum : practicums) {
			param = String.format("practicumId=%d", practicum.getId());
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
		}
	}

}