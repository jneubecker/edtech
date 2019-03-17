<template>
  <div class="login">
    <div class="overlay">
      <div class="dialog">
        <div class="form-group" v-if="!this.isRegistrationVisible">
          <label for="name" class="mt-3">Username</label>
          <input type="text" class="form-control" id="name" placeholder="Username" v-model="name">
          <label for="password" class="mt-3">Password</label>
          <input type="password" class="form-control" id="password" placeholder="Password" v-model="password">
          <span class="error">{{ loginMessage }}</span>
          <button class="btn btn-primary pull-right mt-3" v-on:click="login">Login</button>
          <button class="btn btn-primary pull-right mt-3 mr-2" v-on:click="showRegistration">Register</button>
        </div>

        <div class="form-group" v-if="this.isRegistrationVisible">
          <label for="name" class="mt-3">Username</label>
          <input type="text" class="form-control" id="name" placeholder="Username" v-model="name">
          <label for="displayname" class="mt-3">Display Name</label>
          <input type="text" class="form-control" id="displayname" placeholder="Display Name" v-model="displayName">
          <label for="password" class="mt-3">Password</label>
          <input type="password" class="form-control" id="password" placeholder="Password" v-model="password">
          <label for="confirmpassword" class="mt-3">Confirm Password</label>
          <input type="password" class="form-control" id="confirmpassword" placeholder="Password" v-model="confirmPassword">
          <span class="error">{{ registerMessage }}</span>
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
        .then(response =>  {
          this.$emit('logged-in', response.data);
          this.loginMessage = "";
        })
        .catch(response => this.loginMessage = "Invalid username or password");
    },
    showRegistration: function() {
      this.isRegistrationVisible = true;
    },
    hideRegistration: function() {
      this.isRegistrationVisible = false;
    },
    createUser: function() {
      var self = this;

      if (!self.name.trim()) {
        self.registerMessage = "Your username cannot be blank";
      } else if (!self.displayName.trim()) {
        self.registerMessage = "Your display name cannot be blank";
      } else if (!self.password.trim()) {
        self.registerMessage = "Your password cannot be blank";
      } else if (self.password === self.confirmPassword) {
        axios.post(`http://localhost:7777/invenio/user`, {
          name: self.name.trim(),
          password: self.password,
          displayName: self.displayName.trim()
        }).then(response =>  {
          if (response.status === 200) {
            this.$emit('logged-in', response.data)
          }
        }).catch(response => this.registerMessage = "There was an error during registration");
      } else {
        self.registerMessage = "Your passwords must match";
      }
      
    }
  },
  data: function () {
    return {
      isRegistrationVisible: false,
      name: "",
      password: "",
      confirmPassword: "",
      displayName: "",
      loginMessage: "",
      registerMessage: ""
    }
  }
}
</script>

<style scoped>
.dialog {
  padding: 30px;
}
.error {
  color: red;
}
</style>
