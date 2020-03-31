package com.mateuszhaberla.recruitmenttaskrsq.repository


import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment,Long>{
}