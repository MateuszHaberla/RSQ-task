package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.AppointmentDTO
import org.springframework.stereotype.Service

@Service
class AppointmentMapper {
    fun mapAppointmentToDto(appointment: Appointment) : AppointmentDTO {
        return AppointmentDTO(id = appointment.id,
                date = appointment.date,
                hour = appointment.hour,
                place = appointment.place,
                doctor = appointment.doctor,
                patient = appointment.patient)
    }

    fun mapDtoToAppointment(appointmentDTO: AppointmentDTO) : Appointment {
        return Appointment(id = appointmentDTO.id,
                date = appointmentDTO.date,
                hour = appointmentDTO.hour,
                place = appointmentDTO.place,
                doctor = appointmentDTO.doctor,
                patient = appointmentDTO.patient)
    }
}