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
import java.time.LocalDateTime
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

    fun read(id: Long?): MutableList<Appointment> {
        return appointmentRepository.findByPatient(id)
    }

    fun readAll(): MutableList<Appointment> {
        return appointmentRepository.findAll()
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

    fun patch(id: Long, appointmentChangesMap: HashMap<String, String>): Optional<Appointment> {
        return Optional.of(appointmentRepository.findById(id))
                .map { appointment -> mapper.convertValue(appointment, Map::class.java) }
                .map { appointmentToUpdateMap -> appointmentToUpdateMap.toMutableMap() }
                .map { appointmentToUpdateMap ->
                    appointmentChangesMap.forEach { appointmentToUpdateMap[it.key] = it.value }
                    appointmentToUpdateMap
                }
                .map { appointmentToUpdateMap -> mapper.convertValue(appointmentToUpdateMap, Appointment::class.java) }
                .map { appointmentRepository.save(it) }
    }

    fun changeTimeOfAppointment(id: Long, date: LocalDateTime): Optional<Appointment> {
        val map = hashMapOf("date" to date.toString())
        return patch(id, map)
    }
}