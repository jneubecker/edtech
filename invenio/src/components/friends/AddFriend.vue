<template>
  <div class="overlay">
      <div class="dialog">
        <h2>Add Friend</h2>
        <div class="form-container"> 
            <div class="user-list">
                <ul class="list-unstyled">
                    <li v-bind:key="user.id" v-for="user in users">
                        <div class="user" :data-id="user.id" v-on:click="addFriend">{{ user.displayName }}</div>
                    </li>
                </ul>
            </div>
        </div>
        <button class="btn btn-primary pull-right tl-margin" v-on:click="$emit('cancel-add')">Cancel</button>
        </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AddFriend',
  props: ["users"],
  methods: {
    addFriend: function(event) {
      const self = this;
      const userId = event.target.dataset.id;

       axios.put(`http://localhost:7777/invenio/user/friend/${userId}`, {}, {withCredentials: true}).then(function(response) {
        self.$emit('friend-added', {
            id: userId,
            displayName: response.data.displayName
        });
      });      
    }
  }
}
</script>

<style scoped>
.user {
  cursor: pointer;
}
.user:hover {
  background-color: var(--secondary-color);
}
</style>
