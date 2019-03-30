<template>
  <div class="friends-container">
    <div class="friends-header">
      <span class="list-header clickable"  v-on:click="showAddFriend">Friends</span> 
      <i class="fas fa-user-friends clickable" v-on:click="$emit('show-friend-approval')"></i>
    </div>
    <AddFriend v-bind:users="users" v-if="this.isAddFriendVisible" v-on:friend-added="handleFriendAdded" v-on:cancel-add="hideAddFriend"/>
    <FriendList v-on:friend-selected="$emit('friend-selected', $event)" v-bind:friends="friends" v-on:unfriend="$emit('unfriend')"/>
  </div>
</template>

<script>
import axios from 'axios';
import FriendList from './FriendList.vue';
import AddFriend from './AddFriend.vue';

export default {
  name: 'Friends',
  components: {
    FriendList,
    AddFriend
  },
  props: ["isLoggedIn", "friends"],
  methods: {
    showAddFriend: function() {
      axios.get('http://localhost:7777/invenio/user/friend/not', {withCredentials: true})
        .then(response => {
          this.users = response.data;
          this.isAddFriendVisible = true;
        });
    },
    hideAddFriend: function() {
        this.isAddFriendVisible = false;
    },
    handleFriendAdded: function(friend) {
      this.isAddFriendVisible = false;
    }
  },
  data: function () {
    return {
        isAddFriendVisible: false,
        users: []
    }
  }
}
</script>

<style scoped>
.friends-container  {
  width: 100%;
}
.friends-header {
  display: flex;
  align-items: center;
  padding: 15px 15px 5px 15px;
}
</style>
