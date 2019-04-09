<template>
  <div class="settings">
    <div class="setting">
      <div class="setting-label">Personal Feed View Setting</div>
      <div class="setting-card">
        <div class="radio">
          <label><input value="public" type="radio" name="optradio" class="mr-2" v-model="settings.feedPrivacy" v-on:change="updateSettings">Public</label>
        </div>
        <div class="radio">
          <label><input value="private" type="radio" name="optradio" class="mr-2" v-model="settings.feedPrivacy" v-on:change="updateSettings">Private</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Settings',
  props: ["user"],
  data: function() {
    return {
      settings: this.user.settings
    }
  },
  methods: {
    updateSettings: function() {
      const self = this;
      const settings = self.settings;
      axios.put(`http://localhost:7777/invenio/user/settings`, settings, {withCredentials: true}).then(function() {
        self.$emit('setting-updated', settings);
      });
    }
  }
}
</script>

<style scoped>
.setting {
  color: black;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}
.setting-card {
  padding: 10px;
  border-radius: 4px;
  background-color: white;
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.075);
}
.setting-label {
  margin: 15px 0px 5px 0px;
}
</style>
