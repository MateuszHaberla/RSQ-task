package com.mateuszhaberla.recruitmenttaskrsq.service


import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.config.Config
import com.mateuszhaberla.recruitmenttaskrsq.mapper.DoctorMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import com.mateuszhaberla.recruitmenttaskrsq.repository.DoctorRepository
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class DoctorCrudService(
        private val doctorMapper: DoctorMapper,
        private val doctorRepository: DoctorRepository,
        private val mapper: ObjectMapper = Config().getObjectMapper()
) {
    fun create(doctor: Doctor): Doctor {
        return doctorRepository.save(doctor)
    }

    fun read(id: Long): Optional<Doctor> {
        return doctorRepository.findById(id)
    }

    fun update(doctorDto: DoctorDto): Optional<Doctor> {
        val patientToUpdate: Doctor = doctorMapper.mapDtoToDoctor(doctorDto)
        return doctorRepository.findById(patientToUpdate.id)
                .map { doctorRepository.save(patientToUpdate) }
    }

    fun delete(id: Long): Boolean {
        return if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun patch(id: Long, doctorChangesMap: HashMap<String, String>): Optional<Doctor> {
        return Optional.of(doctorRepository.findById(id))
                .map { doctor -> mapper.convertValue(doctor, Map::class.java) }
                .map { doctorToUpdateMap -> doctorToUpdateMap.toMutableMap() }
                .map { doctorToUpdateMap ->
                    doctorChangesMap.forEach { doctorToUpdateMap[it.key] = it.value }
                    doctorToUpdateMap
                }
                .map { doctorToUpdateMap -> mapper.convertValue(doctorToUpdateMap, Doctor::class.java) }
                .map { doctorRepository.save(it) }
    }
}