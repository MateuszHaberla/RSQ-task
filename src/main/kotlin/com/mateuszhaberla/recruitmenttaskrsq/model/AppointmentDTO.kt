package com.mateuszhaberla.recruitmenttaskrsq.model

import java.time.LocalDateTime


data class AppointmentDTO(
        val id: Long,
        val date: LocalDateTime,
        val hour: LocalDateTime,
        val place: String,
        val doctor: Long,
        val patient: Long
)