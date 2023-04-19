
package acme.features.authenticated.audit;

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
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListService extends AbstractService<Authenticated, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findManyAuditsByCourseId(courseId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Mark auditMark;

		auditMark = this.getAuditMark(object.getId());

		tuple = super.unbind(object, "code", "conclusion", "course.title");
		if (this.repository.findManyAuditingRecordsByAuditId(object.getId()).isEmpty())
			tuple.put("mark", "-");
		else
			tuple.put("mark", auditMark);

		super.getResponse().setData(tuple);
	}

	private Mark getAuditMark(final int auditId) {

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
