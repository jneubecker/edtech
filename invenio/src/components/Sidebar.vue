<template>
  <div class="sidebar col">
    <Groups v-on:group-selected="$emit('group-selected', $event)"/>
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
    height: 100%;
    background-color: var(--main-dark-color);
    max-width: 250px;
  }
</style>
