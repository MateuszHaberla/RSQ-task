package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
        private val patientMapper: PatientMapper,
        private val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
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

    fun patch(id:Long, patientChangesMap: HashMap<String, String>): Optional<Patient> {
        return Optional.of(patientRepository.findById(id))
                .map { patient -> mapper.convertValue(patient, Map::class.java) }
                .map { patientToUpdateMap -> patientToUpdateMap.toMutableMap() }
                .map { patientToUpdateMap ->
                    patientChangesMap.forEach{ patientToUpdateMap[it.key] = it.value }
                    patientToUpdateMap
                }
                .map { patientToUpdateMap -> mapper.convertValue(patientToUpdateMap, Patient::class.java) }
                .map { patientRepository.save(it) }
    }
}

