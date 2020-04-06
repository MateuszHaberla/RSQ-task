package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.config.Config
import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.AppointmentMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.repository.AppointmentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import java.util.stream.Collectors


@Service
class AppointmentCrudService(
        private val appointmentRepository: AppointmentRepository,
        private val appointmentMapper: AppointmentMapper,
        private val mapper: ObjectMapper = Config().getObjectMapper()
) {

    fun create(appointmentDto: AppointmentDto): AppointmentDto {
        val appointment: Appointment = appointmentMapper.mapDtoToAppointment(appointmentDto)
        appointmentRepository.save(appointment)

        return appointmentDto
    }

    fun read(id: Long?): MutableList<AppointmentDto> {
        val appointments: MutableList<Appointment> = appointmentRepository.findByPatientId(id)

        return appointments.stream()
                .map { appointmentMapper.mapAppointmentToDto(it) }
                .collect(Collectors.toList())
    }

    fun readAll(): MutableList<AppointmentDto> {
        val appointments: MutableList<Appointment> = appointmentRepository.findAll()

        return appointments.stream()
                .map { appointmentMapper.mapAppointmentToDto(it) }
                .collect(Collectors.toList())
    }

    fun update(appointmentDto: AppointmentDto): Optional<AppointmentDto> {
        val appointmentToUpdate: Appointment = appointmentMapper.mapDtoToAppointment(appointmentDto)

        return appointmentRepository.findById(appointmentToUpdate.id)
                .map { appointmentRepository.save(appointmentToUpdate) }
                .map { appointmentMapper.mapAppointmentToDto(it) }
    }

    fun delete(id: Long): Boolean {
        return if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun patch(id: Long, appointmentChangesMap: HashMap<String, String>): Optional<AppointmentDto> {
        return appointmentRepository.findById(id)
                .map { appointment -> mapper.convertValue(appointment, Map::class.java) }
                .map { appointmentToUpdateMap -> appointmentToUpdateMap.toMutableMap() }
                .map { appointmentToUpdateMap ->
                    appointmentChangesMap.forEach { appointmentToUpdateMap[it.key] = it.value }
                    appointmentToUpdateMap
                }
                .map { appointmentToUpdateMap -> mapper.convertValue(appointmentToUpdateMap, Appointment::class.java) }
                .map { appointmentRepository.save(it) }
                .map { appointmentMapper.mapAppointmentToDto(it) }
    }

    fun changeTimeOfAppointment(id: Long, date: LocalDateTime): Optional<AppointmentDto> {
        val map = hashMapOf("timeOfAppointment" to date.toString())
        return patch(id, map)
    }
}