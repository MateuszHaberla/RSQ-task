package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.PatientDTO
import org.springframework.stereotype.Service

@Service
class PatientMapper {
     fun mapPatientToDto(patient: Patient) : PatientDTO {
         return PatientDTO(id = patient.id,
                  name = patient.name,
                  surname = patient.surname,
                  address = patient.address)
     }

    fun mapDtoToPatient(patientDTO: PatientDTO) : Patient {
        return Patient(id = patientDTO.id,
                name = patientDTO.name,
                surname = patientDTO.surname,
                address = patientDTO.address)
    }
}