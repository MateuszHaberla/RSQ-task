package com.mateuszhaberla.recruitmenttaskrsq.mapper

import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class DoctorMapperTest {

    private lateinit var doctorMapper: DoctorMapper

    @BeforeEach
    fun setUp() {
        doctorMapper = DoctorMapper()
    }

    @Test
    fun mapDoctorToDto() {
        //given
        val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)

        //when
        val doctorDto = doctorMapper.mapDoctorToDto(doctor)

        //then
        Assertions.assertNotNull(doctorDto)

        Assertions.assertEquals(doctorDto.id, doctor.id)
        Assertions.assertEquals(doctorDto.name, doctor.name)
        Assertions.assertEquals(doctorDto.surname, doctor.surname)
        Assertions.assertEquals(doctorDto.specialization, doctor.specialization)
    }

    @Test
    fun mapDtoToDoctor() {
        //given
        val doctorDto = DoctorDto(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)

        //when
        val doctor = doctorMapper.mapDtoToDoctor(doctorDto)

        //then
        Assertions.assertNotNull(doctor)

        Assertions.assertEquals(doctor.id, doctorDto.id)
        Assertions.assertEquals(doctor.name, doctorDto.name)
        Assertions.assertEquals(doctor.surname, doctorDto.surname)
        Assertions.assertEquals(doctor.specialization, doctorDto.specialization)
    }
}