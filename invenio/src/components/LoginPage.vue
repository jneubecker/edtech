<template>
  <div class="login">
    <div class="overlay">
      <div class="dialog">
        <button class="login-button btn btn-primary" v-on:click="login">Login With Facebook</button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'LoginPage',
  methods: {
    login: function() {
      var self = this;

      FB.login(function(response) {
        self.$emit('logged-in', response);
      });
    }
  }
}
</script>

<style scoped>
.dialog {
  height: 250px;
  text-align:center;
  line-height: 250px;
  padding: 0px;
}
</style>
