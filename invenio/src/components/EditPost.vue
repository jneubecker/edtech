<template>
  <div class="post-edit">
    <div class="input-group">
      <textarea class="form-control edit-post-input" :value="post.content" ref="editedContent" v-on:keyup="resizeTextArea"></textarea>
    </div>
    <button class="mt-2 btn btn-primary btn-sm" :data-id="post.id" v-on:click="saveEditPost">Save</button>
    <button class="ml-2 mt-2 btn btn-primary btn-sm" :data-id="post.id" v-on:click="$emit('hide-edit')">Cancel</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'EditPost',
  components: {
  },
  props: ["post"],
  data: function () {
    return {
      initialHeight: 0,
    }
  },
  methods: {
    saveEditPost: function() {
      const self = this;
      const newContent = self.$refs.editedContent.value;
      self.post.content = newContent;

      axios.put(`http://localhost:7777/invenio/post`, self.post, {withCredentials: true}).then(function(response) {
        self.$emit('hide-edit');
      });
    },
    resizeTextArea: function() {
      const element = this.$refs.editedContent;
      element.style.height = '0px';
      const scrollHeight = element.scrollHeight;
      element.style.height = Math.max(this.initialHeight, scrollHeight) + 2 + 'px';
      return scrollHeight;
    }
  }, computed: {
    markedContent: function() {
      return marked(this.post.content, { sanitize: true });
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
