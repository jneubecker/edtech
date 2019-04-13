<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.id" v-for="group in groups">
        <div class="group-list-element" :data-id="group.id" :data-name="group.name" v-on:click="$emit('group-selected', group)">
          <span :data-id="group.id" :data-name="group.name" class="group-info">{{ group.name }}</span>
          <i :data-id="group.id" :data-name="group.name" v-on:click="leaveGroup(group)" class="fas fa-minus-circle leave-group clickable" v-if="!group.admins.includes(user.id)"></i>
          <i class="fas fa-star" v-if="group.admins.includes(user.id)"></i>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GroupList',
  props: ["groups", "user"],
  methods: {
    leaveGroup: function(group) {
      const self = this;

      axios.delete(`http://localhost:7777/invenio/group/${group.id}/member`, {withCredentials: true}).then(function() {
        self.groups.splice(self.groups.findIndex(function(i) {
          return i.id === group.id;
        }), 1);
      });      

      this.$emit("leave-group", group);
      event.stopPropagation();
    }
  }
}
</script>

<style scoped>
.group-list-element {
  display: flex;
  align-items: center;
  padding: 2px 15px 2px 15px;
  cursor: pointer;
}
.group-list-element:hover {
  background-color: var(--accent-color);
}
.leave-group {
  display: none;
}
.settings {
  display: none;
}
.group-list-element:hover .leave-group {
  display: inline-block;
}
.group-list-element:hover .settings {
  display: inline-block;
}
.group-list-group {
  margin-right: 10px;
}
.group-info {
  flex-basis: 100%;
}
</style>
