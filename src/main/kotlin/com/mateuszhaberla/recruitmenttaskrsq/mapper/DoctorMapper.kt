package com.mateuszhaberla.recruitmenttaskrsq.mapper


import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import org.springframework.stereotype.Service

@Service
class DoctorMapper {
    fun mapDoctorToDto(doctor: Doctor) : DoctorDto {
        return DoctorDto(id = doctor.id,
                name = doctor.name,
                surname = doctor.surname,
                specialization = doctor.specialization)
    }

    fun mapDtoToDoctor(doctorDto: DoctorDto) : Doctor {
        return Doctor(id = doctorDto.id,
                name = doctorDto.name,
                surname = doctorDto.surname,
                specialization = doctorDto.specialization)
    }
}