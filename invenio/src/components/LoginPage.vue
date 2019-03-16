<template>
  <div class="login">
    <div class="overlay">
      <div class="dialog">
        <div class="form-group" v-if="!this.isRegistrationVisible">
          <label for="name" class="mt-3">Name</label>
          <input type="text" class="form-control" id="name" placeholder="Name" v-model="name">
          <label for="password" class="mt-3">Password</label>
          <input type="password" class="form-control" id="password" placeholder="Password" v-model="password">
          <button class="btn btn-primary pull-right mt-3" v-on:click="login">Login</button>
          <button class="btn btn-primary pull-right mt-3 mr-2" v-on:click="showRegistration">Register</button>
        </div>

        <div class="form-group" v-if="this.isRegistrationVisible">
          <label for="name" class="mt-3">Name</label>
          <input type="text" class="form-control" id="name" placeholder="Name" v-model="name">
          <label for="displayname" class="mt-3">Display Name</label>
          <input type="text" class="form-control" id="displayname" placeholder="Display Name" v-model="displayName">
          <label for="password" class="mt-3">Password</label>
          <input type="password" class="form-control" id="password" placeholder="Password" v-model="password">
          <label for="confirmpassword" class="mt-3">Confirm Password</label>
          <input type="password" class="form-control" id="confirmpassword" placeholder="Password" v-model="confirmPassword">
          <button class="btn btn-primary pull-right mt-3" v-on:click="createUser">Create</button>
          <button class="btn btn-primary pull-right mt-3 mr-2" v-on:click="hideRegistration">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginPage',
  methods: {
    login: function() {
      axios
        .get(`http://localhost:7777/invenio/user/login/${this.name}/${this.password}`)
        .then(response =>  this.$emit('logged-in', response.data));
    },
    showRegistration: function() {
      this.isRegistrationVisible = true;
    },
    hideRegistration: function() {
      this.isRegistrationVisible = false;
    },
    createUser: function() {
      var self = this;

      if (self.password === self.confirmPassword) {
        axios.post(`http://localhost:7777/invenio/user`, {
          name: self.name,
          password: self.password,
          displayName: self.displayName
        }).then(response =>  {
          if (response.status === 200) {
            this.$emit('logged-in', response.data)
          }
        });
      }
      
    }
  },
  data: function () {
    return {
      isRegistrationVisible: false,
      name: "",
      password: "",
      confirmPassword: "",
      displayName: ""
    }
  }
}
</script>

<style scoped>
.dialog {
  padding: 30px;
}
.alert {
  display: block;
}
</style>
