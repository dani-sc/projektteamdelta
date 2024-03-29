package at.itb13.oculus.application.queue;

import java.util.List;

import at.itb13.oculus.application.ControllerFacade;
import at.itb13.oculus.domain.CalendarEvent;
import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.domain.Queue;
import at.itb13.oculus.domain.readonlyinterfaces.CalendarEventRO;
import at.itb13.oculus.domain.readonlyinterfaces.PatientRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueEntryRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueRO;
import at.itb13.oculus.technicalServices.dao.QueueDao;
import at.itb13.teamD.application.exceptions.InvalidInputException;

/**
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 14.04.2015
 */
public class QueueController {
	private Queue _queue;
	
	public QueueController(Queue queue) {
		_queue = queue;
	}
	
	public QueueRO getQueue() {
		return _queue;
	}
	
	public List<? extends QueueEntryRO> getQueueEntries() {
		return _queue.getQueueEntries();
	}
	
	/**
	 * Inserts queueEntryRO to the last position in the Queue's QueueEntryList and updates the database accordingly.
	 * 
	 * @param queueEntryRO the QueueEntry to be inserted
	 * @param calendarEventRO may be null, if only the patient without reference to a calendarEvent should be inserted
	 * @return true if the queueEntry was successfully added to the Queue and saved in the database and patient wasn't in the queue before.
	 * @throws InvalidInputException Indicates that the patient is already in a queue. A patient may only be in 1 queue at a time.
	 * @see #pushQueueEntry(QueueEntryRO, CalendarEventRO)
	 */
	public boolean pushQueueEntry(PatientRO patientRO, CalendarEventRO calendarEventRO) throws InvalidInputException {
		for(QueueController qC : ControllerFacade.getInstance().getAllQueueController()) {
			if(qC.getQueue().containsPatient(patientRO.getPatientId())) {
				throw new InvalidInputException("Patient (" + patientRO.getPatientId() + ") is already in a queue");
			}
		}
		_queue.pushQueueEntry((Patient) patientRO, (CalendarEvent) calendarEventRO);
		return QueueDao.getInstance().makePersistent(_queue); 
	}
	
	/**
	 * Returns the QueueEntry that's at the front of the queue and removes it from the queue, as well as from the database.
	 * May update the calendarEventRo's state (isOpen) if it's not the general orthoptist queue. TODO: Or not? -> delete?
	 * To only read but not remove the QueueEntry, use {@link #peekQueueEntry()} instead.
	 * 
	 * @return null if the Queue's list of QueueEntries is empty.
	 */
	public QueueEntryRO popQueueEntry() {
		QueueEntryRO queueEntryRO = _queue.popQueueEntry();
		if(queueEntryRO != null) {
			QueueDao.getInstance().makePersistent(_queue);
		}
		return queueEntryRO;
	}
	
	/**
	 * Returns the QueueEntry that's at the front of the queue.
	 * 
	 * @return null if the Queue's list of QueueEntries is empty.
	 * @see #popQueueEntry(CalendarEventRO)
	 */
	public QueueEntryRO peekQueueEntry() {
		return _queue.peek();
	}
	
	/**
	 * 
	 * @param patient
	 * @return true if the patient was in the queue and was removed successfully
	 */
	public boolean removeQueueEntry(PatientRO patient) {
		if(_queue.removeQueueEntry((Patient) patient)) {
			return QueueDao.getInstance().makePersistent(_queue);
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param queueEntryRO the QueueEntryRO that should be moved. Must currently be located in the QueueControllers Queue!
	 * @param moveUp if true, the queueEntryRO moves 1 step up (forward, towards the start). If false, the queueEntryRO is moved 1 step "down", toward the end of the queue.
	 * @return true if the QueueEntry was successfully moved and this change was saved to the database.
	 */
	public boolean moveQueueEntry(QueueEntryRO queueEntryRO, boolean moveUp) {
		if(_queue.move(queueEntryRO.getQueueEntryId(), moveUp)) {
			return QueueDao.getInstance().makePersistent(_queue);
		}
		return false;
	}
	
	/**
	 * TODO
	 * 
	 * @param patientRO
	 * @return
	 */
	public boolean isPatientInQueue(PatientRO patientRO) {
		return _queue.containsPatient(patientRO.getPatientId());
	}
	
	/**
	 * 
	 * @param queueRO
	 * @return
	 */
	public boolean representsSameQueueByID(QueueRO queueRO) {
		return _queue.representsSameQueueByID((Queue) queueRO);
	}
	
	/**
	 * Reloads the queue from the database
	 */
	public void reloadQueue() {
		_queue = QueueDao.getInstance().findById(_queue.getDoctor() == null ? null : _queue.getDoctor().getDoctorId(), _queue.getOrthoptist() == null ? null : _queue.getOrthoptist().getOrthoptistId());
	}
	
	/**
	 * Clears the queue: All calendarEvents are removed. Additionally, this new state is saved to the database.
	 * @return true if this change was successfully saved to the database.
	 */
	public boolean clearQueue() {
		_queue.clear();
		return QueueDao.getInstance().makePersistent(_queue);
	}
	
	/**
	 * 
	 * @return true if the queue is empty, i.e. does not contain any calendarevents.
	 */
	public boolean isQueueEmpty() {
		return _queue.isEmpty();
	}
}
