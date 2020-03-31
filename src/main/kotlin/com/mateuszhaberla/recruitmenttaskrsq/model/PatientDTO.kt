package com.mateuszhaberla.recruitmenttaskrsq.model

data class PatientDTO(
        val id: Long,
        val name: String,
        val surname: String,
        val address: String
)