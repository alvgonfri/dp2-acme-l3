
package acme.features.auditor.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.Mark;
import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

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
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;
		Mark auditMark;

		auditMark = this.getAuditMark(object.getId());

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		if (this.repository.findManyAuditingRecordsByAuditId(object.getId()).isEmpty())
			tuple.put("mark", "-");
		else
			tuple.put("mark", auditMark);

		super.getResponse().setData(tuple);
	}

	public Mark getAuditMark(final int auditId) {

		final List<Pair<Mark, Integer>> aux = new ArrayList<>();

		final Mark[] marks = Mark.values();
		final Collection<AuditingRecord> auditingRecords = this.repository.findManyAuditingRecordsByAuditId(auditId);

		for (final Mark m : marks) {
			final Pair<Mark, Integer> p = Pair.of(m, auditingRecords.stream().filter(x -> x.getMark() == m).collect(Collectors.toList()).size());
			aux.add(p);
		}

		aux.sort((y, x) -> Integer.compare(x.getSecond(), y.getSecond()));

		if (aux.get(0).getSecond() != aux.get(1).getSecond())
			return aux.get(0).getFirst();
		else {
			final Integer mode = aux.get(0).getSecond();
			return aux.stream().filter(x -> x.getSecond() == mode).findAny().get().getFirst();

		}
	}

}
