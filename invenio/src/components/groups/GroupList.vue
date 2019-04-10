<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.id" v-for="group in groups">
        <div class="group-list-element" :data-id="group.id" :data-name="group.name" v-on:click="$emit('group-selected', group)">
          <span :data-id="group.id" :data-name="group.name" class="group-info">{{ group.name }}</span>
          <i :data-id="group.id" :data-name="group.name" v-on:click="leaveGroup" class="fas fa-minus-circle leave-group clickable" v-if="!group.admins.includes(user.id)"></i>
          <i :data-id="group.id" class="fas fa-cog clickable settings" v-if="group.admins.includes(user.id)" v-on:click="gotoSettings"></i>
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
    leaveGroup: function() {
      const self = this;
      const groupId = event.target.dataset.id;

      axios.delete(`http://localhost:7777/invenio/group/${groupId}/member`, {withCredentials: true}).then(function() {
        self.groups.splice(self.groups.findIndex(function(i) {
          return i.id === groupId;
        }), 1);
      });      

      this.$emit("leave-group");
      event.stopPropagation();
    },
    gotoSettings: function() {
      const groupId = event.target.dataset.id;
      const group = this.groups[this.groups.findIndex(function(i) {
          return i.id === groupId;
      })];

      this.$emit("goto-group-settings", group);
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
