package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.AppointmentMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import com.mateuszhaberla.recruitmenttaskrsq.repository.AppointmentRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.Optional


@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class AppointmentCrudServiceTest {

    private lateinit var appointmentMapper: AppointmentMapper
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var appointmentCrudService: AppointmentCrudService
    private lateinit var objectMapper: ObjectMapper

    val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
    val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
    val appointment = Appointment(1, LocalDateTime.now(), "Poznań", doctor, patient)
    val appointmentDto = AppointmentDto(1, LocalDateTime.now(), "Poznań", doctor, patient)

    @BeforeEach
    fun setUp() {

        appointmentMapper = mock(AppointmentMapper::class.java)
        appointmentRepository = mock(AppointmentRepository::class.java)
        objectMapper = mock(ObjectMapper::class.java)

        appointmentCrudService = AppointmentCrudService(appointmentRepository, appointmentMapper, objectMapper)
    }

    @Test
    fun create() {
        //given
        Mockito.`when`(appointmentRepository.save(Mockito.any(Appointment::class.java)))
                .thenReturn(appointment)

        //when
        val createdAppointment = appointmentCrudService.create(appointment)

        //then
        assertTrue(appointment == createdAppointment)
        Mockito.verify(appointmentRepository).save(Mockito.any(Appointment::class.java))
    }

    @Test
    fun read() {
        //given
        val expectedAppointments = mutableListOf(appointment)
        Mockito.`when`(appointmentRepository.findByPatientId(1))
                .thenReturn(expectedAppointments)

        //when
        val appointment = appointmentCrudService.read(1)

        //then
        assertNotNull(appointment)
        Mockito.verify(appointmentRepository).findByPatientId(Mockito.any())
    }

    @Test
    fun readAll() {
        //given
        val expectedAppointments = mutableListOf(appointment)
        Mockito.`when`(appointmentRepository.findAll())
                .thenReturn(expectedAppointments)

        //when
        val appointment = appointmentCrudService.readAll()

        //then
        assertNotNull(appointment)
        Mockito.verify(appointmentRepository).findAll()
    }

    @Test
    fun update() {
        //given
        Mockito.`when`(appointmentMapper.mapDtoToAppointment(appointmentDto))
                .thenReturn(appointment)

        Mockito.`when`(appointmentRepository.findById(1))
                .thenReturn(Optional.of(appointment))

        Mockito.`when`(appointmentRepository.save(Mockito.any(Appointment::class.java)))
                .thenReturn(appointment)

        //when
        val updatedAppointment = appointmentCrudService.update(appointmentDto)
        val appoitment = Optional.of(appointment)
        //then
        assertTrue(appoitment == updatedAppointment)

        Mockito.verify(appointmentMapper).mapDtoToAppointment(appointmentDto)
        Mockito.verify(appointmentRepository).findById(Mockito.any())
        Mockito.verify(appointmentRepository).save(Mockito.any(Appointment::class.java))
    }

    @Test
    fun deleteWhenIdExist() {
        //given
        Mockito.`when`(appointmentRepository.existsById(1))
                .thenReturn(true)

        //when
        val exists = appointmentCrudService.delete(1)

        //then
        assertTrue(exists)
        Mockito.verify(appointmentRepository).existsById(ArgumentMatchers.any())
    }

    @Test
    fun deleteWhenIdDoesNotExist() {
        //given
        Mockito.`when`(appointmentRepository.existsById(2))
                .thenReturn(false)

        //when
        val exists = appointmentCrudService.delete(2)

        //then
        assertTrue(!exists)
        Mockito.verify(appointmentRepository).existsById(ArgumentMatchers.any())
    }

    @Test
    fun patch() {
        //given
        val currentTime = LocalDateTime.now()
        val optionalOfAppointment = Optional.of(appointment)
        Mockito.`when`(appointmentRepository.findById(1))
                .thenReturn(optionalOfAppointment)

        val immutableMapOfAppointment = mapOf("id" to 1, "timeOfAppointment" to currentTime, "officeAddress" to "Poznań", "doctor" to doctor, "patient" to patient)
        Mockito.`when`(objectMapper.convertValue(Mockito.any(Optional::class.java), ArgumentMatchers.eq(Map::class.java)))
                .thenReturn(immutableMapOfAppointment)

        val appoitemtnaAfterPatch = Appointment(1, currentTime, "Wrocław", doctor, patient)
        Mockito.`when`(objectMapper.convertValue(Mockito.any(LinkedHashMap::class.java), ArgumentMatchers.eq(Appointment::class.java)))
                .thenReturn(appoitemtnaAfterPatch)

        Mockito.`when`(appointmentRepository.save(appoitemtnaAfterPatch))
                .thenReturn(appoitemtnaAfterPatch)

        val mapOfChanges = hashMapOf("timeOfAppointment" to currentTime.plusHours(1).toString(), "address" to "Wrocław")

        //when
        val patchedAppointment = appointmentCrudService.patch(1, mapOfChanges)
        val expectedResult = Optional.of(appoitemtnaAfterPatch)

        //then
        assertEquals(expectedResult, patchedAppointment)

        Mockito.verify(appointmentRepository).findById(Mockito.any())
        Mockito.verify(appointmentRepository).save(Mockito.any())
        Mockito.verify(objectMapper).convertValue(Mockito.any(Optional::class.java), ArgumentMatchers.eq(Map::class.java))
        Mockito.verify(objectMapper).convertValue(Mockito.any(LinkedHashMap::class.java), ArgumentMatchers.eq(Appointment::class.java))
    }
}