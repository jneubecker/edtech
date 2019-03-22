<template>
  <div id="app">
    <div class="container-fluid h-100 p-0" v-if="this.isLoggedIn">
      <div class="row h-100">
        <Sidebar v-bind:user="user" 
          v-bind:isLoggedIn="this.isLoggedIn" 
          v-on:group-selected="handleGroupSelected" 
          v-on:leave-group="handleLeaveGroupOrUnfriend"
          v-on:friend-selected="handleFriendSelected"
          v-on:unfriend="handleLeaveGroupOrUnfriend"
          v-on:self-selected="handleFriendSelected"
        />
        <div class="col">
          <Header v-bind:selected-group="selectedEntity.name" v-on:logout="logout"/>
          <ContentFeed v-bind:content="content"/>
          <ContentInput v-on:post="post"  v-bind:disabled="inputDisabled"/>
        </div>
      </div>
    </div>
    <LoginPage v-if="!this.isLoggedIn" v-on:logged-in="setLoggedIn"/>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'
import Header from './components/Header.vue'
import LoginPage from './components/LoginPage.vue'
import ContentFeed from './components/ContentFeed.vue'
import ContentInput from './components/ContentInput.vue'
import axios from 'axios';

export default {
  name: 'app',
  components: {
    Sidebar,
    LoginPage,
    Header,
    ContentInput,
    ContentFeed
  },
  data: function () {
    return {
      isLoggedIn: false,
      selectedEntity: {
        name: "",
        id: ""
      },
      content: [],
      user: {},
      inputDisabled: true
    }
  },
  methods: {
    setLoggedIn: function (response) {
      document.cookie = "userId=" + response.id + ";port=7777;";
      this.user = response;
      this.isLoggedIn = true;
    },
    logout: function() {
      this.isLoggedIn = false;
      this.content = [];
      this.user = {};
      this.selectedEntity = {
        name: "",
        id: ""
      };
    },
    handleGroupSelected: function($event) {
      this.selectedEntity = $event.target.dataset;

      axios.get(`http://localhost:7777/invenio/group/post/${this.selectedEntity.id}`, {withCredentials: true})
        .then(response => (this.content = response.data));

      this.inputDisabled = false;
    },
    handleFriendSelected: function($event) {
      this.selectedEntity = $event.target.dataset;

      axios.get(`http://localhost:7777/invenio/user/post/${this.selectedEntity.id}`, {withCredentials: true})
        .then(response => (this.content = response.data));

      this.inputDisabled = true;
    },
    post: function(content) {
      if (content.trim() && this.selectedEntity.id) {
        const self = this;
        const data = {
          content: marked(content, { sanitize: true })
        }

        axios.post(`http://localhost:7777/invenio/group/post/${self.selectedEntity.id}`, data, {withCredentials: true}).then(function(response) {
          self.content.push(response.data);
        }); 
      }
    },
    handleLeaveGroupOrUnfriend: function() {
      var self = this;

      if (self.selectedEntity.id === event.target.dataset.id) {
        self.content = [];

        self.selectedEntity = {
          name: "",
          id: ""
        }
      }

      event.stopPropagation();
    }
  }
}
</script>

<style>
:root {
  --main-light-color: #E6E6E6;
  --secondary-color: #DFB007;
  --accent-color: #347B98;
  --main-dark-color: #092834;
  --secondary-text-color: #C21460;
}
html {
  height: 100%;
}
body {
  height: 100%;
  background-color: var(--main-light-color);
}
h2 {
  color: var(--accent-color);
  font-weight: 600;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: var(--main-light-color);
  height: 100%;
  box-sizing: border-box;
}
.clickable {
  cursor: pointer;
}
.clickable:hover {
  filter: brightness(85%);
}
.pull-right {
  float: right;
}
.overlay {
  position: fixed; /* Sit on top of the page content */
  display: block; /* Hidden by default */
  width: 100%; /* Full width (cover the whole page) */
  height: 100%; /* Full height (cover the whole page) */
  top: 0; 
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5); /* Black background with opacity */
  z-index: 2; /* Specify a stack order in case you're using a different order for other elements */
}
.form-container {
  max-height: 500px;
  overflow-y: auto;
}
.dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 15px;
  width: 600px;
  color: var(--main-dark-color);
  background-color: var(--main-light-color);
  overflow-y: auto;
}
.tl-margin {
  margin-top: 15px;
  margin-left: 8px;
}
.btn {
  background-color: var(--accent-color);
  border-color: var(--accent-color);
  white-space: nowrap;
}
.btn-primary:hover {
  background-color: var(--secondary-text-color);
  border-color: var(--secondary-text-color);
}
.list-header {
  color: var(--secondary-color);
}
.row {
  margin: 0px;
}
.col {
  padding: 0px;
}
textarea {
  resize: none;
}
</style>
