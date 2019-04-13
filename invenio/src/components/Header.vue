<template>
  <div class="top-header d-flex align-items-center">
      <div class="header ml-3">
        <h4 class="group-header">{{ headerLabel }}</h4>
      </div>
      <div class="logout mr-3 ml-3">
        <button class="btn btn-primary" v-on:click="$emit('logout')">Log Out</button>
      </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Header',
  components: {
  },
  props: ["headerLabel"]
}
</script>

<style scoped>
.top-header {
    height: 50px;
    background-color: var(--accent-color);
}

.btn {
    background-color: var(--main-dark-color);
    border-color: var(--main-dark-color);
}
.btn-primary:hover {
    background-color: var(--secondary-text-color);
    border-color: var(--secondary-text-color);
}
.group-header {
  margin: 0px;
}
.header {
  flex-basis: 100%;
}
</style>
