/*
 * Copyright (c) 2015 Team F
 *
 * This file is part of Oculus.
 * Oculus is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Oculus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Oculus.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.oculus.teamf.domain.entity.factory;


import at.oculus.teamf.domain.entity.interfaces.*;

import java.util.Date;

/**
 * Created by Simon Angerer on 13.05.2015.
 */
public interface IFactoryTB2 {

    IVisualAid createVisualAid();

    IDiagnosis createDiagnos(String title, String description, IDoctor doctor);

    IPrescription createPrescription();

    IPrescriptionEntry createPrescriptionEntry();

    IExaminationProtocol createExaminationProtocol(Integer id, Date startTime, Date endTime, String description, IPatient patient, IDoctor doctor,
                                                   IOrthoptist orthoptist, IDiagnosis diagnosis);
}
