package com.mateuszhaberla.recruitmenttaskrsq.repository


import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Long> {
    fun findByPatient(@Param("patient") patientId: Long?): MutableList<Appointment>
}