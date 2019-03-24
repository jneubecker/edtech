<template>
  <div class="friends-container">
      <FriendsHeader v-on:friend-added="handleFriendAdded"/>
      <FriendList v-on:friend-selected="$emit('friend-selected', $event)" v-bind:friends="friends" v-on:unfriend="$emit('unfriend')"/>
  </div>
</template>

<script>
import axios from 'axios';
import FriendsHeader from './FriendsHeader.vue';
import FriendList from './FriendList.vue';

export default {
  name: 'Friends',
  components: {
    FriendsHeader,
    FriendList
  },
  props: ["isLoggedIn", "friends"],
  methods: {
    handleFriendAdded: function(friend) {
      // this.friends.push(friend);
    }  
  }
}
</script>

<style scoped>
.friends-container  {
  width: 100%;
}
.friends-header {
  padding: 15px 15px 5px 15px;
}
</style>
