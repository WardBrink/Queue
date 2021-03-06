package queue.helpers;

import java.util.concurrent.ScheduledFuture;

import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import queue.proxies.ENU_JobStatus;
//import queue.proxies.ENU_JobStatus;
import queue.proxies.Job;
import queue.repositories.ScheduledJobRepository;

public class JobCanceller {
	
	public boolean cancel(IContext context, ScheduledJobRepository scheduledJobRepository, Job job, boolean removeWhenRunning) throws CoreException {
		ScheduledFuture<?> future;
		
		IMendixObject jobObject = job.getMendixObject();
		
		future = scheduledJobRepository.get(context, jobObject);
		
		if(future == null) {
			throw new CoreException("Job cannot be cancelled, because ScheduledFuture does not exist.");
		}
		
		future.cancel(removeWhenRunning);
		
		if(future.isCancelled()) {
			job.setStatus(context, ENU_JobStatus.Cancelled);
			job.commit(context);
			return true;
		}
		
		throw new CoreException("Job cannot be cancelled, because it has already completed, has already been cancelled, or could not be cancelled for some other reason.");
	}

}
