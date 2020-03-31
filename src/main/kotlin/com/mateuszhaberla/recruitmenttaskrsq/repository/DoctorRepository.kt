package com.mateuszhaberla.recruitmenttaskrsq.repository


import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorRepository: JpaRepository<Doctor, Long> {
}