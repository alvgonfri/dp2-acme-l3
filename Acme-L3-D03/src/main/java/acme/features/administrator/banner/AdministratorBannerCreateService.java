
package acme.features.administrator.banner;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		Date moment;
		Date displayStart;
		Date displayEnd;
		Calendar calendar;
		final int MILLIS_IN_ONE_MINUTE = 60 * 1000;

		moment = MomentHelper.getCurrentMoment();

		displayStart = MomentHelper.getCurrentMoment();
		displayStart.setTime(displayStart.getTime() + MILLIS_IN_ONE_MINUTE);

		calendar = Calendar.getInstance();
		calendar.setTime(displayStart);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		displayEnd = calendar.getTime();

		object = new Banner();
		object.setMoment(moment);
		object.setDisplayStart(displayStart);
		object.setDisplayEnd(displayEnd);
		object.setPicture("");
		object.setSlogan("");
		object.setTarget("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "moment", "slogan", "displayStart", "displayEnd", "picture", "target");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			Calendar calendar;
			final Date date;

			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2100);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			date = new Date(calendar.getTimeInMillis());

			super.state(MomentHelper.isBeforeOrEqual(object.getDisplayEnd(), date), "displayEnd", "administrator.banner.form.error.wrong-end");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayStart"))
			super.state(MomentHelper.isFuture(object.getDisplayStart()), "displayStart", "administrator.banner.form.error.wrong-displayStart");

		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			Date displayStart;
			Date displayStartPlusOneWeek;
			Calendar calendar;

			displayStart = object.getDisplayStart();
			calendar = Calendar.getInstance();
			calendar.setTime(displayStart);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			displayStartPlusOneWeek = calendar.getTime();

			super.state(MomentHelper.isAfterOrEqual(object.getDisplayEnd(), displayStartPlusOneWeek), "displayEnd", "administrator.banner.form.error.wrong-displayEnd");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "slogan", "displayStart", "displayEnd", "picture", "target");

		super.getResponse().setData(tuple);
	}

}
