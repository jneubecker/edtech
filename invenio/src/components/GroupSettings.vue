<template>
  <div class="settings">
    <div class="setting">
      <div class="setting-label">Manage Members</div>
      <div class="setting-card">
        <div class="d-flex mt-1" v-bind:key="user.id" v-for="user in members">
          <i class="fas fa-user mr-2"></i>
          <span class="user-info">{{ user.displayName }}</span>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="none" :checked="!realGroup.admins.includes(user.id) && !realGroup.moderators.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">None</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="moderator" :checked="realGroup.moderators.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">Moderator</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" :disabled="currentUser.id === user.id" :data-userid="user.id" type="radio" :name="user.name" value="admin" :checked="realGroup.admins.includes(user.id)" v-on:change="updateSettings">
            <label class="form-check-label" :for="user.name">Admin</label>
          </div>
          <button v-if="currentUser.id !== user.id" class="ml-2 btn btn-primary btn-sm" v-on:click="removeUser(user.id)">Remove</button>
        </div>
      </div>

      <div class="setting-label">Group Privacy</div>
      <div class="setting-card">
          <div class="radio">
            <label><input value="public" type="radio" name="optradio" class="mr-2" v-model="settings.groupPrivacy" v-on:change="updatePrivacySettings">Public</label>
          </div>
          <div class="radio">
            <label><input value="private" type="radio" name="optradio" class="mr-2" v-model="settings.groupPrivacy" v-on:change="updatePrivacySettings">Private</label>
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
      members: [],
      settings: { groupPrivacy: "" },
      realGroup: { admins: [], moderators: [] }
    }
  },
  methods: {
    updateSettings: function(event) {
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
      console.log(userId);
      const self = this;
      const groupId = self.realGroup.id;

      axios.delete(`http://localhost:7777/invenio/group/${groupId}/member/${userId}`, {withCredentials: true}).then(function() {
        self.members.splice(self.members.findIndex(function(i) {
          return i.id === userId;
        }), 1);
      }); 
    },
    updatePrivacySettings: function() {
      const self = this;
      const settings = self.settings;
      axios.put(`http://localhost:7777/invenio/group/${this.realGroup.id}/settings`, settings, {withCredentials: true}).then(function(response) {
        self.$emit('group-updated', response.data);
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
