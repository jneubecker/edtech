<template>
  <div id="groups-header">
    <span class="list-header clickable" v-on:click="showJoinGroup">GROUPS</span> 
    <i v-on:click="showCreateGroup" class="fas fa-plus-circle clickable pull-right"></i>
    <JoinGroup v-bind:groups="groups" v-if="this.isJoinGroupVisible" v-on:group-joined="handleGroupJoined" v-on:cancel-join="hideJoinGroup"/>
    <CreateGroup v-if="this.isCreateGroupVisible" v-on:group-created="handleGroupCreate" v-on:cancel-create="hideCreateGroup"/>
  </div>
</template>

<script>
import axios from 'axios';

import CreateGroup from './CreateGroup.vue'
import JoinGroup from './JoinGroup.vue';

export default {
  name: 'GroupsHeader',
  components: {
      CreateGroup,
      JoinGroup
  },
  methods: {
      showCreateGroup: function() {
        this.isCreateGroupVisible = true;
      },
      hideCreateGroup: function() {
        this.isCreateGroupVisible = false;
      },
      handleGroupCreate: function(group) {
        this.isCreateGroupVisible = false;
        this.$emit('group-created', group);
      },
      showJoinGroup: function() {
        axios.get('http://localhost:7777/invenio/group')
          .then(response => {
            this.groups = response.data;
            this.isJoinGroupVisible = true;
          });
      },
      hideJoinGroup: function() {
        this.isJoinGroupVisible = false;
      },
      handleGroupJoined: function(group) {
        this.isJoinGroupVisible = false;
        this.$emit('group-joined', group);
      }
  },
  data: function () {
    return {
      isCreateGroupVisible: false,
      isJoinGroupVisible: false,
      groups: []
    }
  }
}
</script>

<style scoped>

</style>
