
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.TrainingModule;
import acme.entities.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private DeveloperTrainingSessionRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		final boolean status;
		int masterId;
		TrainingModule trainingModule;
		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);

		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);

		object = new TrainingSession();
		object.setDraftMode(true);
		object.setTrainingModule(trainingModule);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated");
		}
		/*
		 * if (!super.getBuffer().getErrors().hasErrors("trainingModule")) {
		 * super.state(object.getTrainingModule().isDraftMode(), "trainingModule", "developer.training-session.form.error.published-training-module");
		 * super.state(object.getTrainingModule() != null, "trainingModule", "developer.training-session.form.error.null-training-module.");
		 * }
		 */
		if (!super.getBuffer().getErrors().hasErrors("startPeriod") && object.getStartPeriod() != null) {

			Date maxStartPeriod;
			maxStartPeriod = MomentHelper.parse("2200/12/24 23:59", "yyyy/MM/dd HH:mm");

			if (object.getTrainingModule() != null) {
				Date minEndPeriod;
				minEndPeriod = MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS);

				super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minEndPeriod), "startPeriod", "developer.training-session.form.error.not-one-week-ahead-trainingModule");
			}
			super.state(MomentHelper.isBeforeOrEqual(object.getStartPeriod(), maxStartPeriod), "startPeriod", "developer.training-session.form.error.invalid-date-start-period");

		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && object.getEndPeriod() != null) {

			Date maxEndPeriod;
			maxEndPeriod = MomentHelper.parse("2200/12/31 23:59", "yyyy/MM/dd HH:mm");

			if (object.getStartPeriod() != null) {
				Date minEndPeriod;
				minEndPeriod = MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS);

				super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minEndPeriod), "endPeriod", "developer.training-session.form.error.not-one-week-long");
				super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "developer.training-session.form.error.invalidEndPeriod");

			}
			super.state(MomentHelper.isBeforeOrEqual(object.getEndPeriod(), maxEndPeriod), "endPeriod", "developer.training-session.form.error.invalid-date-end-period");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.isDraftMode());
		dataset.put("trainingModule", object.getTrainingModule().getCode());

		super.getResponse().addData(dataset);
	}
}
