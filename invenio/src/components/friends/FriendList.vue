<template>
  <div class="friend-list">
    <ul id="friend-list" class="list-unstyled">
      <li v-bind:key="friend.id" v-for="friend in friends">
        <div class="friend-list-element" :data-id="friend.id" :data-name="friend.displayName" v-on:click="$emit('friend-selected', $event)">
          {{ friend.displayName }}
          <i :data-id="friend.id" v-on:click="removeFriend" class="fas fa-minus-circle pull-right remove-friend clickable"></i>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'FriendList',
  props: ["friends"],
  methods: {
    removeFriend: function() {
     
    }
  }
}
</script>

<style scoped>
.friend-list-element {
  padding: 5px 15px 0px 15px;
  cursor: pointer;
}
.friend-list-element:hover {
  background-color: var(--accent-color);
}
</style>
