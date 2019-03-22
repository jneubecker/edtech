<template>
  <div class="footer row text-center">
    <div class="col">
      <div class="input-group">
        <textarea class="form-control content-input" v-model="content" :disabled="disabled"></textarea>
        <div class="input-group-append">
          <span class="post-button input-group-text fas fa-plus" v-on:click="handlePost"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ContentInput',
  props: ["disabled"],
  data: function () {
    return {
      content: ""
    }
  },
  methods: {
    handlePost: function() {
      this.$emit('post', this.content);
      this.content = "";
    }
  }
}
</script>

<style scoped>
.footer {
  height: 250px;
}
.content-input {
  height: 150px;
}
.post-button {
  width: 50px;
  line-height: 148px;
  padding: 0px;
  cursor: pointer;
  background-color: var(--main-dark-color);
  color: var(--main-light-color);
  border: solid 1px var(--main-dark-color);
}
.post-button:hover {
  background-color: var(--accent-color);
}
.input-group {
  padding-left: 100px;
  padding-right: 100px;
  margin-top: 30px;
}
textarea {
  overflow: auto;
  outline: none;
  box-shadow: none;
  border: solid 1px var(--main-dark-color);
}
textarea:focus {
  border: solid 1px var(--main-dark-color);
  overflow: auto;
  outline: none;
  box-shadow: none;
}
</style>
