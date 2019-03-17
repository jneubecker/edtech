<template>
  <div class="friends-container">
      <FriendsHeader v-on:friend-added="handleFriendAdded"/>
      <FriendList v-bind:friends="friends"/>
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
  props: ["isLoggedIn"],
  data () {
    return {
      friends: []
    }
  },
  methods: {
    handleFriendAdded: function(friend) {
      this.friends.push(friend);
    }  
  },
  watch: {
    isLoggedIn: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal) {
          axios
            .get('http://localhost:7777/invenio/user/friend', {withCredentials: true})
            .then(response => (this.friends = response.data));
        }
      }
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
