package com.mateuszhaberla.recruitmenttaskrsq.mapper


import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class AppointmentMapperTest {

    private lateinit var appointmentMapper: AppointmentMapper

    @BeforeEach
    fun setUp() {
        appointmentMapper = AppointmentMapper()
    }

    @Test
    fun mapAppointmentToDto() {
        //given
        val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
        val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
        val appointment = Appointment(1, LocalDateTime.now(), "Poznań", doctor, patient)


        //when
        val appointmentDto = appointmentMapper.mapAppointmentToDto(appointment)

        //then
        Assertions.assertNotNull(appointmentDto)

        Assertions.assertEquals(appointmentDto.id, appointment.id)
        Assertions.assertEquals(appointmentDto.officeAddress, appointment.officeAddress)
        Assertions.assertEquals(appointmentDto.timeOfAppointment, appointment.timeOfAppointment)
        Assertions.assertEquals(appointmentDto.patient, appointment.patient)
        Assertions.assertEquals(appointmentDto.doctor, appointment.doctor)
    }

    @Test
    fun mapDtoToDoctor() {
        //given
        val patient = Patient(1, "Mateusz", "Haberla", "Poznań")
        val doctor = Doctor(1, "Mateusz", "Haberla", Specialization.ALLERGY_AND_IMMUNOLOGY)
        val appointmentDto = AppointmentDto(1, LocalDateTime.now(), "Poznań", doctor, patient)

        //when
        val appointment = appointmentMapper.mapDtoToAppointment(appointmentDto)

        //then
        Assertions.assertNotNull(appointment)

        Assertions.assertEquals(appointment.id, appointmentDto.id)
        Assertions.assertEquals(appointment.officeAddress, appointmentDto.officeAddress)
        Assertions.assertEquals(appointment.timeOfAppointment, appointmentDto.timeOfAppointment)
        Assertions.assertEquals(appointment.patient, appointmentDto.patient)
        Assertions.assertEquals(appointment.doctor, appointmentDto.doctor)
    }
}