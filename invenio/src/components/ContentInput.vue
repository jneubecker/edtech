<template>
  <div class="footer row text-center">
    <div class="col">
      <div class="input-group">
        <textarea class="form-control content-input"></textarea>
        <div class="input-group-append">
          <span class="post-button input-group-text fas fa-plus"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ContentInput',
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
}
.post-button:hover {
  background-color: var(--accent-color);
}
.input-group {
  padding-left: 100px;
  padding-right: 100px;
}
textarea {
  border: none;
  overflow: auto;
  outline: none;
  box-shadow: none;
}
textarea:focus {
  border: none;
  overflow: auto;
  outline: none;
  box-shadow: none;
}
</style>
