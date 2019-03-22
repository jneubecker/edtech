<template>
  <div class="sidebar col">
    <div class="user-info clickable" :data-id="user.id" :data-name="user.displayName" v-on:click="$emit('self-selected', $event)">{{ user.displayName }}</div>
    <Groups v-bind:isLoggedIn="isLoggedIn" v-on:leave-group="$emit('leave-group')" v-on:group-selected="$emit('group-selected', $event)"/>
    <Friends v-bind:isLoggedIn="isLoggedIn" v-on:friend-selected="$emit('friend-selected', $event)" v-on:unfriend="$emit('unfriend')"/>
  </div>
</template>

<script>
import Groups from './groups/Groups.vue'
import Friends from './friends/Friends.vue'

export default {
  name: 'Sidebar',
  props: ["isLoggedIn", "user"],
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
