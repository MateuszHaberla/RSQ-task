package com.mateuszhaberla.recruitmenttaskrsq.model

import lombok.NoArgsConstructor
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@NoArgsConstructor
data class Appointment(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
        @Column(nullable = false) val date: LocalDate,
        @Column(nullable = false) var hour: LocalTime,
        @Column(nullable = false) val place: String,
        @Column(nullable = false) val doctor: Long,
        @Column(nullable = false) val patient: Long
)