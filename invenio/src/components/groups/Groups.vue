<template>
  <div id="groups-container">
    <GroupsHeader v-on:group-created="handleGroupCreate" v-on:group-joined="handleGroupJoined"/>
    <GroupList v-bind:groups="groups"/>
  </div>
</template>

<script>
import axios from 'axios';

import GroupList from './GroupList.vue'
import GroupsHeader from './GroupsHeader.vue'

export default {
  name: 'Groups',
  components: {
    GroupList,
    GroupsHeader
  },
  data () {
    return {
      groups: []
    }
  },
  methods: {
    handleGroupCreate: function(group) {
      this.groups.push(group);
    },
    handleGroupJoined: function(group) {
      this.groups.push(group);
    }
  },
  mounted () {
    axios
      .get('http://localhost:7777/invenio/group', {withCredentials: true})
      .then(response => (this.groups = response.data));
  }
}
</script>

<style scoped>
#groups-container  {
  width: 100%;
}
</style>
