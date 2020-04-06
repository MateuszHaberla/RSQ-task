package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.PatientMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.repository.PatientRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class PatientCrudServiceTest {

    private lateinit var patientMapper: PatientMapper
    private lateinit var patientRepository: PatientRepository
    private lateinit var patientCrudService: PatientCrudService
    private lateinit var objectMapper: ObjectMapper

    val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
    val patientDto = PatientDto(1, "Mateusz", "Haberla", "Poznań")

    @BeforeEach
    fun setUp() {
        patientMapper = mock(PatientMapper::class.java)
        patientRepository = mock(PatientRepository::class.java)
        objectMapper = mock(ObjectMapper::class.java)

        patientCrudService = PatientCrudService(patientRepository, patientMapper, objectMapper)
    }

    @Test
    fun create() {
        //given
        `when`(patientMapper.mapDtoToPatient(patientDto))
                .thenReturn(patient)

        `when`(patientRepository.save(any(Patient::class.java)))
                .thenReturn(patient)

        //when
        val createdPatientDto = patientCrudService.create(patientDto)

        //then
        assertTrue(patientDto == createdPatientDto)
        verify(patientRepository).save(any(Patient::class.java))
        verify(patientMapper).mapDtoToPatient(patientDto)
    }

    @Test
    fun read() {
        //given
        `when`(patientRepository.findById(1))
                .thenReturn(Optional.of(patient))

        `when`(patientMapper.mapPatientToDto(patient))
                .thenReturn(patientDto)

        //when
        val patientDto = patientCrudService.read(1)

        //then
        assertNotNull(patientDto)
        verify(patientRepository).findById(any())
        verify(patientMapper).mapPatientToDto(patient)
    }

    @Test
    fun readAll() {
        //given
        val allPatients = mutableListOf(patient)
        `when`(patientRepository.findAll())
                .thenReturn(allPatients)

        `when`(patientMapper.mapPatientToDto(patient))
                .thenReturn(patientDto)

        // when
        val allPatientsDto = patientCrudService.readAll()
        val expectedPatients = mutableListOf(patientDto)

        //then
        assertEquals(expectedPatients, allPatientsDto)
        verify(patientRepository).findAll()
        verify(patientMapper).mapPatientToDto(patient)
    }

    @Test
    fun update() {
        //given
        `when`(patientMapper.mapDtoToPatient(patientDto))
                .thenReturn(patient)

        `when`(patientRepository.findById(1))
                .thenReturn(Optional.of(patient))

        `when`(patientRepository.save(any(Patient::class.java)))
                .thenReturn(patient)

        `when`(patientMapper.mapPatientToDto(patient))
                .thenReturn(patientDto)

        //when
        val updatedPatientDto = patientCrudService.update(patientDto)
        val expectedResult = Optional.of(patientDto)

        //then
        assertTrue(expectedResult == updatedPatientDto)

        verify(patientMapper).mapDtoToPatient(patientDto)
        verify(patientRepository).findById(any())
        verify(patientRepository).save(any(Patient::class.java))
        verify(patientMapper).mapPatientToDto(patient)
    }

    @Test
    fun deleteWhenIdExist() {
        //given
        `when`(patientRepository.existsById(1))
                .thenReturn(true)

        //when
        val exists = patientCrudService.delete(1)

        //then
        assertTrue(exists)
        verify(patientRepository).existsById(any())
    }

    @Test
    fun deleteWhenIdDoesNotExist() {
        //given
        `when`(patientRepository.existsById(2))
                .thenReturn(false)

        //when
        val exists = patientCrudService.delete(2)

        //then
        assertTrue(!exists)
        verify(patientRepository).existsById(any())
    }

    @Test
    fun patch() {
        //given
        val optionalOfPatient = Optional.of(patient)
        `when`(patientRepository.findById(1))
                .thenReturn(optionalOfPatient)

        val immutableMapOfPatient = mapOf("id" to 1, "name" to "Mateusz", "surname" to "Haberla", "address" to "Poznań")
        `when`(objectMapper.convertValue(any(Patient::class.java), eq(Map::class.java)))
                .thenReturn(immutableMapOfPatient)

        val patientAfterPatch = Patient(1, "Piotr", "Haberla", "Wrocław")
        `when`(objectMapper.convertValue(any(LinkedHashMap::class.java), eq(Patient::class.java)))
                .thenReturn(patientAfterPatch)

        `when`(patientRepository.save(patientAfterPatch))
                .thenReturn(patientAfterPatch)

        val patientDtoAfterPatch = PatientDto(1, "Piotr", "Haberla", "Wrocław")
        `when`(patientMapper.mapPatientToDto(patientAfterPatch))
                .thenReturn(patientDtoAfterPatch)

        val mapOfChanges = hashMapOf("name" to "Piotr", "address" to "Wrocław")

        //when
        val patchedPatient = patientCrudService.patch(1, mapOfChanges)
        val expectedResult = Optional.of(patientDtoAfterPatch)

        //then
        assertEquals(expectedResult, patchedPatient)

        verify(patientRepository).findById(any())
        verify(patientRepository).save(any())
        verify(objectMapper).convertValue(any(Patient::class.java), eq(Map::class.java))
        verify(objectMapper).convertValue(any(LinkedHashMap::class.java), eq(Patient::class.java))
        verify(patientMapper).mapPatientToDto(patientAfterPatch)
    }
}