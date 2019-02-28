<template>
  <div id="groups-header">
    Groups <i v-on:click="showCreateGroup" class="fas fa-plus-circle clickable pull-right"></i>
    <CreateGroup v-if="this.isCreateGroupVisible" v-on:group-created="handleGroupCreate" v-on:cancel-create="hideCreateGroup"/>
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
      showCreateGroup: function() {
        this.isCreateGroupVisible = true;
      },
      hideCreateGroup: function() {
        this.isCreateGroupVisible = false;
      },
      handleGroupCreate: function(group) {
        this.isCreateGroupVisible = false;
        this.$emit('group-created', group);
      }
  },
  data: function () {
    return {
      isCreateGroupVisible: false
    }
  }
}
</script>

<style scoped>

</style>
