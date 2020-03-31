package com.mateuszhaberla.recruitmenttaskrsq.mapper


import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.DoctorDTO
import org.springframework.stereotype.Service

@Service
class DoctorMapper {
    fun mapDoctorToDto(doctor: Doctor) : DoctorDTO {
        return DoctorDTO(id = doctor.id,
                name = doctor.name,
                surname = doctor.surname,
                specialization = doctor.specialization)
    }

    fun mapDtoToDoctor(doctorDTO: DoctorDTO) : Doctor {
        return Doctor(id = doctorDTO.id,
                name = doctorDTO.name,
                surname = doctorDTO.surname,
                specialization = doctorDTO.specialization)
    }
}