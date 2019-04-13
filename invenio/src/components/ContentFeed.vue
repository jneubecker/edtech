<template>
  <div class="row content-feed">
    <div v-if="group">
      <div class="position-absolute w-100">
        <i class="fas fa-cog settings clickable" v-if="showSettings" v-on:click="gotoSettings(group)"></i>
      </div>
    </div>
    <div class="col">
      <div v-bind:key="post.id" v-for="post in content">
        <div class="post shadow-sm">
          <Post v-bind:group="group" v-bind:post="post" v-bind:user="user" v-on:delete-post="removePost"/>
        </div>
      </div>
      <div class="empty-feed" v-if="content.length === 0">
        <h1>There are no posts or this feed is private</h1>
      </div>
    </div>
  </div>
</template>

<script>
import Post from './Post.vue'

export default {
  name: 'ContentFeed',
  props: ["content", "user", "group"],
  components: {
    Post
  },
  methods: {
    removePost: function(postId) {
      this.content.splice(this.content.findIndex(function(i){
        return i.id === postId;
      }), 1);
    },
    gotoSettings: function(group) {
      this.$emit("goto-group-settings", group);
    }
  },
  computed: {
    showSettings: function() {
      const group = this.group;
      return group && (group.groupSettings.memberApprovalPolicy === 'anyone' || group.admins.includes(this.user.id) || (group.groupSettings.memberApprovalPolicy === 'moderator' && group.moderators.includes(this.user.id)));
    }
  }
}
</script>

<style scoped>
.content-feed {
  height: calc(100vh - 300px);
  overflow: auto;
  color: black;
}
.post {
  background-color: white;
  margin: 15px;
  margin-left: 100px;
  margin-right: 100px;
  padding: 10px;
  border-radius: 4px;
}
.empty-feed {
  margin: 100px;
  color: grey;
}
.settings {
  font-size: 25px;
  color:var(--accent-color);
  position: absolute;
  top: 15px;
  right: 25px;
  z-index: 100;
}
</style>
