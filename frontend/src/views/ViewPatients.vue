<template>
  <main>
    <Navbar />

    <!-- Search Bar -->
    <div class="container mt-3">
      <input type="text" class="form-control form-control-sm" placeholder="Search patients..." v-model="searchQuery">
    </div>

    <div class="container mt-3">
      <button class="btn btn-secondary" @click="exportToExcel">Export to Excel</button>
    </div>

    <!-- Table-->
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="text-center">Patient Management System </h1>
          <h2 class="text-center">Patients</h2>
          <!--Add button -->
          <a href="/add" class="btn btn-primary">Add Patient</a>
          <table class="table table-striped mt-3">
            <thead>
            <tr>
              <th scope="col" class="sortable" @click="sortBy('id')">Id</th>
              <th scope="col" class="sortable" @click="sortBy('name')">Name</th>
              <th scope="col" class="sortable" @click="sortBy('email')">Email</th>
              <th scope="col" class="sortable" @click="sortBy('pNo')">Phone Number</th>
              <th scope="col" class="sortable" @click="sortBy('gender')">Gender</th>
              <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="patient in paginatedPatients" :key="patient.id">
              <th scope="row">{{patient.id}}</th>
              <td>{{patient.name}}</td>
              <td>{{patient.email}}</td>
              <td>{{patient.pNo}}</td>
              <td>{{patient.gender}}</td>
              <td>
                <button class="btn btn-primary mx-2" @click="openUpdatePage(patient.id)">Edit</button>
                <button id="sil" class="btn btn-danger mx-2" @click="deletePatient(patient.id)">Delete</button>
              </td>
            </tr>
            </tbody>
          </table>
          <!-- Pagination Controls -->
          <nav>
            <ul class="pagination">
              <li class="page-item" :class="{ disabled: currentPage === 1 }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
              </li>
              <li class="page-item" :class="{ active: n === currentPage }" v-for="n in totalPages" :key="n">
                <a class="page-link" href="#" @click.prevent="changePage(n)">{{ n }}</a>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === totalPages }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>

  </main>
</template>




<script>
import Navbar from '../components/Navbar.vue'
import updatePatient from "@/views/UpdatePatient.vue";
import router from "@/router";
import swal from "sweetalert";
import * as XLSX from 'xlsx';

export default {
  name: 'ViewPatients',
  computed: {
    updatePatient() {
      return updatePatient
    },
    sortedPatients() {
      return this.patients.sort((a, b) => {
        let modifier = this.sortOrder;
        if (a[this.sortKey] < b[this.sortKey]) return -1 * modifier;
        if (a[this.sortKey] > b[this.sortKey]) return 1 * modifier;
        return 0;
      });
    },
    filteredPatients() {
      return this.sortedPatients.filter(patient =>
          patient.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          patient.email.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          patient.pNo.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    paginatedPatients() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredPatients.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredPatients.length / this.itemsPerPage);
    }
  },
  components: {
    Navbar
  },
  data() {
    return {
      patients: [],
      sortKey: '',
      sortOrder: 1,
      searchQuery: '',
      currentPage: 1,
      itemsPerPage: 10
    }
  },

  beforeMount(){
    this.getPatients()
  },

  methods: {
    openUpdatePage(id){
      return router.push("/edit/"+id)
    },
    getPatients(){
      fetch('http://localhost:8080/patients')
          .then(res => res.json())
          .then(data => {
            this.patients = data
            console.log(data)
          })
    },
    deletePatient(id){
      fetch(`http://localhost:8080/patient/${id}`, {
        method: 'DELETE'
      })
          .then(data => {
            console.log(data)
            this.getPatients()
            swal("Success","Deleted Successfully","success")
          })
    },
    sortBy(key) {
      if (this.sortKey === key) {
        this.sortOrder = -this.sortOrder
      } else {
        this.sortOrder = 1
      }
      this.sortKey = key
    },
    changePage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    exportToExcel() {
      const worksheet = XLSX.utils.json_to_sheet(this.patients);
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "Patients");
      XLSX.writeFile(workbook, "patients.xlsx");
    }
  }
}
</script>



<style scoped>
.sortable {
  cursor: pointer;
  transition: color 0.3s, transform 0.3s;
}

.sortable:hover {
  background-color: #1276e8; /* Change to your desired color */
  transform: translateY(-2px); /* Floating effect */
}
</style>
