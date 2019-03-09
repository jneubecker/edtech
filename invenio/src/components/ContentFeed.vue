<template>
  <div class="row content-feed">
    <div class="col">
      <div v-bind:key="post.id" v-for="post in content">
        <div v-html="post.content" class="post">
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ContentFeed',
  props: ["content"]
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
