<template>
  <div class="settings">
    <div class="setting">
      <div v-if="realGroup.admins.includes(currentUser.id)">

        <div class="setting-label">Group Privacy</div>
        <div class="setting-card">
            <div class="radio">
              <label><input value="public" type="radio" name="privacy" class="mr-2" v-model="settings.groupPrivacy" v-on:change="updateSettings">Public</label>
            </div>
            <div class="radio">
              <label><input value="private" type="radio" name="privacy" class="mr-2" v-model="settings.groupPrivacy" v-on:change="updateSettings">Private</label>
            </div>
        </div>

        <div class="setting-label">Member Approval Policy</div>
        <div class="setting-card">
            <div class="radio">
              <label><input value="admin" type="radio" name="approval" class="mr-2" v-model="settings.memberApprovalPolicy" v-on:change="updateSettings">Admins</label>
            </div>
            <div class="radio">
              <label><input value="moderator" type="radio" name="approval" class="mr-2" v-model="settings.memberApprovalPolicy" v-on:change="updateSettings">Moderators/Admins</label>
            </div>
            <div class="radio">
              <label><input value="anyone" type="radio" name="approval" class="mr-2" v-model="settings.memberApprovalPolicy" v-on:change="updateSettings">Any Member</label>
            </div>
        </div>

        <div class="setting-label">Manage Members</div>
        <div class="setting-card">
          <div class="d-flex mt-1" v-bind:key="user.id" v-for="user in members">
            <i class="fas fa-user mr-2"></i>
            <span class="user-info">{{ user.displayName }}</span>
            <div class="form-check form-check-inline">
              <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="none" :checked="!realGroup.admins.includes(user.id) && !realGroup.moderators.includes(user.id)" v-on:change="updateUserSettings">
              <label class="form-check-label" :for="user.name">None</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="moderator" :checked="realGroup.moderators.includes(user.id)" v-on:change="updateUserSettings">
              <label class="form-check-label" :for="user.name">Moderator</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="admin" :checked="realGroup.admins.includes(user.id)" v-on:change="updateUserSettings">
              <label class="form-check-label" :for="user.name">Admin</label>
            </div>
            <button v-if="currentUser.id !== user.id" class="ml-2 btn btn-primary btn-sm" v-on:click="removeUser(user.id)">Remove</button>
          </div>
        </div>

      </div>

      <div class="setting-label">Membership Requests</div>
      <div v-bind:key="user.id" v-for="user in realGroup.pendingRequests">
        <div class="setting-card d-flex">
            <i class="fas fa-user mr-2"></i>
            <span class="user-info">{{ user.displayName }}</span>
            <button class="ml-2 btn btn-primary btn-sm" v-on:click="denyRequest(user)">Deny</button>
            <button class="ml-2 btn btn-primary btn-sm" v-on:click="approveRequest(user)">Approve</button>
        </div>
      </div>
      <div class="setting-card d-flex" v-if="realGroup.pendingRequests.length === 0">
        No Pending Membership Requests
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
      members: [],
      settings: { groupPrivacy: "", memberApprovalPolicy: "" },
      realGroup: { admins: [], moderators: [], pendingRequests: [] }
    }
  },
  methods: {
    updateUserSettings: function(event) {
      var self = this;
      const value = event.target.value;
      const userId = event.target.dataset.userid;
      const userIsAdmin = self.realGroup.admins.includes(userId);
      const userIsMod = self.realGroup.moderators.includes(userId);
      const groupId = self.realGroup.id;

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
      const self = this;
      const groupId = self.realGroup.id;

      axios.delete(`http://localhost:7777/invenio/group/${groupId}/member/${userId}`, {withCredentials: true}).then(function() {
        self.members.splice(self.members.findIndex(function(i) {
          return i.id === userId;
        }), 1);
      }); 
    },
    updateSettings: function() {
      const self = this;
      const settings = self.settings;
      axios.put(`http://localhost:7777/invenio/group/${this.realGroup.id}/settings`, settings, {withCredentials: true}).then(function(response) {
        self.$emit('group-updated', response.data);
      });
    },
    denyRequest: function(user) {
      var self = this;
      axios.delete(`http://localhost:7777/invenio/group/${this.realGroup.id}/memberapproval/${user.id}`, {withCredentials: true}).then(function(response) {
        self.realGroup = response.data;
      });
    },
    approveRequest: function(user) {
      var self = this;

      axios.put(`http://localhost:7777/invenio/group/${this.realGroup.id}/memberapproval/${user.id}`, {}, {withCredentials: true}).then(function(response) {
        self.realGroup = response.data;
      });
    }
  }, 
  watch: {
    group: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal !== oldVal) {
          axios.get(`http://localhost:7777/invenio/group/${this.group.id}/members`, {withCredentials: true}).then(response => {
            this.members = response.data;
          });
          axios.get(`http://localhost:7777/invenio/group/${this.group.id}`, {withCredentials: true}).then(response => {
            this.settings = response.data.groupSettings;
            this.realGroup = response.data;
          });
        }
      }
    }
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
