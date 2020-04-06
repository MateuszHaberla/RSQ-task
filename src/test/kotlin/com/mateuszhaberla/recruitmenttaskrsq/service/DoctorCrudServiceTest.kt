package com.mateuszhaberla.recruitmenttaskrsq.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import com.mateuszhaberla.recruitmenttaskrsq.mapper.DoctorMapper
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import com.mateuszhaberla.recruitmenttaskrsq.repository.DoctorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class DoctorCrudServiceTest {

    private lateinit var doctorMapper: DoctorMapper
    private lateinit var doctorRepository: DoctorRepository
    private lateinit var doctorCrudService: DoctorCrudService
    private lateinit var objectMapper: ObjectMapper

    val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
    val doctorDto = DoctorDto(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)

    @BeforeEach
    fun setUp() {
        doctorMapper = mock(DoctorMapper::class.java)
        doctorRepository = mock(DoctorRepository::class.java)
        objectMapper = mock(ObjectMapper::class.java)

        doctorCrudService = DoctorCrudService(doctorMapper, doctorRepository, objectMapper)
    }

    @Test
    fun create() {
        //given
        `when`(doctorMapper.mapDtoToDoctor(doctorDto))
                .thenReturn(doctor)

        `when`(doctorRepository.save(any(Doctor::class.java)))
                .thenReturn(doctor)

        //when
        val createdDoctor = doctorCrudService.create(doctorDto)

        //then
        assertTrue(doctorDto == createdDoctor)
        verify(doctorRepository).save(any(Doctor::class.java))
        verify(doctorMapper).mapDtoToDoctor(doctorDto)
    }

    @Test
    fun read() {
        //given
        `when`(doctorRepository.findById(1))
                .thenReturn(Optional.of(doctor))

        `when`(doctorMapper.mapDoctorToDto(doctor))
                .thenReturn(doctorDto)

        //when
        val doctorDto = doctorCrudService.read(1)

        //then
        assertNotNull(doctorDto)
        verify(doctorRepository).findById(any())
        verify(doctorMapper).mapDoctorToDto(doctor)
    }

    @Test
    fun update() {
        //given
        `when`(doctorMapper.mapDtoToDoctor(doctorDto))
                .thenReturn(doctor)

        `when`(doctorRepository.findById(1))
                .thenReturn(Optional.of(doctor))

        `when`(doctorRepository.save(any(Doctor::class.java)))
                .thenReturn(doctor)

        `when`(doctorMapper.mapDoctorToDto(doctor))
                .thenReturn(doctorDto)

        //when
        val updatedDoctorDto = doctorCrudService.update(doctorDto)
        val expectedResult = Optional.of(doctorDto)

        //then
        assertTrue(expectedResult == updatedDoctorDto)

        verify(doctorMapper).mapDtoToDoctor(doctorDto)
        verify(doctorRepository).findById(any())
        verify(doctorRepository).save(any(Doctor::class.java))
        verify(doctorMapper).mapDoctorToDto(doctor)
    }

    @Test
    fun deleteWhenIdExist() {
        //given
        `when`(doctorRepository.existsById(1))
                .thenReturn(true)

        //when
        val exists = doctorCrudService.delete(1)

        //then
        assertTrue(exists)
        verify(doctorRepository).existsById(any())
    }

    @Test
    fun deleteWhenIdDoesNotExist() {
        //given
        `when`(doctorRepository.existsById(2))
                .thenReturn(false)

        //when
        val exists = doctorCrudService.delete(2)

        //then
        assertTrue(!exists)
        verify(doctorRepository).existsById(any())
    }

    @Test
    fun patch() {
        //given
        val optionalOfDoctor = Optional.of(doctor)
        `when`(doctorRepository.findById(1))
                .thenReturn(optionalOfDoctor)

        val immutableMapOfDoctor = mapOf("id" to 1, "name" to "Mateusz", "surname" to "Haberla", "specialization" to Specialization.ALLERGY_AND_IMMUNOLOGY)
        `when`(objectMapper.convertValue(any(Doctor::class.java), eq(Map::class.java)))
                .thenReturn(immutableMapOfDoctor)

        val doctorAfterPatch = Doctor(1, "Piotr", "Haberla", Specialization.PEDIATRICS)
        `when`(objectMapper.convertValue(any(LinkedHashMap::class.java), eq(Doctor::class.java)))
                .thenReturn(doctorAfterPatch)

        `when`(doctorRepository.save(doctorAfterPatch))
                .thenReturn(doctorAfterPatch)

        val doctorDtoAfterPatch = DoctorDto(1, "Piotr", "Haberla", Specialization.PEDIATRICS)
        `when`(doctorMapper.mapDoctorToDto(doctorAfterPatch))
                .thenReturn(doctorDtoAfterPatch)

        val mapOfChanges = hashMapOf("name" to "Piotr", "specialization" to Specialization.PEDIATRICS.name)

        //when
        val patchedDoctorDto = doctorCrudService.patch(1, mapOfChanges)
        val expectedResult = Optional.of(doctorDtoAfterPatch)

        //then
        assertEquals(expectedResult, patchedDoctorDto)

        verify(doctorRepository).findById(any())
        verify(doctorRepository).save(any())
        verify(objectMapper).convertValue(any(Doctor::class.java), eq(Map::class.java))
        verify(objectMapper).convertValue(any(LinkedHashMap::class.java), eq(Doctor::class.java))
        verify(doctorMapper).mapDoctorToDto(doctorAfterPatch)
    }
}