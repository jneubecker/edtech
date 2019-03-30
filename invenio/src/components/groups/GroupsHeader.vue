<template>
  <div id="groups-header">
    <div class="group-header">
      <span class="list-header clickable" v-on:click="$emit('show-join-group')">Groups</span> 
      <i v-on:click="showCreateGroup" class="fas fa-plus-circle clickable"></i>
    </div>
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
      }
  },
  data: function () {
    return {
      isCreateGroupVisible: false,
      isJoinGroupVisible: false,
    }
  }
}
</script>

<style scoped>
.group-header {
  display: flex;
  align-items: center;
}
</style>
