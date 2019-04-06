<template>
  <div class="sidebar col">
    <div class="user-header d-flex align-items-center">
      <span class="user-info clickable w-100" :data-id="user.id" :data-name="user.displayName" v-on:click="$emit('self-selected', $event)">{{ user.displayName }}</span>
      <i class="fas fa-cog clickable" v-on:click="$emit('goto-settings')"></i>
    </div>
    <Groups       
      ref="groups"
      v-on:show-join-group="$emit('show-join-group')"
      v-bind:isLoggedIn="isLoggedIn" 
      v-on:leave-group="$emit('leave-group')" 
      v-on:group-selected="$emit('group-selected', $event)"
    />
    <Friends  
      v-bind:friends="friends" 
      v-bind:isLoggedIn="isLoggedIn" 
      v-on:friend-selected="$emit('friend-selected', $event)" 
      v-on:unfriend="$emit('unfriend')"
      v-on:show-friend-approval="$emit('show-friend-approval')"
    />
  </div>
</template>

<script>
import Groups from './groups/Groups.vue'
import Friends from './friends/Friends.vue'

export default {
  name: 'Sidebar',
  props: ["isLoggedIn", "user", "friends"],
  components: {
    Groups,
    Friends
  },
  methods: {
    addGroup: function(group) {
      this.$refs.groups.addGroup(group);
    }
  }
}
</script>

<style scoped>
  .sidebar {
    height: 100%;
    background-color: var(--main-dark-color);
    max-width: 250px;
  }
  .user-header {
    padding: 15px 15px 0px 15px;;
  }
</style>
