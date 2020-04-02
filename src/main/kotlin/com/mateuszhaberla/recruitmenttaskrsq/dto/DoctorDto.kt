package com.mateuszhaberla.recruitmenttaskrsq.dto

import com.mateuszhaberla.recruitmenttaskrsq.model.Specialization

data class DoctorDto(
        val id: Long,
        val name: String,
        val surname: String,
        val specialization: Specialization
)