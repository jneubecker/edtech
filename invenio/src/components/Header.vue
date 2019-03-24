<template>
  <div class="top-header row">
      <div class="col pl-3">
        <h4 class="group-header">{{ selectedGroup }}</h4>
      </div>
      <div class="col icons">
        <div class="icon" v-bind:class="{ selected: isFriendApprovalVisible }">
          <i class="fas fa-user-friends clickable" v-on:click="toggleFriendApproval"></i>

          <div class="friend-approval shadow" v-if="this.isFriendApprovalVisible">
            <span>Friend Requests</span>

            <div class="friend-request mt-1" v-bind:key="user.id" v-for="user in friendRequests">
              <span>{{ user.displayName }}</span>
              <button class="pull-right ml-2 btn btn-primary btn-sm" :data-id="user.id" v-on:click="deleteRequest">delete</button>
              <button class="pull-right ml-2 btn btn-primary btn-sm" :data-id="user.id" v-on:click="approveRequest">confirm</button>
            </div>
          </div>
        </div>
      </div>
      <div class="col logout">
        <button class="btn btn-primary" v-on:click="$emit('logout')">Log Out</button>
      </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Header',
  components: {
  },
  props: ["selectedGroup"],
  methods: {
    toggleFriendApproval: function() {
      this.isFriendApprovalVisible = !this.isFriendApprovalVisible;

      if (this.isFriendApprovalVisible) {
        axios
          .get('http://localhost:7777/invenio/user/friend/request', {withCredentials: true})
          .then(response => (this.friendRequests = response.data));
      }
    },
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
  data: function () {
    return {
      isFriendApprovalVisible: false,
      friendRequests: []
    }
  }
}
</script>

<style scoped>
.top-header {
    height: 50px;
    line-height: 50px;
    background-color: var(--accent-color);
}

.btn {
    background-color: var(--main-dark-color);
    border-color: var(--main-dark-color);
}
.btn-primary:hover {
    background-color: var(--secondary-text-color);
    border-color: var(--secondary-text-color);
}
.logout {
    max-width: 100px;
}
.group-header {
  line-height: 50px;
}
.icons {
  text-align: right;
  padding-right: 30px;
}
.icon {
  display: inline-block;
  padding-left: 15px;
  padding-right: 15px;
}
.icon.selected {
  background-color: white;
}
.friend-approval {
  line-height: initial;
  text-align: left;
  background-color: white;
  color: black;
  position: absolute;
  z-index: 10;
  right: 30px;
  padding: 10px;
  width: 300px;
}
.friend-request {
  height: 31px;
  line-height: 31px;
}
</style>
