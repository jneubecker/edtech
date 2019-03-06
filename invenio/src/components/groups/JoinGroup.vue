<template>
  <div class="overlay">
      <div class="dialog">
        <h2>Join Group</h2>
        <div class="form-container"> 
            <div class="group-list">
                <ul class="list-unstyled">
                <li v-bind:key="group.id" v-for="group in groups">
                    <div class="group" :data-name="group.name" :data-id="group.id" v-on:click="joinGroup">{{ group.name }}</div>
                </li>
                </ul>
            </div>
        </div>
        <button class="btn btn-primary pull-right tl-margin" v-on:click="$emit('cancel-join')">Cancel</button>
        </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'JoinGroup',
  props: ["groups"],
  methods: {
    joinGroup: function(event) {
      const self = this;
      const groupId = event.target.dataset.id;

      axios.put(`http://localhost:7777/invenio/group/member/${groupId}`, {}, {withCredentials: true}).then(function() {
        self.$emit('group-joined', {
            id: groupId,
            name: event.target.dataset.name
        });
      });      
    }
  }
}
</script>

<style scoped>
.group {
  cursor: pointer;
}
.group:hover {
  background-color: var(--secondary-color);
}
</style>
