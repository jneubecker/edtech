<template>
  <div class="friends-header">
    <span class="list-header clickable"  v-on:click="showAddFriend">FRIENDS</span> 
    <AddFriend v-bind:users="users" v-if="this.isAddFriendVisible" v-on:friend-added="handleFriendAdded" v-on:cancel-add="hideAddFriend"/>
  </div>
</template>

<script>
import axios from 'axios';

import AddFriend from './AddFriend.vue';

export default {
  name: 'FriendsHeader',
  components: {
    AddFriend
  },
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
        this.$emit('friend-added', friend);
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

</style>
