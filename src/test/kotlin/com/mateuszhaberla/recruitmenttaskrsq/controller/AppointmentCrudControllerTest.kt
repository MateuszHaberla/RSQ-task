package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import com.mateuszhaberla.recruitmenttaskrsq.service.AppointmentCrudService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.Optional

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class AppointmentCrudControllerTest {

    private lateinit var appointmentCrudService: AppointmentCrudService
    private lateinit var appointmentCrudController: AppointmentCrudController

    private val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
    private val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
    private val appointment = Appointment(1, LocalDateTime.now(), "Poznań", doctor, patient)
    private val appointmentDto = AppointmentDto(1, LocalDateTime.now(), "Poznań", doctor, patient)


    @BeforeEach
    fun setUp() {
        appointmentCrudService = Mockito.mock(AppointmentCrudService::class.java)

        appointmentCrudController = AppointmentCrudController(appointmentCrudService)
    }

    @Test
    fun create() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(appointment)

        Mockito.`when`(appointmentCrudService.create(appointment))
                .thenReturn(appointment)

        //when
        val createdAppointmentResponse = appointmentCrudController.create(appointment)

        //then
        assertEquals(expectedResult, createdAppointmentResponse)
        Mockito.verify(appointmentCrudService).create(appointment)
    }

    @Test
    fun readAllOrOneWhenIdGivenAndAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(mutableListOf(appointment))

        Mockito.`when`(appointmentCrudService.read(1))
                .thenReturn(mutableListOf(appointment))
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(1)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        Mockito.verify(appointmentCrudService).read(1)
    }

    @Test
    fun readAllOrOneWhenIdGivenAndAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<Appointment>()

        Mockito.`when`(appointmentCrudService.read(2))
                .thenReturn(mutableListOf())

        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(2)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        Mockito.verify(appointmentCrudService).read(2)
    }

    @Test
    fun readAllOrOneWhenIdNotGivenAndAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(mutableListOf(appointment))

        Mockito.`when`(appointmentCrudService.readAll())
                .thenReturn(mutableListOf(appointment))
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(null)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        Mockito.verify(appointmentCrudService).readAll()
    }

    @Test
    fun readAllOrOneWhenIdNotGivenAndAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<Appointment>()

        Mockito.`when`(appointmentCrudService.readAll())
                .thenReturn(mutableListOf())
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(null)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        Mockito.verify(appointmentCrudService).readAll()
    }


    @Test
    fun updateWhenAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(appointment)

        Mockito.`when`(appointmentCrudService.update(appointmentDto))
                .thenReturn(Optional.of(appointment))
        //when
        val updateAppointmentResponse = appointmentCrudController.update(appointmentDto)

        //then
        assertEquals(expectedResult, updateAppointmentResponse)
        Mockito.verify(appointmentCrudService).update(appointmentDto)
    }

    @Test
    fun updateWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<Appointment>()

        Mockito.`when`(appointmentCrudService.update(appointmentDto))
                .thenReturn(Optional.empty())
        //when
        val updateAppointmentResponse = appointmentCrudController.update(appointmentDto)

        //then
        assertEquals(expectedResult, updateAppointmentResponse)
        Mockito.verify(appointmentCrudService).update(appointmentDto)
    }

    @Test
    fun deleteWhenAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).build<Appointment>()

        Mockito.`when`(appointmentCrudService.delete(1))
                .thenReturn(true)

        //when
        val deleteAppointmentResponse = appointmentCrudController.delete(1)

        //then
        assertEquals(expectedResult, deleteAppointmentResponse)
        Mockito.verify(appointmentCrudService).delete(1)
    }

    @Test
    fun deleteWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<Appointment>()

        Mockito.`when`(appointmentCrudService.delete(2))
                .thenReturn(false)

        //when
        val deleteAppointmentResponse = appointmentCrudController.delete(2)

        //then
        assertEquals(expectedResult, deleteAppointmentResponse)
        Mockito.verify(appointmentCrudService).delete(2)
    }

    @Test
    fun patchWhenAppointmentExist() {
        //given
        val currentTime = LocalDateTime.now()
        val appointmentAfterPatch = Appointment(1, LocalDateTime.now().plusHours(1), "Wrocław", doctor, patient)
        val expectedResult = ResponseEntity.ok(appointmentAfterPatch)
        val mapOfChanges = hashMapOf("officeAddress" to "Wrocław", "timeOfAppointment" to currentTime.plusHours(1).toString())

        Mockito.`when`(appointmentCrudService.patch(1, mapOfChanges))
                .thenReturn(Optional.of(appointmentAfterPatch))
        //when
        val patchAppointmentResponse = appointmentCrudController.patch(1, mapOfChanges)

        //then
        assertEquals(expectedResult, patchAppointmentResponse)
        Mockito.verify(appointmentCrudService).patch(1, mapOfChanges)
    }

    @Test
    fun patchWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<Appointment>()
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        Mockito.`when`(appointmentCrudService.patch(2, mapOfChanges))
                .thenReturn(Optional.empty())

        //when
        val patchAppointmentResponse = appointmentCrudController.patch(2, mapOfChanges)

        //then
        assertEquals(expectedResult, patchAppointmentResponse)
        Mockito.verify(appointmentCrudService).patch(2, mapOfChanges)
    }
}