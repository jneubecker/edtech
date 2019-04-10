<template>
  <div id="app">
    <div class="container-fluid h-100 p-0" v-if="this.isLoggedIn">
      <div class="row h-100">
        <Sidebar 
          ref="sideBar"
          v-bind:user="user" 
          v-bind:isLoggedIn="this.isLoggedIn" 
          v-on:group-selected="handleGroupSelected" 
          v-on:leave-group="handleLeaveGroup"
          v-on:friend-selected="handleFriendSelected"
          v-on:unfriend="handleUnfriend"
          v-on:self-selected="handleFriendSelected"
          v-bind:friends="friends" 
          v-on:show-friend-approval="showFriendApproval"
          v-on:show-join-group="showJoinGroup"
          v-on:goto-settings="showSettings"
          v-on:goto-group-settings="gotoGroupSettings"
        />
        <div class="col">
          <Header 
            v-bind:headerLabel="headerLabel" 
            v-on:logout="logout"
          />
          <div v-if="currentScreen == 'content'">
            <ContentFeed v-bind:group="selectedGroup" v-bind:content="content" v-bind:user="user"/>
            <ContentInput v-on:post="post"  v-bind:disabled="inputDisabled"/>
          </div>
          <div class= "other" v-if="currentScreen != 'content'">
            <FriendApproval 
              v-if="currentScreen == 'friend-approval'" 
              v-on:friend-approved="handleFriendApproved"
            />
            <JoinGroup 
              ref="joinGroups"
              v-if="currentScreen == 'join-group'" 
              v-on:group-joined="handleGroupJoined"
            />
            <Settings v-bind:user="user" v-if="currentScreen === 'settings'"/>
            <GroupSettings v-bind:currentUser="user" v-bind:group="selectedGroup" v-if="currentScreen === 'groupSettings'" v-on:group-updated="updateGroup"/>
          </div>
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
import FriendApproval from './components/friends/FriendApproval.vue';
import JoinGroup from './components/groups/JoinGroup.vue';
import Settings from './components/Settings.vue'
import GroupSettings from './components/GroupSettings.vue'

import axios from 'axios';

export default {
  name: 'app',
  components: {
    Sidebar,
    LoginPage,
    Header,
    ContentInput,
    ContentFeed,
    FriendApproval,
    JoinGroup,
    Settings,
    GroupSettings
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
      inputDisabled: true,
      friends: [],
      currentScreen: "",
      headerLabel: "",
      selectedGroup: {}
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
      this.headerLabel = "";
      this.currentScreen = "";
    },
    handleGroupSelected: function(group) {
      this.selectedEntity = group;
      if (this.selectedEntity.admins) {
        this.selectedGroup = this.selectedEntity;
      }
      this.currentScreen = 'content';
      this.headerLabel = this.selectedEntity.name;

      axios.get(`http://localhost:7777/invenio/group/post/${this.selectedEntity.id}`, {withCredentials: true})
        .then(response => (this.content = response.data));

      this.inputDisabled = false;
    },
    handleFriendSelected: function($event) {
      this.currentScreen = 'content';
      this.selectedGroup = null;
      this.selectedEntity = $event.target.dataset;
      this.headerLabel = this.selectedEntity.name;

      axios.get(`http://localhost:7777/invenio/user/post/${this.selectedEntity.id}`, {withCredentials: true})
        .then(response => (this.content = response.data));

      this.inputDisabled = true;
    },
    post: function(content) {
      if (content.trim() && this.selectedEntity.id) {
        const self = this;
        const data = {
          content: content
        }

        axios.post(`http://localhost:7777/invenio/group/post/${self.selectedEntity.id}`, data, {withCredentials: true}).then(function(response) {
          self.content.push(response.data);
        }); 
      }
    },
    handleUnfriend: function() {
      var self = this;

      if (self.selectedEntity.id === event.target.dataset.id) {
        self.content = [];

        self.selectedEntity = {
          name: "",
          id: ""
        }
      }

      event.stopPropagation();
    },
    handleLeaveGroup: function() {
      var self = this;

      if (self.selectedEntity.id === event.target.dataset.id) {
        self.content = [];

        self.selectedEntity = {
          name: "",
          id: ""
        }
      }

      const joinGroups = this.$refs.joinGroups;
      if (joinGroups) {
        joinGroups.addGroup(event.target.dataset);
      }

      event.stopPropagation();
    },
    handleFriendApproved: function(friend) {
      this.friends.push(friend);
    },
    showFriendApproval: function() {
      this.currentScreen = 'friend-approval';
      this.headerLabel = 'Friend Requests';
    },
    showJoinGroup: function() {
      this.currentScreen = 'join-group';
      this.headerLabel = 'Join Group';
    },
    handleGroupJoined: function(group) {
      this.$refs.sideBar.addGroup(group);
    },
    showSettings: function() {
      this.headerLabel = "Settings";
      this.currentScreen = 'settings';
    },
    gotoGroupSettings: function(group) {
      this.headerLabel = group.name;
      this.selectedGroup = group;
      this.currentScreen = "groupSettings";
      event.stopPropagation();
    },
    updateGroup: function(group) {
      this.selectedGroup = group;
    }
  },
  watch: {
    isLoggedIn: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal) {
          axios
            .get('http://localhost:7777/invenio/user/friend', {withCredentials: true})
            .then(response => (this.friends = response.data));
        }
      }
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
  flex-basis: 100%;
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
.other {
  height: calc(100vh - 50px);
  overflow: auto;
}
</style>
