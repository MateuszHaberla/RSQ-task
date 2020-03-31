package com.mateuszhaberla.recruitmenttaskrsq.model

import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class Patient(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
        @Column(nullable = false) val name: String,
        @Column(nullable = false) val surname: String,
        @Column(nullable = false) val address: String
)