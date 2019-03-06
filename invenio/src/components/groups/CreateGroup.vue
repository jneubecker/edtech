<template>
  <div class="overlay">
    <div class="dialog">
      <h2>Create Group</h2>
      <div class="form-container">
          <div class="form-group">
            <label for="group-name">Group Name</label>
            <input id="group-name" class="form-control" type="text" name="name" placeholder="Enter group name"/>
          </div>
      </div>
      <button class="btn btn-primary pull-right tl-margin" v-on:click="createGroup">Create</button>
      <button class="btn btn-primary pull-right tl-margin" v-on:click="$emit('cancel-create')">Cancel</button>
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
      let groupId = null;

      axios.put(`http://localhost:7777/invenio/group/${groupName}`, {}, {withCredentials: true}).then(function(response) {
        groupId = response.data.id;

        if (groupId) {
          axios.put(`http://localhost:7777/invenio/group/member/${groupId}`, {}, {withCredentials: true});
        }

        self.$emit('group-created', response.data);
      }); 
    }
  }
}
</script>

<style scoped>

</style>
