
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String retailPrice, final String moreInfo) {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Course> courses;

		String query;

		courses = this.repository.findManyCoursesByUserName("lecturer1");

		for (final Course c : courses) {
			query = String.format("id=%d", c.getId());
			super.request("/lecturer/course/show", query);
			super.checkPanicExists();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/show", query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/course/show", query);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
