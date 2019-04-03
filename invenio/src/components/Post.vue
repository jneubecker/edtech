<template>
  <div>
    <div class="post-content"  v-if="!beingEdited">
      <div class="post-header">
        <div class="post-info">
        </div>
        <i class="fas fa-comment post-icon mr-2" v-on:click="showAddComment"></i>
        <i class="fas fa-edit post-icon mr-2" v-if="post.userId == user.id" :data-id="post.id" v-on:click="showEditPost"></i> 
        <i class="fas fa-trash delete-post mr-2" v-if="post.userId == user.id" :data-id="post.id" v-on:dblclick="deletePost"></i>
      </div>
      <div v-html="markedContent">
      </div>
    </div> 
    <EditPost v-bind:post="post" v-if="beingEdited" v-on:hide-edit="hideEditPost"/>
    <div class="comments ml-3 mt-2" v-if="post.subPosts.length > 0">
      <div class="comment" v-bind:key="post.id" v-for="post in post.subPosts">
        <Post v-bind:post="post" v-bind:user="user" v-on:delete-post="removeComment"/>
      </div>
    </div>
    <Comment v-bind:postId="post.id" v-if="isCommentInputVisible" v-on:comment-added="handleAddComment" v-on:hide-comment="hideAddComment"/>
  </div>
</template>

<script>
import axios from 'axios';
import EditPost from './EditPost.vue';
import Comment from './Comment.vue'

export default {
  name: 'Post',
  components: {
    EditPost,
    Comment
  },
  data: function () {
    return {
      beingEdited: false,
      isCommentInputVisible: false
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
    },
    handleAddComment: function(comment) {
      this.post.subPosts.push(comment)
      this.isCommentInputVisible = false;
    },
    hideAddComment: function() {
      this.isCommentInputVisible = false;
    },
    showAddComment: function() {
      this.isCommentInputVisible = true;
    },
    removeComment: function(postId) {
      this.post.subPosts.splice(this.post.subPosts.findIndex(function(i){
        return i.id === postId;
      }), 1);
    }
  }, computed: {
    markedContent: function() {
      return marked(this.post.content, { sanitize: true });
    }
  }
}
</script>

<style scoped>
.post-edit {
  white-space: pre;
  color: var(--main-dark-color);
}
.post-icon {
  cursor: pointer;
}
.post-icon:hover {
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
.comments {
  border-left: solid 1px #cccccc;;
}
.comment {
  padding-top: 5px;
  padding-left: 10px;
}
</style>
