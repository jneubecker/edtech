<template>
  <div class="login">
     <button class="btn btn-primary" v-on:click="login">Login</button>
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

</style>
