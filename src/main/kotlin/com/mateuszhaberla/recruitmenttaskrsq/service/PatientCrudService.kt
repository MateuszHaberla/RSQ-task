package com.mateuszhaberla.recruitmenttaskrsq.service

import com.mateuszhaberla.recruitmenttaskrsq.mapper.PatientMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.PatientDTO
import com.mateuszhaberla.recruitmenttaskrsq.repository.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PatientCrudService @Autowired constructor(
        private val patientRepository: PatientRepository,
        private val patientMapper: PatientMapper
) {

    fun create(patient: Patient): Patient {
        return patientRepository.save(patient)
    }

    fun read(id: Long): Optional<Patient> {
        return patientRepository.findById(id)
    }

    fun update(patientDto: PatientDTO): Optional<Patient> {
        val patientToUpdate: Patient = patientMapper.mapDtoToPatient(patientDto)
        return patientRepository.findById(patientToUpdate.id)
                .map { patientRepository.save(patientToUpdate) }
    }

    fun delete(id: Long): Boolean {
        return if(patientRepository.existsById(id)) {
            patientRepository.deleteById(id)
            true
        }
        else { false }
    }
}

