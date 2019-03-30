<template>
  <div class="row content-feed">
    <div class="col">
      <div v-bind:key="post.id" v-for="post in markedContent">
        <div class="post">
          <div class="post-content"  v-if="beingEdited != post.id">
            <div class="post-header">
              <div class="post-info">
              </div>
              <i class="fas fa-edit edit-post" v-if="post.userId == user.id" :data-id="post.id" v-on:click="showEditPost"></i> 
            </div>
            <div v-html="post.markedContent">
            </div>
          </div>
          <div class="edit-post" v-if="beingEdited == post.id">
            <div class="input-group">
              <div contenteditable class="form-control edit-post-input" v-html="post.content"></div>
            </div>
            <button class="mt-2 btn btn-primary btn-sm" :data-id="post.id">Save</button>
            <button class="ml-2 mt-2 btn btn-primary btn-sm" :data-id="post.id">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ContentFeed',
  props: ["content", "user"],
  data: function () {
    return {
      beingEdited: ""
    }
  },
  computed: {
    markedContent: function() {
      return this.content.map(post => { 
        post.markedContent = marked(post.content, { sanitize: true });
        return post;
      });
    }
  },
  methods: {
    showEditPost: function() {
      this.beingEdited = event.target.dataset.id;
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
.edit-post {
  white-space: pre;
  color: var(--main-dark-color);
}
.edit-post:hover {
  color: var(--secondary-color);
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
