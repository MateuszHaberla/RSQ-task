package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.config.Config
import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.PatientMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.repository.PatientRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.stream.Collectors

@Service
class PatientCrudService(
        private val patientRepository: PatientRepository,
        private val patientMapper: PatientMapper,
        private val mapper: ObjectMapper = Config().getObjectMapper()
) {

    fun create(patientDto: PatientDto): PatientDto {
        val patient = patientMapper.mapDtoToPatient(patientDto)
        patientRepository.save(patient)

        return patientDto
    }

    fun read(id: Long): Optional<PatientDto> {
        val optionalOfPatient: Optional<Patient> = patientRepository.findById(id)

        return optionalOfPatient.map { patientMapper.mapPatientToDto(it) }
    }

    fun readAll(): MutableList<PatientDto> {
        val allPatients = patientRepository.findAll()

        return allPatients.stream()
                .map { patientMapper.mapPatientToDto(it) }
                .collect(Collectors.toList())
    }

    fun update(patientDto: PatientDto): Optional<PatientDto> {
        val patientToUpdate: Patient = patientMapper.mapDtoToPatient(patientDto)

        return patientRepository.findById(patientToUpdate.id)
                .map { patientRepository.save(patientToUpdate) }
                .map { patientMapper.mapPatientToDto(it) }
    }

    fun delete(id: Long): Boolean {
        return if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun patch(id: Long, patientChangesMap: HashMap<String, String>): Optional<PatientDto> {
        return patientRepository.findById(id)
                .map { patient -> mapper.convertValue(patient, Map::class.java) }
                .map { patientToUpdateMap -> patientToUpdateMap.toMutableMap() }
                .map { patientToUpdateMap ->
                    patientChangesMap.forEach { patientToUpdateMap[it.key] = it.value }
                    patientToUpdateMap
                }
                .map { patientToUpdateMap -> mapper.convertValue(patientToUpdateMap, Patient::class.java) }
                .map { patientRepository.save(it) }
                .map { patientMapper.mapPatientToDto(it) }
    }
}

