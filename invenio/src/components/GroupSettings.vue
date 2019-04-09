<template>
  <div class="settings">
    <div class="setting">
      <div class="setting-label">Assign Moderators and Admins</div>
      <div class="setting-card">
        <div class="d-flex mt-1" v-bind:key="user.id" v-for="user in members">
          <i class="fas fa-user mr-2"></i>
          <span class="user-info">{{ user.displayName }}</span>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" :name="user.name" value="option1">
            <label class="form-check-label" for="inlineRadio1">None</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" :name="user.name" value="option1">
            <label class="form-check-label" for="inlineRadio1">Moderator</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" :name="user.name" value="option1">
            <label class="form-check-label" for="inlineRadio1">Admin</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GroupSettings',
  props: ["group"],
  data: function() {
    return {
      members: [],
    }
  },
  methods: {
    updateSettings: function() {
      
    }
  }, 
  mounted() {
    axios.get(`http://localhost:7777/invenio/group/${this.group.id}/members`, {withCredentials: true})
      .then(response => {
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
