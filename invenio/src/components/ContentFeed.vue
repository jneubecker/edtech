<template>
  <div class="row content-feed">
    <div class="col">
      <div v-bind:key="post.id" v-for="post in content">
        <div class="post shadow-sm">
          <Post v-bind:post="post" v-bind:user="user" v-on:delete-post="removePost"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Post from './Post.vue'

export default {
  name: 'ContentFeed',
  props: ["content", "user"],
  components: {
    Post
  },
  methods: {
    removePost: function(postId) {
      this.content.splice(this.content.findIndex(function(i){
        return i.id === postId;
      }), 1);
    }
  }
}
</script>

<style scoped>
.content-feed {
  height: calc(100vh - 300px);
  overflow: auto;
}
.post {
  background-color: white;
  color: black;
  margin: 15px;
  margin-left: 100px;
  margin-right: 100px;
  padding: 10px;
  border-radius: 4px;
}
</style>
