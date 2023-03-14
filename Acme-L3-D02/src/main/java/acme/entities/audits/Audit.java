
package acme.entities.audits;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.util.Pair;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	// Derived attributes -----------------------------------------------------


	@NotNull
	public Mark getMark() {
		final List<Pair<Mark, Integer>> aux = new ArrayList<>();
		final Mark[] marks = Mark.values();
		for (final Mark m : marks) {
			final Pair<Mark, Integer> p = Pair.of(m, this.auditingRecords.stream().filter(x -> x.getMark() == m).collect(Collectors.toList()).size());
			aux.add(p);
		}

		aux.stream().sorted((x, y) -> y.getSecond().compareTo(x.getSecond())).collect(Collectors.toList());

		//si solo hay una nota que es moda la devuelvo
		if (aux.get(0).getSecond() != aux.get(1).getSecond())
			return aux.get(0).getFirst();
		else {
			final Integer mode = aux.get(0).getSecond();
			return aux.stream().filter(x -> x.getSecond() == mode).findAny().get().getFirst();

		}

	}
	// Relationships ----------------------------------------------------------


	@ManyToOne
	protected Course				course;

	@OneToMany
	protected List<AuditingRecord>	auditingRecords;
}
