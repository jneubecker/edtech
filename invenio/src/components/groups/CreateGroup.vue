<template>
  <div id="create-group">
      <div class="form-container">
          <span>Name</span>
          <input id="group-name" type="text" name="name"/>
          <button class="btn btn-primary" v-on:click="createGroup">Create</button>
      </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CreateGroup',
  methods: {
    createGroup: function() {
        const groupName = document.getElementById("group-name").value;
        const data = { 
            name: groupName 
        };

        axios.put('http://localhost:7777/invenio/group', data);      
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
    cursor: pointer; /* Add a pointer on hover */
}
</style>
