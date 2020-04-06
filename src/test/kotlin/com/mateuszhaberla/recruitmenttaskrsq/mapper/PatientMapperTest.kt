package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.dto.PatientDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class PatientMapperTest {

    private lateinit var patientMapper: PatientMapper

    @BeforeEach
    fun setUp() {
        patientMapper = PatientMapper()
    }

    @Test
    fun mapPatientToDto() {
        //given
        val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
        //when
        val patientDto = patientMapper.mapPatientToDto(patient)
        //then
        Assertions.assertNotNull(patientDto)

        Assertions.assertEquals(patientDto.id, patient.id)
        Assertions.assertEquals(patientDto.name, patient.name)
        Assertions.assertEquals(patientDto.surname, patient.surname)
        Assertions.assertEquals(patientDto.address, patient.address)
    }

    @Test
    fun mapDtoToPatient() {
        //given
        val patientDto = PatientDto(1, "Mateusz", "Haberla", "Poznań")
        //when
        val patient = patientMapper.mapDtoToPatient(patientDto)
        //then
        Assertions.assertNotNull(patient)

        Assertions.assertEquals(patient.id, patientDto.id)
        Assertions.assertEquals(patient.name, patientDto.name)
        Assertions.assertEquals(patient.surname, patientDto.surname)
        Assertions.assertEquals(patient.address, patientDto.address)
    }
}