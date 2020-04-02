package com.mateuszhaberla.recruitmenttaskrsq.dto

import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import java.time.LocalDateTime


data class AppointmentDto(
        val id: Long,
        val timeOfAppointment: LocalDateTime,
        val officeAddress: String,
        val doctor: Doctor,
        val patient: Patient
)