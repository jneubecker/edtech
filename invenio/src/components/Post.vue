<template>
  <div class="post shadow-sm">
    <div class="post-content"  v-if="!beingEdited">
      <div class="post-header">
        <div class="post-info">
        </div>
        <i class="fas fa-edit edit-post mr-2" v-if="post.userId == user.id" :data-id="post.id" v-on:click="showEditPost"></i> 
        <i class="fas fa-trash delete-post" v-if="post.userId == user.id" :data-id="post.id" v-on:dblclick="deletePost"></i>
      </div>
      <div v-html="markedContent">
      </div>
    </div> 
    <EditPost v-bind:post="post" v-if="beingEdited" v-on:hide-edit="hideEditPost"/> 
  </div>
</template>

<script>
import axios from 'axios';
import EditPost from './EditPost.vue';

export default {
  name: 'Post',
  components: {
    EditPost
  },
  data: function () {
    return {
      beingEdited: false,
    }
  },
  props: ["post", "user"],
  methods: {
    showEditPost: function() {
      this.beingEdited = true;
    },
    hideEditPost: function() {
      this.beingEdited = false;
    },
    deletePost: function() {
      var self = this;
      const postId = self.post.id;  

      axios.delete(`http://localhost:7777/invenio/post/${postId}`, {withCredentials: true}).then(function() {
        self.$emit('delete-post', postId);
      });
    }
  }, computed: {
    markedContent: function() {
      return marked(this.post.content, { sanitize: true });
    }
  }
}
</script>

<style scoped>
.post {
  background-color: white;
  color: black;
  margin: 15px;
  margin-left: 100px;
  margin-right: 100px;
  padding: 10px;
  border-radius: 4px;
}
.post-edit {
  white-space: pre;
  color: var(--main-dark-color);
}
.edit-post {
  cursor: pointer;
}
.edit-post:hover {
  color: var(--secondary-color);
}
.delete-post {
  cursor: pointer;
}
.delete-post:hover {
  color: var(--secondary-text-color);
}
.post-header {
  display: flex;
  align-items: center;
}
.post-info {
  flex-basis: 100%;
}
.edit-post-input {
  height: initial;
}
</style>
