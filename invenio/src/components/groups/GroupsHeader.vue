<template>
  <div id="groups-header">
    Groups <i v-on:click="createGroup" class="fas fa-plus-circle"></i>
    <CreateGroup/>
  </div>
</template>

<script>
import CreateGroup from './CreateGroup.vue'

export default {
  name: 'GroupsHeader',
  components: {
      CreateGroup
  },
  methods: {
      createGroup: function() {
          console.log("clicked")
      }
  }
}
</script>

<style scoped>

</style>
