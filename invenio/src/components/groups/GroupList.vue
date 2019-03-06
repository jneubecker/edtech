<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li class="group-list-element" v-bind:key="group.id" v-for="group in groups">
        <span class="group-list-group">{{ group.name }}</span>
        <i :data-id="group.id" v-on:click="leaveGroup" class="fas fa-minus-circle pull-right leave-group clickable"></i>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GroupList',
  props: ["groups"],
  methods: {
    leaveGroup: function() {
      var self = this;
      var groupId = event.target.dataset.id;

      const data = { 
        groupId: groupId,
        userId: document.userId
      };

      axios.post('http://localhost:7777/invenio/group/leave', data).then(function() {
        self.groups.splice(self.groups.findIndex(function(i){
          return i.id === groupId;
        }), 1);
      });      
    }
  }
}
</script>

<style scoped>
.group-list-element {
  height: 24px;
  line-height: 24px;
}
.leave-group {
  line-height: 24px;
}
.group-list-group {
  margin-right: 10px;
}
</style>
