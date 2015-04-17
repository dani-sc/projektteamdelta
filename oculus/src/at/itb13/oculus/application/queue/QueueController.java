package at.itb13.oculus.application.queue;

import java.time.LocalDateTime;
import java.util.List;

import at.itb13.oculus.domain.Patient;
import at.itb13.oculus.domain.Queue;
import at.itb13.oculus.domain.QueueEntry;
import at.itb13.oculus.domain.readonlyinterfaces.CalendarEventRO;
import at.itb13.oculus.domain.readonlyinterfaces.PatientRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueEntryRO;
import at.itb13.oculus.domain.readonlyinterfaces.QueueRO;
import at.itb13.oculus.technicalServices.dao.PatientDao;
import at.itb13.oculus.technicalServices.dao.QueueDao;

/**
 * TODO: Insert description here.
 * 
 * @author Daniel Scheffknecht
 * @date 14.04.2015
 */
public class QueueController {
	private Queue _queue;
	
	public QueueController(Queue queue) {	// TODO: sichtbarkeit einschränken, wenn möglich
		_queue = queue;
	}
	
	public QueueRO getQueue() {
		return _queue;
	}
	
	public List<? extends QueueEntryRO> getQueueEntries() {
		return _queue.getQueueEntries();
	}
	
	/**
	 * Created QueueEntry is not persisted! Can only be persisted by persisting its Queue.
	 * @param patientRO
	 * @param arrivalTime
	 * @return
	 */
	public QueueEntryRO createQueueEntry(PatientRO patientRO, LocalDateTime arrivalTime) {
		Patient patient = PatientDao.getInstance().findById(patientRO.getPatientId());
		
		QueueEntry queueEntry = new QueueEntry();
		queueEntry.setPatient(patient);
		queueEntry.setArrivalTime(arrivalTime);
		
		return queueEntry;
	}
	
	/**
	 * Inserts queueEntryRO to the last position in the Queue's QueueEntryList
	 * @param queueEntryRO
	 * @return true if the queueEntry was successfully added to the Queue and saved in the database
	 */
	public boolean pushQueueEntry(QueueEntryRO queueEntryRO) {
		QueueEntry queueEntry = (QueueEntry) queueEntryRO;
		_queue.pushQueueEntry(queueEntry);
		return QueueDao.getInstance().makePersistent(_queue);
	}
	
	public boolean pushQueueEntry(PatientRO patientRO) {
		QueueEntry queueEntry = new QueueEntry();
		queueEntry.setPatient((Patient) patientRO);
		queueEntry.setArrivalTime(LocalDateTime.now());
		return pushQueueEntry(queueEntry);
	}
	
	/**
	 * 
	 * @param calendarEventRo the CalendarEventRO which status will be updated if necessary
	 * @return null if empty QueueEntryList
	 */
	public QueueEntryRO popQueueEntry(CalendarEventRO calendarEventRO) {
		// TODO: change status of calendarEvent concerning queue (if in generalQueue then not yet)
		return _queue.popQueueEntry();
	}
	
	/**
	 * 
	 * @return null if empty QueueEntryList
	 */
	public QueueEntryRO peekQueueEntry() {
		return _queue.peek();
	}
}
