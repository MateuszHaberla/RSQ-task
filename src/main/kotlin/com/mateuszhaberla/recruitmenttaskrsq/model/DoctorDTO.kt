package com.mateuszhaberla.recruitmenttaskrsq.model

data class DoctorDTO(
        val id: Long,
        val name: String,
        val surname: String,
        val specialization: Specialization
)