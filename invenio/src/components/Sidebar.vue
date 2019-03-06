<template>
  <div class="sidebar d-inline-flex">
    <Groups/>
  </div>
</template>

<script>
import Groups from './groups/Groups.vue'

export default {
  name: 'Sidebar',
  components: {
    Groups
  }
}
</script>

<style scoped>
  .sidebar {
    padding: 15px;
    height: 100%;
    background-color: var(--main-dark-color);
    width: 250px;
  }
</style>
