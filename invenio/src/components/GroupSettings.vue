<template>
  <div class="settings">
    <div class="setting">
      <div class="setting-label">Manage Members</div>
      <div class="setting-card">
        <div class="d-flex mt-1" v-bind:key="user.id" v-for="user in members">
          <i class="fas fa-user mr-2"></i>
          <span class="user-info">{{ user.displayName }}</span>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="none" :checked="!group.admins.includes(user.id) && !group.moderators.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">None</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="moderator" :checked="group.moderators.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">Moderator</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="admin" :checked="group.admins.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">Admin</label>
          </div>
          <button v-if="currentUser.id !== user.id" class="ml-2 btn btn-primary btn-sm" v-on:click="removeUser(user.id)">Remove</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GroupSettings',
  props: ["group", "currentUser"],
  data: function() {
    return {
      members: []
    }
  },
  methods: {
    updateSettings: function(event) {
      var self = this;
      const value = event.target.value;
      const userId = event.target.dataset.userid;
      const userIsAdmin = self.group.admins.includes(userId);
      const userIsMod = self.group.moderators.includes(userId);
      const groupId = self.group.id;

      if (value === 'none') {
        if (userIsMod) {
          axios.delete(`http://localhost:7777/invenio/group/${groupId}/moderator/${userId}`, {withCredentials: true}).then(function(response) {
            self.$emit('group-updated', response.data);
          });
        } else if (userIsAdmin) {
          axios.delete(`http://localhost:7777/invenio/group/${groupId}/admin/${userId}`, {withCredentials: true}).then(function(response) {
            self.$emit('group-updated', response.data);
          });
        }
      } else if (value === 'moderator') {
        if (userIsAdmin) {
          axios.delete(`http://localhost:7777/invenio/group/${groupId}/admin/${userId}`, {withCredentials: true}).then(function(response) {
            axios.put(`http://localhost:7777/invenio/group/${groupId}/moderator/${userId}`, {}, {withCredentials: true}).then(function(response) {
              self.$emit('group-updated', response.data);
            });
          });
        } else {
          axios.put(`http://localhost:7777/invenio/group/${groupId}/moderator/${userId}`, {}, {withCredentials: true}).then(function(response) {
            self.$emit('group-updated', response.data);
          });
        }        
      } else if (value === 'admin') {
        if (userIsMod) {
          axios.delete(`http://localhost:7777/invenio/group/${groupId}/moderator/${userId}`, {withCredentials: true}).then(function(response) {
            axios.put(`http://localhost:7777/invenio/group/${groupId}/admin/${userId}`, {}, {withCredentials: true}).then(function(response) {
              self.$emit('group-updated', response.data);
            });
          });
        } else {
          axios.put(`http://localhost:7777/invenio/group/${groupId}/admin/${userId}`, {}, {withCredentials: true}).then(function(response) {
            self.$emit('group-updated', response.data);
          });
        }
      }
    },
    removeUser: function(userId) {
      console.log(userId);
      const self = this;
      const groupId = self.group.id;

      axios.delete(`http://localhost:7777/invenio/group/${groupId}/member/${userId}`, {withCredentials: true}).then(function() {
        self.members.splice(self.members.findIndex(function(i) {
          return i.id === userId;
        }), 1);
      }); 
    }
  }, 
  mounted() {
    axios.get(`http://localhost:7777/invenio/group/${this.group.id}/members`, {withCredentials: true}).then(response => {
        this.members = response.data;
    });
  }
}
</script>

<style scoped>
.setting {
  color: black;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}
.setting-card {
  padding: 10px;
  border-radius: 4px;
  background-color: white;
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.075);
}
.setting-label {
  margin: 15px 0px 5px 0px;
}
.user-info {
  flex-basis: 100%;
}
</style>
