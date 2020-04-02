package com.mateuszhaberla.recruitmenttaskrsq.model

import lombok.NoArgsConstructor
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
@NoArgsConstructor
data class Appointment(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        @Column(nullable = false)
        val timeOfAppointment: LocalDateTime,
        @Column(nullable = false)
        val officeAddress: String,
        @ManyToOne(optional = false)
        val doctor: Doctor,
        @ManyToOne(optional = false)
        val patient: Patient
)