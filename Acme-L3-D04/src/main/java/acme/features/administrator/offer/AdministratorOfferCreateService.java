
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Offer object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Offer();
		object.setMoment(moment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "moment", "heading", "summary", "startAvailable", "endAvailable", "price", "moreInfo");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		Calendar calendar;
		final Date latestDate;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2100);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		latestDate = new Date(calendar.getTimeInMillis());

		final Date earliestDate;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2000);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		earliestDate = new Date(calendar.getTimeInMillis());

		if (!super.getBuffer().getErrors().hasErrors("moment") && !super.getBuffer().getErrors().hasErrors("startAvailable")) {
			boolean isValid;

			isValid = MomentHelper.isLongEnough(object.getMoment(), object.getStartAvailable(), 1, ChronoUnit.DAYS) && MomentHelper.isFuture(object.getStartAvailable());

			super.state(isValid, "startAvailable", "administrator.offer.error.onedayfuture");
		}

		if (!super.getBuffer().getErrors().hasErrors("endAvailable") && !super.getBuffer().getErrors().hasErrors("startAvailable")) {
			boolean isValid;

			isValid = MomentHelper.isLongEnough(object.getStartAvailable(), object.getEndAvailable(), 1, ChronoUnit.WEEKS) && MomentHelper.isBefore(object.getStartAvailable(), object.getEndAvailable());

			super.state(isValid, "startAvailable", "administrator.offer.error.oneweeklong");
			super.state(isValid, "endAvailable", "administrator.offer.error.oneweeklong");

		}

		if (!super.getBuffer().getErrors().hasErrors("startAvailable")) {

			boolean isValid;

			isValid = MomentHelper.isBeforeOrEqual(object.getStartAvailable(), latestDate);
			super.state(isValid, "startAvailable", "administrator.offer.error.datelimits");
		}

		if (!super.getBuffer().getErrors().hasErrors("endAvailable")) {

			boolean isValid;

			isValid = MomentHelper.isAfterOrEqual(object.getEndAvailable(), earliestDate) && MomentHelper.isBeforeOrEqual(object.getEndAvailable(), latestDate);
			super.state(isValid, "endAvailable", "administrator.offer.error.datelimits");
		}

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() >= 0, "price", "administrator.offer.error.negativePrice");

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "heading", "summary", "startAvailable", "endAvailable", "price", "moreInfo");

		super.getResponse().setData(tuple);
	}

}
