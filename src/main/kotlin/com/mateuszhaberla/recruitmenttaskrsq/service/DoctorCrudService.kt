package com.mateuszhaberla.recruitmenttaskrsq.service


import com.mateuszhaberla.recruitmenttaskrsq.mapper.DoctorMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.DoctorDTO
import com.mateuszhaberla.recruitmenttaskrsq.repository.DoctorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class DoctorCrudService @Autowired constructor(
        private val doctorMapper: DoctorMapper,
        private val doctorRepository: DoctorRepository
) {
    fun create(doctor: Doctor): Doctor {
        return doctorRepository.save(doctor)
    }

    fun read(id: Long): Optional<Doctor> {
        return doctorRepository.findById(id)
    }

    fun update(doctorDto: DoctorDTO): Optional<Doctor> {
        val patientToUpdate: Doctor = doctorMapper.mapDtoToDoctor(doctorDto)
        return doctorRepository.findById(patientToUpdate.id)
                .map { doctorRepository.save(patientToUpdate) }
    }

    fun delete(id: Long): Boolean {
        return if(doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id)
            true
        }
        else { false }
    }
}