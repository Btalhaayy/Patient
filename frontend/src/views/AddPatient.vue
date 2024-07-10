<template>
  <main>
    <Navbar />
    <div class="my-5">
      <div class="mx-auto w-50" style="max-width: 100%;">
        <h2 class="text-center mb-3">Add Patient</h2>
        <form @submit.prevent="addPatient">
          <!-- Name -->
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label for="name" class="form-label">Name</label>
              <input id="name" type="text" name="name" class="form-control" placeholder="Name" required v-model="patient.name">
            </div>
          </div>

          <!-- Email -->
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label for="email" class="form-label">Email</label>
              <input id="email" type="email" name="email" class="form-control" placeholder="Email" required v-model="patient.email">
            </div>
          </div>

          <!-- Phone Number -->
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label for="pNo" class="form-label">Phone Number</label>
              <input id="pNo" type="text" name="pNo" class="form-control" placeholder="Phone Number" required v-model="patient.pNo">
            </div>
          </div>

          <!-- Gender -->
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label class="form-label">Gender</label>
              <div class="form-check">
                <input class="form-check-input" type="radio" id="male" value="male" v-model="patient.gender">
                <label class="form-check-label" for="male">Male</label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" id="female" value="female" v-model="patient.gender">
                <label class="form-check-label" for="female">Female</label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" id="others" value="others" v-model="patient.gender">
                <label class="form-check-label" for="others">Others</label>
              </div>
            </div>
          </div>

          <!-- Submit Button -->
          <div class="row">
            <div class="col-md-12 form-group">
              <button class="btn btn-primary w-100" type="submit">Submit</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import Navbar from '../components/Navbar.vue';
import swal from "sweetalert";

export default {
  name: 'AddPatient',
  components: {
    Navbar
  },
  data() {
    return {
      patient: {
        name: '',
        email: '',
        gender: 'others', // Default to 'others' for gender
        pNo: ''
      }
    }
  },
  methods: {
    addPatient() {
      fetch('http://localhost:8080/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.patient)
      })
          .then(response => {
            if (!response.ok) {
              throw new Error('Failed to add patient');
            }
            return response.json();
          })
          .then(data => {
            console.log(data);
            this.$router.push("/"); // Redirect to homepage after successful addition
            swal("Success", "Patient added successfully", "success");
          })
          .catch(error => {
            console.error('Error adding patient:', error);
            swal("Error", "Failed to add patient", "error");
          });
    }
  }
}
</script>

<style>
/* Add custom styles here if needed */
</style>
