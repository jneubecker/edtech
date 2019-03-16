<template>
  <div class="friends-container">
      <FriendsHeader v-on:friend-added="handleFriendAdded"/>
      {{ friends }}
  </div>
</template>

<script>
import axios from 'axios';
import FriendsHeader from './FriendsHeader.vue'


export default {
  name: 'Friends',
  components: {
    FriendsHeader  
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
