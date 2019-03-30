<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.id" v-for="group in groups">
        <div class="group-list-element" :data-id="group.id" :data-name="group.name" v-on:click="$emit('group-selected', $event)">
          <span :data-id="group.id" :data-name="group.name" class="group-info">{{ group.name }}</span>
          <i :data-id="group.id" :data-name="group.name" v-on:click="leaveGroup" class="fas fa-minus-circle leave-group clickable"></i>
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
      const self = this;
      const groupId = event.target.dataset.id;

      axios.delete(`http://localhost:7777/invenio/group/member/${groupId}`, {withCredentials: true}).then(function() {
        self.groups.splice(self.groups.findIndex(function(i){
          return i.id === groupId;
        }), 1);
      });      

      this.$emit("leave-group");
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
.group-list-element:hover .leave-group {
  display: inline-block;
}
.group-list-group {
  margin-right: 10px;
}
.group-info {
  flex-basis: 100%;
}
</style>
