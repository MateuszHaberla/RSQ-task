package com.mateuszhaberla.recruitmenttaskrsq.model

import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@NoArgsConstructor
data class Doctor(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val surname: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val specialization: Specialization,
    val dupa: String
)