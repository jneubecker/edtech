<template>
  <div class="group-list">
      <div class="form-group group-search">
        <input type="text" class="form-control" placeholder="Filter Groups" v-model="filter"/>
      </div>
      <ul class="list-unstyled">
        <li v-bind:key="group.id" v-for="group in filteredGroups">
          <div class="group shadow-sm">
            <i v-if="group.groupSettings.groupPrivacy === 'public'" class="fas fa-users mr-2"></i>
            <i v-if="group.groupSettings.groupPrivacy === 'private'" class="fas fa-user-lock mr-2"></i>
            <span class="group-info">{{ group.name }}</span>
            <button v-if="group.groupSettings.groupPrivacy === 'public'" class="ml-2 btn btn-primary btn-sm" :data-name="group.name" :data-id="group.id" v-on:click="joinGroup">Join</button>
            <button v-if="group.groupSettings.groupPrivacy === 'private' && !group.pendingRequests.includes(user.id)" class="ml-2 btn btn-primary btn-sm"  v-on:click="requsetAccess(group)">Request Access</button>
            <button class="btn btn-sm" disabled v-if="group.groupSettings.groupPrivacy === 'private' && group.pendingRequests.includes(user.id)">Request Pending</button>
          </div>
        </li>
      </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'JoinGroup',
  props: ["user"],
  data: function() {
    return {
      groups: [],
      filter: ""
    }
  },
  computed: {
    filteredGroups: function() {
      return this.groups.filter(group => group.name && group.name.toLowerCase().includes(this.filter.toLowerCase()));
    }
  },
  methods: {
    joinGroup: function(event) {
      const self = this;
      const groupId = event.target.dataset.id;

      axios.put(`http://localhost:7777/invenio/group/member/${groupId}`, {}, {withCredentials: true}).then(function(response) {
        self.$emit('group-joined', response.data);

        self.groups.splice(self.groups.findIndex(function(i){
            return i.id === groupId;
          }), 1);
      });      
    },
    addGroup: function(group) {
      this.groups.push(group);
    },
    requsetAccess: function(group) {
      axios.put(`http://localhost:7777/invenio/group//${group.id}/requestaccess`, {}, {withCredentials: true}).then(function(response) {
        group = response.data;
      }); 
    }
  },
  mounted() {
    axios.get('http://localhost:7777/invenio/group/not', {withCredentials: true})
      .then(response => {
        this.groups = response.data;
        this.isJoinGroupVisible = true;
      });
  }
}
</script>

<style scoped>
.group-list {
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}
.group {
  display: flex;
  align-items: center;
  background-color: white;
  color: black;
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 4px;
}
.group-info {
  flex-basis: 100%;
}
.group-search {
  margin-top: 15px;
}
.btn {
  color: white;
}
</style>
