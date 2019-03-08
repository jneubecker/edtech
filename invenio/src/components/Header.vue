<template>
  <div class="top-header row">
      <div class="col pl-3">
          {{ selectedGroup }}
      </div>
      <div class="col logout">
        <button class="btn btn-primary" v-on:click="$emit('logout')">Log Out</button>
      </div>
  </div>
</template>

<script>

export default {
  name: 'Header',
  components: {
  },
  props: ["selectedGroup"]
}
</script>

<style scoped>
.top-header {
    height: 50px;
    line-height: 50px;
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
.logout {
    max-width: 100px;
}
</style>
