package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.model.AppointmentDTO
import com.mateuszhaberla.recruitmenttaskrsq.service.AppointmentCrudService
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/api"])
@Validated
@RequiredArgsConstructor
class AppointmentController @Autowired constructor(
        private val appointmentCrudService: AppointmentCrudService
) {

    @PostMapping("/appointments")
    @ApiOperation(value = "Create Appointments")
    fun create(@RequestBody appointment: Appointment): ResponseEntity<Appointment> {
        val createdPatient = appointmentCrudService.create(appointment)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPatient)
    }

    @PutMapping("/appointments")
    @ApiOperation(value = "Update Appointments")
    fun update(@RequestBody appointmentDTO: AppointmentDTO): ResponseEntity<Appointment> {
        return appointmentCrudService.update(appointmentDTO)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }

    @DeleteMapping("/appointments/{id}")
    @ApiOperation(value = "Delete Appointments")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Appointment> {
        return if (appointmentCrudService.delete(id))
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}