<template>
  <div class="group-list">
    <ul id="group-list" class="list-unstyled">
      <li v-bind:key="group.name" v-for="group in groups">
        {{ group.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GroupList',
  data () {
    return {
      groups: []
    }
  },
  mounted () {
    axios
      .get('http://localhost:7777/invenio/group')
      .then(response => (this.groups = response.data));
  }
}
</script>

<style scoped>

</style>
