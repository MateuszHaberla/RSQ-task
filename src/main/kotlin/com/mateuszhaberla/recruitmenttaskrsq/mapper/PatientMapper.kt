package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import org.springframework.stereotype.Service

@Service
class PatientMapper {
     fun mapPatientToDto(patient: Patient) : PatientDto {
         return PatientDto(id = patient.id,
                 name = patient.name,
                 surname = patient.surname,
                 address = patient.address)
     }

    fun mapDtoToPatient(patientDto: PatientDto) : Patient {
        return Patient(id = patientDto.id,
                name = patientDto.name,
                surname = patientDto.surname,
                address = patientDto.address)
    }
}