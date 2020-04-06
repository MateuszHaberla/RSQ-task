package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import com.mateuszhaberla.recruitmenttaskrsq.service.AppointmentCrudService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private val appointmentDto = AppointmentDto(1, LocalDateTime.now(), "Poznań", doctor, patient)


    @BeforeEach
    fun setUp() {
        appointmentCrudService = Mockito.mock(AppointmentCrudService::class.java)

        appointmentCrudController = AppointmentCrudController(appointmentCrudService)
    }

    @Test
    fun create() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(appointmentDto)

        `when`(appointmentCrudService.create(appointmentDto))
                .thenReturn(appointmentDto)

        //when
        val createdAppointmentResponse = appointmentCrudController.create(appointmentDto)

        //then
        assertEquals(expectedResult, createdAppointmentResponse)
        verify(appointmentCrudService).create(appointmentDto)
    }

    @Test
    fun readAllOrOneWhenIdGivenAndAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(mutableListOf(appointmentDto))

        `when`(appointmentCrudService.read(1))
                .thenReturn(mutableListOf(appointmentDto))
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(1)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        verify(appointmentCrudService).read(1)
    }

    @Test
    fun readAllOrOneWhenIdGivenAndAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<AppointmentDto>()

        `when`(appointmentCrudService.read(2))
                .thenReturn(mutableListOf())

        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(2)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        verify(appointmentCrudService).read(2)
    }

    @Test
    fun readAllOrOneWhenIdNotGivenAndAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(mutableListOf(appointmentDto))

        `when`(appointmentCrudService.readAll())
                .thenReturn(mutableListOf(appointmentDto))
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(null)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        verify(appointmentCrudService).readAll()
    }

    @Test
    fun readAllOrOneWhenIdNotGivenAndAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<AppointmentDto>()

        `when`(appointmentCrudService.readAll())
                .thenReturn(mutableListOf())
        //when
        val readAppointmentResponse = appointmentCrudController.readAllOrOne(null)

        //then
        assertEquals(expectedResult, readAppointmentResponse)
        verify(appointmentCrudService).readAll()
    }


    @Test
    fun updateWhenAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.ok(appointmentDto)

        `when`(appointmentCrudService.update(appointmentDto))
                .thenReturn(Optional.of(appointmentDto))
        //when
        val updateAppointmentResponse = appointmentCrudController.update(appointmentDto)

        //then
        assertEquals(expectedResult, updateAppointmentResponse)
        verify(appointmentCrudService).update(appointmentDto)
    }

    @Test
    fun updateWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<AppointmentDto>()

        `when`(appointmentCrudService.update(appointmentDto))
                .thenReturn(Optional.empty())
        //when
        val updateAppointmentResponse = appointmentCrudController.update(appointmentDto)

        //then
        assertEquals(expectedResult, updateAppointmentResponse)
        verify(appointmentCrudService).update(appointmentDto)
    }

    @Test
    fun deleteWhenAppointmentExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).build<AppointmentDto>()

        `when`(appointmentCrudService.delete(1))
                .thenReturn(true)

        //when
        val deleteAppointmentResponse = appointmentCrudController.delete(1)

        //then
        assertEquals(expectedResult, deleteAppointmentResponse)
        verify(appointmentCrudService).delete(1)
    }

    @Test
    fun deleteWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<AppointmentDto>()

        `when`(appointmentCrudService.delete(2))
                .thenReturn(false)

        //when
        val deleteAppointmentResponse = appointmentCrudController.delete(2)

        //then
        assertEquals(expectedResult, deleteAppointmentResponse)
        verify(appointmentCrudService).delete(2)
    }

    @Test
    fun patchWhenAppointmentExist() {
        //given
        val currentTime = LocalDateTime.now()
        val appointmentAfterPatch = AppointmentDto(1, LocalDateTime.now().plusHours(1), "Wrocław", doctor, patient)
        val expectedResult = ResponseEntity.ok(appointmentAfterPatch)
        val mapOfChanges = hashMapOf("officeAddress" to "Wrocław", "timeOfAppointment" to currentTime.plusHours(1).toString())

        `when`(appointmentCrudService.patch(1, mapOfChanges))
                .thenReturn(Optional.of(appointmentAfterPatch))
        //when
        val patchAppointmentResponse = appointmentCrudController.patch(1, mapOfChanges)

        //then
        assertEquals(expectedResult, patchAppointmentResponse)
        verify(appointmentCrudService).patch(1, mapOfChanges)
    }

    @Test
    fun patchWhenAppointmentDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<AppointmentDto>()
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        `when`(appointmentCrudService.patch(2, mapOfChanges))
                .thenReturn(Optional.empty())

        //when
        val patchAppointmentResponse = appointmentCrudController.patch(2, mapOfChanges)

        //then
        assertEquals(expectedResult, patchAppointmentResponse)
        verify(appointmentCrudService).patch(2, mapOfChanges)
    }
}