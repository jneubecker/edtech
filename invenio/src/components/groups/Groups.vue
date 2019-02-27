<template>
  <div id="groups-container">
    <GroupsHeader/>
    <GroupList/>
  </div>
</template>

<script>
import GroupList from './GroupList.vue'
import GroupsHeader from './GroupsHeader.vue'

export default {
  name: 'Groups',
  components: {
    GroupList,
    GroupsHeader
  }
}
</script>

<style scoped>

</style>
