/*
 * Copyright (c) 2015 Team F
 *
 * This file is part of Oculus.
 * Oculus is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Oculus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Oculus.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.oculus.teamf.domain.entity.interfaces;

import at.oculus.teamf.databaseconnection.session.exception.BadSessionException;
import at.oculus.teamf.databaseconnection.session.exception.ClassNotMappedException;
import at.oculus.teamf.domain.entity.exception.CantLoadPatientsException;
import at.oculus.teamf.persistence.exception.BadConnectionException;
import at.oculus.teamf.persistence.exception.DatabaseOperationException;
import at.oculus.teamf.persistence.exception.NoBrokerMappedException;
import at.oculus.teamf.persistence.exception.reload.InvalidReloadClassException;
import at.oculus.teamf.persistence.exception.reload.ReloadInterfaceNotImplementedException;

import java.util.Collection;

public interface IDoctor extends IUser, IDomain {
    //<editor-fold desc="Getter/Setter">
    int getId();

    void setId(int id);

    ICalendar getCalendar();

    void setCalendar(ICalendar _calendar);

    IPatientQueue getQueue();

    void setQueue(IPatientQueue _queue);

    IDoctor getDoctorSubstitude();

    void setDoctorSubstitude(IDoctor doctorSubstitude);

    void addPatient(IPatient patient);

    Collection<IPatient> getPatients() throws CantLoadPatientsException;

    void setPatients(Collection<IPatient> patients);

    @Override
    String toString();
}
