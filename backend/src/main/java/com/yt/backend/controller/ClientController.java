package com.yt.backend.controller;

import com.yt.backend.model.Patient;
import com.yt.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    // service
    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String hello_world(){

        return "Hello World";
    }

    // add Patient
    @PostMapping("/add")
    public Patient addPatient(@RequestBody Patient patient){
       Patient patient1 = patientService.addPatient(patient);
       return patient1;
    }

    // getPatientById
    @RequestMapping("/patient/findOne/{id}")
    public Patient getPatientById(@PathVariable("id") long id){
        return patientService.getPatientById(id);
    }

    // getPatients
    @RequestMapping("/patients")
    public List<Patient> getPatients(){

        return patientService.getPatients();
    }

    // updatePatient
    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient){

        return patientService.updatePatient(patient);
    }

    // deletePatientById
    @DeleteMapping("/patient/{id}")
    public String deletePatient(@PathVariable("id") long id){
        patientService.deletePatient(id);
        return "Patient Deleted";
    }

}
