package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper

import com.mateuszhaberla.recruitmenttaskrsq.config.Config
import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.PatientMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.repository.PatientRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PatientCrudService(
        private val patientRepository: PatientRepository,
        private val patientMapper: PatientMapper,
        private val mapper: ObjectMapper = Config().getObjectMapper()
) {

    fun create(patient: Patient): Patient {
        return patientRepository.save(patient)
    }

    fun read(id: Long): Optional<Patient> {
        return patientRepository.findById(id)
    }

    fun readAll(): MutableList<Patient> {
        return patientRepository.findAll()
    }

    fun update(patientDto: PatientDto): Optional<Patient> {
        val patientToUpdate: Patient = patientMapper.mapDtoToPatient(patientDto)
        return patientRepository.findById(patientToUpdate.id)
                .map { patientRepository.save(patientToUpdate) }
    }

    fun delete(id: Long): Boolean {
        return if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id)
            true
        } else { false }
    }

    fun patch(id: Long, patientChangesMap: HashMap<String, String>): Optional<Patient> {
        return Optional.of(patientRepository.findById(id))
                .map { patient -> mapper.convertValue(patient, Map::class.java) }
                .map { patientToUpdateMap -> patientToUpdateMap.toMutableMap() }
                .map { patientToUpdateMap ->
                    patientChangesMap.forEach { patientToUpdateMap[it.key] = it.value }
                    patientToUpdateMap
                }
                .map { patientToUpdateMap -> mapper.convertValue(patientToUpdateMap, Patient::class.java) }
                .map { patientRepository.save(it) }
    }
}

