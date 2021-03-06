package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.AppointmentDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Appointment
import com.mateuszhaberla.recruitmenttaskrsq.service.AppointmentCrudService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.Optional

@RestController
@RequestMapping(path = ["/v1/api"])
class AppointmentCrudController(
        private val appointmentCrudService: AppointmentCrudService
) {

    @PostMapping("/appointments")
    @ApiOperation(value = "Create Appointments")
    fun create(@RequestBody appointmentDto: AppointmentDto): ResponseEntity<AppointmentDto> {
        val createdPatient = appointmentCrudService.create(appointmentDto)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPatient)
    }

    @GetMapping(value = ["/appointments", "/appointments/{id}"])
    @ApiOperation(value = "Read All Appointments")
    fun readAllOrOne(@PathVariable(value = "id", required = false) id: Long?): ResponseEntity<MutableList<AppointmentDto>> {
        val listOfAppointments: MutableList<AppointmentDto> = Optional.ofNullable(id)
                .map { appointmentCrudService.read(id) }
                .orElseGet { appointmentCrudService.readAll() }

        return if (listOfAppointments.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        else {
            ResponseEntity.ok().body(listOfAppointments)
        }
    }

    @PutMapping("/appointments")
    @ApiOperation(value = "Update Appointments")
    fun update(@RequestBody appointmentDto: AppointmentDto): ResponseEntity<AppointmentDto> {
        return appointmentCrudService.update(appointmentDto)
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

    @PatchMapping("/appointments/{id}")
    @ApiOperation(value = "Patch Appointments")
    fun patch(@PathVariable("id") id: Long, @RequestBody appointmentChangesMap: HashMap<String, String>): ResponseEntity<AppointmentDto> {
        return appointmentCrudService.patch(id, appointmentChangesMap)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }

    }

    @PatchMapping("/timeChange/{id}")
    @ApiOperation(value = "Patch Appointments")
    fun changeTimeOfAppointment(@PathVariable("id") id: Long, @RequestBody hourOfExistingVisit: LocalDateTime): ResponseEntity<AppointmentDto> {
        return appointmentCrudService.changeTimeOfAppointment(id, hourOfExistingVisit)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }

    }
}