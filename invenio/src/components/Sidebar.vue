<template>
  <div class="sidebar col">
    <div class="user-info">{{ userName }}</div>
    <Groups v-bind:isLoggedIn="isLoggedIn" v-on:leave-group="$emit('leave-group')" v-on:group-selected="$emit('group-selected', $event)"/>
    <Friends/>
  </div>
</template>

<script>
import Groups from './groups/Groups.vue'
import Friends from './friends/Friends.vue'

export default {
  name: 'Sidebar',
  props: ["isLoggedIn", "userName"],
  components: {
    Groups,
    Friends
  }
}
</script>

<style scoped>
  .sidebar {
    height: 100%;
    background-color: var(--main-dark-color);
    max-width: 250px;
  }
  .user-info {
    padding: 15px 15px 0px 15px;;
  }
</style>
