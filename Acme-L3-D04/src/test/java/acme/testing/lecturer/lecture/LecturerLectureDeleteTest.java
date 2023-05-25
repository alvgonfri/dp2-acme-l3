
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String learningTime, final String body, final String lectureType, final String moreInfo) {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.clickOnSubmit("Delete");

		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Lecture> lectures;

		String query;

		lectures = this.repository.findManyLecturesByUserName("lecturer1");

		super.signIn("lecturer1", "lecturer1");
		for (final Lecture l : lectures)
			if (l.isDraftMode()) {
				query = String.format("id=%d", l.getId());
				super.request("/lecturer/lecture/delete", query);
				super.checkPanicExists();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Lecture> lectures;

		String query;

		lectures = this.repository.findManyLecturesByUserName("lecturer1");

		for (final Lecture l : lectures) {
			query = String.format("id=%d", l.getId());
			super.request("/lecturer/lecture/delete", query);
			super.checkPanicExists();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/delete", query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course/delete", query);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
