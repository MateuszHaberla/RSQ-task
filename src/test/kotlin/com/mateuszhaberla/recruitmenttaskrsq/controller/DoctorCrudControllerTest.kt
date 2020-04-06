package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import com.mateuszhaberla.recruitmenttaskrsq.service.DoctorCrudService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class DoctorCrudControllerTest {

    private lateinit var doctorCrudService: DoctorCrudService
    private lateinit var doctorCrudController: DoctorCrudController

    val doctorDto = DoctorDto(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)

    @BeforeEach
    fun setUp() {
        doctorCrudService = mock(DoctorCrudService::class.java)

        doctorCrudController = DoctorCrudController(doctorCrudService)
    }

    @Test
    fun create() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(doctorDto)

        `when`(doctorCrudService.create(doctorDto))
                .thenReturn(doctorDto)

        //when
        val createdDoctorResponse = doctorCrudController.create(doctorDto)

        //then
        Assertions.assertEquals(expectedResult, createdDoctorResponse)
        verify(doctorCrudService).create(doctorDto)
    }

    @Test
    fun readWhenIdExist() {
        //given
        val expectedResult = ResponseEntity.ok(doctorDto)

        `when`(doctorCrudService.read(1))
                .thenReturn(Optional.of(doctorDto))
        //when
        val readDoctorResponse = doctorCrudController.read(1)

        //then
        Assertions.assertEquals(expectedResult, readDoctorResponse)
        verify(doctorCrudService).read(1)
    }

    @Test
    fun readWhenIdDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<DoctorDto>()

        `when`(doctorCrudService.read(2))
                .thenReturn(Optional.empty())

        //when
        val readDoctorResponse = doctorCrudController.read(2)

        //then
        Assertions.assertEquals(expectedResult, readDoctorResponse)
        verify(doctorCrudService).read(2)
    }

    @Test
    fun updateWhenDoctorExist() {
        //given
        val expectedResult = ResponseEntity.ok(doctorDto)

        `when`(doctorCrudService.update(doctorDto))
                .thenReturn(Optional.of(doctorDto))
        //when
        val updateDoctorResponse = doctorCrudController.update(doctorDto)

        //then
        Assertions.assertEquals(expectedResult, updateDoctorResponse)
        verify(doctorCrudService).update(doctorDto)
    }

    @Test
    fun updateWhenDoctorDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<DoctorDto>()

        `when`(doctorCrudService.update(doctorDto))
                .thenReturn(Optional.empty())
        //when
        val updateDoctorResponse = doctorCrudController.update(doctorDto)

        //then
        Assertions.assertEquals(expectedResult, updateDoctorResponse)
        verify(doctorCrudService).update(doctorDto)
    }

    @Test
    fun deleteWhenDoctorExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).build<DoctorDto>()

        `when`(doctorCrudService.delete(1))
                .thenReturn(true)

        //when
        val deleteDoctorResponse = doctorCrudController.delete(1)

        //then
        Assertions.assertEquals(expectedResult, deleteDoctorResponse)
        verify(doctorCrudService).delete(1)
    }

    @Test
    fun deleteWhenDoctorDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<DoctorDto>()

        `when`(doctorCrudService.delete(2))
                .thenReturn(false)

        //when
        val deleteDoctorResponse = doctorCrudController.delete(2)

        //then
        Assertions.assertEquals(expectedResult, deleteDoctorResponse)
        verify(doctorCrudService).delete(2)
    }

    @Test
    fun patchWhenDoctorExist() {
        //given
        val doctorAfterPatch = DoctorDto(1, "Marian", "Kowalski", Specialization.ALLERGY_AND_IMMUNOLOGY)
        val expectedResult = ResponseEntity.ok(doctorAfterPatch)
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        `when`(doctorCrudService.patch(1, mapOfChanges))
                .thenReturn(Optional.of(doctorAfterPatch))
        //when
        val patchDoctorResponse = doctorCrudController.patch(1, mapOfChanges)

        //then
        Assertions.assertEquals(expectedResult, patchDoctorResponse)
        verify(doctorCrudService).patch(1, mapOfChanges)
    }

    @Test
    fun patchWhenDoctorDoesNotExist() {
        //given
        val expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build<DoctorDto>()
        val mapOfChanges = hashMapOf("name" to "Marian", "surname" to "Kowalski")

        `when`(doctorCrudService.patch(2, mapOfChanges))
                .thenReturn(Optional.empty())

        //when
        val patchDoctorResponse = doctorCrudController.patch(2, mapOfChanges)

        //then
        Assertions.assertEquals(expectedResult, patchDoctorResponse)
        verify(doctorCrudService).patch(2, mapOfChanges)
    }
}