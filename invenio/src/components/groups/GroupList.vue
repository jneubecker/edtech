<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.id" v-for="group in groups">
        <div class="group-list-element" :data-id="group.id" :data-name="group.name" v-on:click="$emit('group-selected', $event)">
          {{ group.name }}
          <i :data-id="group.id" v-on:click="leaveGroup" class="fas fa-minus-circle pull-right leave-group clickable"></i>
        </div>
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

      axios.delete(`http://localhost:7777/invenio/group/member/${groupId}`, {withCredentials: true}).then(function() {
        self.groups.splice(self.groups.findIndex(function(i){
          return i.id === groupId;
        }), 1);
      });      

      this.$emit("leave-group")
    }
  }
}
</script>

<style scoped>
.group-list-element {
  padding: 5px 15px 0px 15px;
  cursor: pointer;
}
.group-list-element:hover {
  background-color: var(--accent-color);
}
.leave-group {
  display: none;
}
.group-list-element:hover .leave-group {
  display: inline-block;
}
.group-list-group {
  margin-right: 10px;
}
</style>
