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
    fun create(doctorDto: DoctorDto): DoctorDto {
        val doctor = doctorMapper.mapDtoToDoctor(doctorDto)
        doctorRepository.save(doctor)

        return doctorDto
    }

    fun read(id: Long): Optional<DoctorDto> {
        val optionalOfDoctor: Optional<Doctor> = doctorRepository.findById(id)

        return optionalOfDoctor.map { doctorMapper.mapDoctorToDto(it) }
    }

    fun update(doctorDto: DoctorDto): Optional<DoctorDto> {
        val patientToUpdate: Doctor = doctorMapper.mapDtoToDoctor(doctorDto)

        return doctorRepository.findById(patientToUpdate.id)
                .map { doctorRepository.save(patientToUpdate) }
                .map { doctorMapper.mapDoctorToDto(it) }
    }

    fun delete(id: Long): Boolean {
        return if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun patch(id: Long, doctorChangesMap: HashMap<String, String>): Optional<DoctorDto> {
        return doctorRepository.findById(id)
                .map { doctor -> mapper.convertValue(doctor, Map::class.java) }
                .map { doctorToUpdateMap -> doctorToUpdateMap.toMutableMap() }
                .map { doctorToUpdateMap ->
                    doctorChangesMap.forEach { doctorToUpdateMap[it.key] = it.value }
                    doctorToUpdateMap
                }
                .map { doctorToUpdateMap -> mapper.convertValue(doctorToUpdateMap, Doctor::class.java) }
                .map { doctorRepository.save(it) }
                .map { doctorMapper.mapDoctorToDto(it) }
    }
}