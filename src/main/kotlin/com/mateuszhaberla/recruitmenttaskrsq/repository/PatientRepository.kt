package com.mateuszhaberla.recruitmenttaskrsq.repository

import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
}