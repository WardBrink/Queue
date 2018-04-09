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
import queue.helpers.QueueController;
import queue.proxies.constants.Constants;
import queue.repositories.QueueRepository;

public class ShutdownQueue extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String Name;
	private java.lang.Boolean Gracefully;
	private java.lang.Boolean AwaitTermination;
	private java.lang.Long TerminationTimeout;

	public ShutdownQueue(IContext context, java.lang.String Name, java.lang.Boolean Gracefully, java.lang.Boolean AwaitTermination, java.lang.Long TerminationTimeout)
	{
		super(context);
		this.Name = Name;
		this.Gracefully = Gracefully;
		this.AwaitTermination = AwaitTermination;
		this.TerminationTimeout = TerminationTimeout;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		ILogNode logger = Core.getLogger(Constants.getLOGNODE());
		QueueController queueController = new QueueController(logger);
		QueueRepository queueRepository = QueueRepository.getInstance();
		return queueController.shutdown(queueRepository, Name, Gracefully, AwaitTermination, TerminationTimeout.intValue());
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "ShutdownQueue";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
