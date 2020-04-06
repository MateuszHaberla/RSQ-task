package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.dto.DoctorDto
import com.mateuszhaberla.recruitmenttaskrsq.model.Doctor
import com.mateuszhaberla.recruitmenttaskrsq.service.DoctorCrudService
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

@RestController
@RequestMapping(path = ["/v1/api"])
class DoctorCrudController(
        private val doctorCrudService: DoctorCrudService
) {

    @PostMapping("/doctors")
    @ApiOperation(value = "Create Doctor")
    fun create(@RequestBody doctorDto: DoctorDto): ResponseEntity<DoctorDto> {
        val createdPatient = doctorCrudService.create(doctorDto)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPatient)
    }

    @GetMapping("/doctors/{id}")
    @ApiOperation(value = "Read Doctor")
    fun read(@PathVariable("id") id: Long): ResponseEntity<DoctorDto> {
        return doctorCrudService.read(id)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }

    @PutMapping("/doctors")
    @ApiOperation(value = "Update Doctor")
    fun update(@RequestBody doctorDto: DoctorDto): ResponseEntity<DoctorDto> {
        return doctorCrudService.update(doctorDto)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }

    @DeleteMapping("/doctors/{id}")
    @ApiOperation(value = "Delete Doctor")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Doctor> {
        return if (doctorCrudService.delete(id))
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PatchMapping("/doctors/{id}")
    @ApiOperation(value = "Patch Patient")
    fun patch(@PathVariable("id") id: Long, @RequestBody doctorChangesMap: HashMap<String, String>): ResponseEntity<DoctorDto> {
        return doctorCrudService.patch(id, doctorChangesMap)
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }
}