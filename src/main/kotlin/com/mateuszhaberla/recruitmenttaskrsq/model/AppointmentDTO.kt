package com.mateuszhaberla.recruitmenttaskrsq.model

import java.time.LocalDate
import java.time.LocalTime


data class AppointmentDTO(
        val id: Long,
        val date: LocalDate,
        val hour: LocalTime,
        val place: String,
        val doctor: Long,
        val patient: Long
)