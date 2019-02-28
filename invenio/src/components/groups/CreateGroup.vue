<template>
  <div id="create-group">
      <div class="form-container">
          <h2>Create Group</h2>
          <div class="form-group">
            <label for="group-name">Group Name</label>
            <input id="group-name" class="form-control" type="text" name="name" placeholder="Enter group name"/>
            <button class="btn btn-primary pull-right" v-on:click="createGroup">Create</button>
            <button class="btn btn-primary pull-right" v-on:click="$emit('cancel-create')">Cancel</button>
          </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CreateGroup',
  methods: {
    createGroup: function() {
      const self = this;
      const groupName = document.getElementById("group-name").value;
      const data = { 
          name: groupName 
      };

      axios.put('http://localhost:7777/invenio/group', data).then(function(response) {
        self.$emit('group-created', response.data);
      });      
    }
  }
}
</script>

<style scoped>
#create-group {
  position: fixed; /* Sit on top of the page content */
  display: block; /* Hidden by default */
  width: 100%; /* Full width (cover the whole page) */
  height: 100%; /* Full height (cover the whole page) */
  top: 0; 
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5); /* Black background with opacity */
  z-index: 2; /* Specify a stack order in case you're using a different order for other elements */
}
.form-container {
  color: #000;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 15px;
  width: 400px;
}
</style>
