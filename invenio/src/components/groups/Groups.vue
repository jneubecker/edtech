<template>
  <div id="groups-container">
    <GroupsHeader class="groups-header" v-on:show-join-group="$emit('show-join-group')" v-on:group-created="addGroup"/>
    <GroupList v-bind:user="user" v-on:goto-group-settings="(group) => $emit('goto-group-settings', group)" v-on:group-selected="$emit('group-selected', $event)" v-bind:groups="groups" v-on:leave-group="$emit('leave-group')"/>
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
  props: ["isLoggedIn", "user"],
  data () {
    return {
      groups: []
    }
  },
  methods: {
    addGroup: function(group) {
      this.groups.push(group);
    }
  },
  watch: {
    isLoggedIn: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal) {
          axios
            .get('http://localhost:7777/invenio/group', {withCredentials: true})
            .then(response => (this.groups = response.data));
        }
      }
    }
  }
}
</script>

<style scoped>
#groups-container  {
  width: 100%;
}
.groups-header {
  padding: 15px 15px 5px 15px;
}
</style>
