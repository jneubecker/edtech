<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.id" v-for="group in groups">
        {{ group.name }}
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  name: 'GroupList',
  props: ["groups"]
}
</script>

<style scoped>

</style>
