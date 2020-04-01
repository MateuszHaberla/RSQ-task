package com.mateuszhaberla.recruitmenttaskrsq.controller

import com.mateuszhaberla.recruitmenttaskrsq.model.Patient
import com.mateuszhaberla.recruitmenttaskrsq.model.PatientDTO
import com.mateuszhaberla.recruitmenttaskrsq.service.PatientCrudService
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/api"])
@RequiredArgsConstructor
class PatientCrudController @Autowired constructor(val patientCrudService: PatientCrudService){

    @PostMapping("/patients")
    @ApiOperation(value = "Create Patient")
    fun create(@RequestBody patient: Patient): ResponseEntity<Patient> {
        val createdPatient = patientCrudService.create(patient)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPatient)
    }

    @GetMapping("/patients/{id}")
    @ApiOperation(value = "Read Patient")
    fun read(@PathVariable("id") id: Long): ResponseEntity<Patient?> {
        return patientCrudService.read(id)
                .map { ResponseEntity.ok(it) }
                .orElseGet{ ResponseEntity.status(HttpStatus.NOT_FOUND).build() }

    }

    @GetMapping("/patients")
    @ApiOperation(value = "Read All Patients")
    fun readAll(): List<ResponseEntity<Patient>> {
        return patientCrudService.readAll()
                .map { ResponseEntity.ok(it) }
    }

    @PutMapping("/patients")
    @ApiOperation(value = "Update Patient")
    fun update(@RequestBody patientDto: PatientDTO): ResponseEntity<Patient> {
        return patientCrudService.update(patientDto)
                .map { ResponseEntity.ok(it) }
                .orElseGet{ ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }

    @DeleteMapping("/patients/{id}")
    @ApiOperation(value = "Delete Patient")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Patient> {
        return if (patientCrudService.delete(id))
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}