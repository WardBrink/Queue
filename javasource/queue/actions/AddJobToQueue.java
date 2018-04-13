// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package queue.actions;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import queue.helpers.ExponentialBackoffCalculator;
import queue.helpers.JobToQueueAdder;
import queue.helpers.JobValidator;
import queue.helpers.MicroflowValidator;
import queue.proxies.constants.Constants;
import queue.repositories.ScheduledJobRepository;
import queue.repositories.JobRepository;
import queue.repositories.MicroflowRepository;
import queue.repositories.QueueRepository;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class AddJobToQueue extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __job;
	private queue.proxies.Job job;

	public AddJobToQueue(IContext context, IMendixObject job)
	{
		super(context);
		this.__job = job;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.job = __job == null ? null : queue.proxies.Job.initialize(getContext(), __job);

		// BEGIN USER CODE
		ILogNode logger = Core.getLogger(Constants.getLOGNODE());
		MicroflowRepository microflowRepository = new MicroflowRepository();
		MicroflowValidator microflowValidator = new MicroflowValidator(microflowRepository);
		JobValidator jobValidator = new JobValidator(logger, microflowValidator);
		ExponentialBackoffCalculator exponentialBackoffCalculator = new ExponentialBackoffCalculator();
		JobToQueueAdder adder = new JobToQueueAdder(jobValidator, exponentialBackoffCalculator);
		ScheduledJobRepository scheduledJobRepository = ScheduledJobRepository.getInstance();
		QueueRepository queueRepository = QueueRepository.getInstance();
		JobRepository jobRepository = new JobRepository();
		
		adder.add(this.context(), logger, queueRepository, jobRepository, scheduledJobRepository, job);
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "AddJobToQueue";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
