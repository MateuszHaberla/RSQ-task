package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import com.mateuszhaberla.recruitmenttaskrsq.service.PatientCrudService
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
import java.util.Optional

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class PatientCrudControllerTest {

    private lateinit var patientCrudService: PatientCrudService
    private lateinit var patientCrudController: PatientCrudController

    val patientDto = PatientDto(1, "Mateusz", "Haberla", "Poznań")

    @BeforeEach
    fun setUp() {
        patientCrudService = Mockito.mock(PatientCrudService::class.java)

        patientCrudController = PatientCrudController(patientCrudService)
    }

    @Test
    fun create() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(patientDto)

        `when`(patientCrudService.create(patientDto))
                .thenReturn(patientDto)

        //when
        val createdDoctorResponse = patientCrudController.create(patientDto)

        //then
        assertEquals(expectedResult, createdDoctorResponse)
        verify(patientCrudService).create(patientDto)
    }

    @Test
    fun readAll() {
        //given
        val expectedResult = ResponseEntity.ok(mutableListOf(patientDto))

        `when`(patientCrudService.readAll())
                .thenReturn(mutableListOf(patientDto))
        //when
        val readDoctorResponse = patientCrudController.readAll()

        //then
        assertEquals(expectedResult, readDoctorResponse)
        verify(patientCrudService).readAll()
    }

    @Test
    fun readWhenIdExist() {
        //given
        val expectedResult = ResponseEntity.ok(patientDto)

        `when`(patientCrudService.read(1))
                .thenReturn(Optional.of(patientDto))
        //when
        val readDoctorResponse = patientCrudController.read(1)

        //then
        assertEquals(expectedResult, readDoctorResponse)
        verify(patientCrudService).read(1)
    }

    @Test
    fun readWhenIdDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<PatientDto>()

        `when`(patientCrudService.read(2))
                .thenReturn(Optional.empty())

        //when
        val readDoctorResponse = patientCrudController.read(2)

        //then
        assertEquals(expectedResult, readDoctorResponse)
        verify(patientCrudService).read(2)
    }

    @Test
    fun updateWhenPatientExist() {
        //given
        val expectedResult = ResponseEntity.ok(patientDto)

        `when`(patientCrudService.update(patientDto))
                .thenReturn(Optional.of(patientDto))
        //when
        val updateDoctorResponse = patientCrudController.update(patientDto)

        //then
        assertEquals(expectedResult, updateDoctorResponse)
        verify(patientCrudService).update(patientDto)
    }

    @Test
    fun updateWhenPatientDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<PatientDto>()

        `when`(patientCrudService.update(patientDto))
                .thenReturn(Optional.empty())
        //when
        val updateDoctorResponse = patientCrudController.update(patientDto)

        //then
        assertEquals(expectedResult, updateDoctorResponse)
        verify(patientCrudService).update(patientDto)
    }

    @Test
    fun deleteWhenPatientExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).build<PatientDto>()

        `when`(patientCrudService.delete(1))
                .thenReturn(true)

        //when
        val deleteDoctorResponse = patientCrudController.delete(1)

        //then
        assertEquals(expectedResult, deleteDoctorResponse)
        verify(patientCrudService).delete(1)
    }

    @Test
    fun deleteWhenPatientDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<PatientDto>()

        `when`(patientCrudService.delete(2))
                .thenReturn(false)

        //when
        val deleteDoctorResponse = patientCrudController.delete(2)

        //then
        assertEquals(expectedResult, deleteDoctorResponse)
        verify(patientCrudService).delete(2)
    }

    @Test
    fun patchWhenPatientExist() {
        //given
        val doctorAfterPatch = PatientDto(1, "Marian", "Kowalski", "Poznań")
        val expectedResult = ResponseEntity.ok(doctorAfterPatch)
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        `when`(patientCrudService.patch(1, mapOfChanges))
                .thenReturn(Optional.of(doctorAfterPatch))
        //when
        val patchDoctorResponse = patientCrudController.patch(1, mapOfChanges)

        //then
        assertEquals(expectedResult, patchDoctorResponse)
        verify(patientCrudService).patch(1, mapOfChanges)
    }

    @Test
    fun patchWhenPatientDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<PatientDto>()
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        `when`(patientCrudService.patch(2, mapOfChanges))
                .thenReturn(Optional.empty())

        //when
        val patchDoctorResponse = patientCrudController.patch(2, mapOfChanges)

        //then
        assertEquals(expectedResult, patchDoctorResponse)
        verify(patientCrudService).patch(2, mapOfChanges)
    }
}