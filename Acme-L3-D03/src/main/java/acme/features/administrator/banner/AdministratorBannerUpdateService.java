
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
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

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
