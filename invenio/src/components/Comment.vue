<template>
  <div class="post-edit mt-3">
    <div class="input-group">
      <textarea class="form-control edit-post-input" v-model="comment" ref="editedContent" v-on:keyup="resizeTextArea"></textarea>
    </div>
    <button class="mt-2 btn btn-primary btn-sm" v-on:click="saveComment">Comment</button>
    <button class="ml-2 mt-2 btn btn-primary btn-sm" v-on:click="$emit('hide-comment')">Cancel</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Comment',
  props: ["postId"],
  data: function () {
    return {
      initialHeight: 0,
      comment: "",
    }
  },
  methods: {
    saveComment: function() {
      const self = this;
      const commentObj = {
        content: self.comment
      };

      axios.put(`http://localhost:7777/invenio/post/comment/${self.postId}`, commentObj, {withCredentials: true}).then(function(response) {
        self.$emit('comment-added', response.data);
      });
    },
    resizeTextArea: function() {
      const element = this.$refs.editedContent;
      element.style.height = '0px';
      const scrollHeight = element.scrollHeight;
      element.style.height = Math.max(this.initialHeight, scrollHeight) + 2 + 'px';
      return scrollHeight;
    }
  },
  mounted() {
    this.initialHeight = this.resizeTextArea();
  }
}
</script>

<style scoped>
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
.edit-post-input {
  height: initial;
  overflow-y: hidden;
}
</style>
