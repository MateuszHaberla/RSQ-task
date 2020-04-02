package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import org.springframework.stereotype.Service

@Service
class AppointmentMapper {
    fun mapAppointmentToDto(appointment: Appointment) : AppointmentDto {
        return AppointmentDto(id = appointment.id,
                timeOfAppointment = appointment.timeOfAppointment,
                officeAddress = appointment.officeAddress,
                doctor = appointment.doctor,
                patient = appointment.patient)
    }

    fun mapDtoToAppointment(appointmentDto: AppointmentDto) : Appointment {
        return Appointment(id = appointmentDto.id,
                timeOfAppointment = appointmentDto.timeOfAppointment,
                officeAddress = appointmentDto.officeAddress,
                doctor = appointmentDto.doctor,
                patient = appointmentDto.patient)
    }
}