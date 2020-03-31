package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mateuszhaberla.recruitmenttaskrsq.mapper.AppointmentMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.AppointmentDTO
import com.mateuszhaberla.recruitmenttaskrsq.repository.AppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class AppointmentCrudService @Autowired constructor(
        private val appointmentRepository: AppointmentRepository,
        private val appointmentMapper: AppointmentMapper,
        private val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
) {

    fun create(appointment: Appointment): Appointment {
        return appointmentRepository.save(appointment)
    }

    fun update(appointmentDTO: AppointmentDTO): Optional<Appointment> {
        val appointmentToUpdate: Appointment = appointmentMapper.mapDtoToAppointment(appointmentDTO)
        return appointmentRepository.findById(appointmentToUpdate.id)
                .map { appointmentRepository.save(appointmentToUpdate) }
    }

    fun delete(id: Long): Boolean {
        return if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}