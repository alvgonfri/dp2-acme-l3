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

package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String course, final String title, final String summary, final String goals) {
		// HINT: En este test intentamos crear una practica con datos correctos.
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.clickOnButton("Create a practicum");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, summary);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("goals", goals);

		super.clickOnButton("View sessions");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String course, final String title, final String summary, final String goals) {
		// HINT: En este test intentamos crear una practica con datos incorrectos.

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicas list");
		super.checkListingExists();
		super.clickOnButton("Create a practicum");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: Este test intenta crear una practica con roles inapropiados

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();
	}

}
