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
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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

    val currentTime = LocalDateTime.now()
    val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
    val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
    val appointment = Appointment(1, currentTime, "Poznań", doctor, patient)
    val appointmentDto = AppointmentDto(1, currentTime, "Poznań", doctor, patient)

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
        `when`(appointmentMapper.mapDtoToAppointment(appointmentDto))
                .thenReturn(appointment)

        `when`(appointmentRepository.save(any(Appointment::class.java)))
                .thenReturn(appointment)

        //when
        val createdAppointment = appointmentCrudService.create(appointmentDto)

        //then
        assertTrue(appointmentDto == createdAppointment)
        verify(appointmentRepository).save(any(Appointment::class.java))
        verify(appointmentMapper).mapDtoToAppointment(appointmentDto)
    }

    @Test
    fun read() {
        //given
        val allAppointments = mutableListOf(appointment)
        `when`(appointmentRepository.findByPatientId(1))
                .thenReturn(allAppointments)

        `when`(appointmentMapper.mapAppointmentToDto(appointment))
                .thenReturn(appointmentDto)

        //when
        val appointmentsDto = appointmentCrudService.read(1)

        //then
        assertNotNull(appointmentsDto)
        verify(appointmentRepository).findByPatientId(any())
        verify(appointmentMapper).mapAppointmentToDto(appointment)
    }

    @Test
    fun readAll() {
        //given
        val allAppointments = mutableListOf(appointment)
        `when`(appointmentRepository.findAll())
                .thenReturn(allAppointments)

        `when`(appointmentMapper.mapAppointmentToDto(appointment))
                .thenReturn(appointmentDto)

        //when
        val appointmentsDto = appointmentCrudService.readAll()
        val expectedPatients = mutableListOf(appointmentDto)

        //then
        assertNotNull(appointmentsDto)
        assertEquals(expectedPatients, appointmentsDto)
        verify(appointmentRepository).findAll()
        verify(appointmentMapper).mapAppointmentToDto(appointment)
    }

    @Test
    fun update() {
        //given
        `when`(appointmentMapper.mapDtoToAppointment(appointmentDto))
                .thenReturn(appointment)

        `when`(appointmentRepository.findById(1))
                .thenReturn(Optional.of(appointment))

        `when`(appointmentRepository.save(any(Appointment::class.java)))
                .thenReturn(appointment)

        `when`(appointmentMapper.mapAppointmentToDto(appointment))
                .thenReturn(appointmentDto)

        //when
        val updatedAppointmentDto = appointmentCrudService.update(appointmentDto)
        val expectedResult = Optional.of(appointmentDto)

        //then
        assertTrue(expectedResult == updatedAppointmentDto)

        verify(appointmentMapper).mapDtoToAppointment(appointmentDto)
        verify(appointmentRepository).findById(any())
        verify(appointmentRepository).save(any(Appointment::class.java))
        verify(appointmentMapper).mapAppointmentToDto(appointment)
    }

    @Test
    fun deleteWhenIdExist() {
        //given
        `when`(appointmentRepository.existsById(1))
                .thenReturn(true)

        //when
        val exists = appointmentCrudService.delete(1)

        //then
        assertTrue(exists)
        verify(appointmentRepository).existsById(any())
    }

    @Test
    fun deleteWhenIdDoesNotExist() {
        //given
        `when`(appointmentRepository.existsById(2))
                .thenReturn(false)

        //when
        val exists = appointmentCrudService.delete(2)

        //then
        assertTrue(!exists)
        verify(appointmentRepository).existsById(any())
    }

    @Test
    fun patch() {
        //given
        val optionalOfAppointment = Optional.of(appointment)
        `when`(appointmentRepository.findById(1))
                .thenReturn(optionalOfAppointment)

        val immutableMapOfAppointment = mapOf("id" to 1, "timeOfAppointment" to currentTime, "officeAddress" to "Poznań", "doctor" to doctor, "patient" to patient)
        `when`(objectMapper.convertValue(any(Appointment::class.java), eq(Map::class.java)))
                .thenReturn(immutableMapOfAppointment)

        val appointmentAfterPatch = Appointment(1, currentTime.plusHours(1), "Wrocław", doctor, patient)
        `when`(objectMapper.convertValue(any(LinkedHashMap::class.java), eq(Appointment::class.java)))
                .thenReturn(appointmentAfterPatch)

        `when`(appointmentRepository.save(appointmentAfterPatch))
                .thenReturn(appointmentAfterPatch)

        val appointmentDtoAfterPatch = AppointmentDto(1, currentTime.plusHours(1), "Wrocław", doctor, patient)
        `when`(appointmentMapper.mapAppointmentToDto(appointmentAfterPatch))
                .thenReturn(appointmentDtoAfterPatch)

        val mapOfChanges = hashMapOf("timeOfAppointment" to currentTime.plusHours(1).toString(), "address" to "Wrocław")

        //when
        val patchedAppointmentDto: Optional<AppointmentDto> = appointmentCrudService.patch(1, mapOfChanges)
        val expectedResult = Optional.of(appointmentDtoAfterPatch)

        //then
        assertEquals(expectedResult, patchedAppointmentDto)

        verify(appointmentRepository).findById(any())
        verify(appointmentRepository).save(any())
        verify(objectMapper).convertValue(any(Appointment::class.java), eq(Map::class.java))
        verify(objectMapper).convertValue(any(LinkedHashMap::class.java), eq(Appointment::class.java))
        verify(appointmentMapper).mapAppointmentToDto(appointmentAfterPatch)
    }
}