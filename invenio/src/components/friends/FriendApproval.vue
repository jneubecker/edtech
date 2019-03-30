<template>
  <div class="friend-approval">
    <div class="friend-request" v-bind:key="user.id" v-for="user in friendRequests">
      <i class="fas fa-user mr-2"></i>
      <span class="user-info">{{ user.displayName }}</span>
      <button class="ml-2 btn btn-primary btn-sm" :data-id="user.id" v-on:click="deleteRequest">delete</button>
      <button class="ml-2 btn btn-primary btn-sm" :data-id="user.id" v-on:click="approveRequest">confirm</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'FriendApproval',
  data: function () {
    return {
      friendRequests: []
    }
  },
  methods: {
    deleteRequest: function() {
      const self = this;
      const friendId = event.target.dataset.id;

      axios
        .delete(`http://localhost:7777/invenio/user/friend/request/${friendId}`, {withCredentials: true})
        .then(function() {
          self.friendRequests.splice(self.friendRequests.findIndex(function(i){
            return i.id === friendId;
          }), 1);
        });
    },
    approveRequest: function() {
      const self = this;
      const friendId = event.target.dataset.id;

      axios
        .put(`http://localhost:7777/invenio/user/friend/approve/${friendId}`, {}, {withCredentials: true})
        .then(function(response) {
          self.friendRequests.splice(self.friendRequests.findIndex(function(i){
            return i.id === friendId;
          }), 1);

          self.$emit("friend-approved", response.data);
        });
    }
  },
    mounted() {
      axios
        .get('http://localhost:7777/invenio/user/friend/request', {withCredentials: true})
        .then(response => (this.friendRequests = response.data));
    }
}
</script>

<style scoped>
.friend-request {
  display: flex;
  align-items: center;
  background-color: white;
  color: black;
  margin: 15px;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
  padding: 10px;
  border-radius: 4px;
}
.user-info {
  flex-basis: 100%;
}
</style>
