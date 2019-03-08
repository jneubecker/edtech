<template>
  <div id="app">
    <div class="container-fluid h-100 p-0" v-if="this.isLoggedIn">
      <div class="row h-100">
        <Sidebar v-on:group-selected="handleGroupSelected"/>
        <div class="col">
          <Header v-bind:selected-group="selectedGroup" v-on:logout="logout"/>
          <ContentFeed/>
          <ContentInput/>
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
      selectedGroup: ""
    }
  },
  methods: {
    setLoggedIn: function (response) {
      this.isLoggedIn = true;
    },
    logout: function() {
      FB.logout();
      this.isLoggedIn = false;
    },
    handleGroupSelected: function($event) {
      console.log($event.target);
      this.selectedGroup = $event.target.dataset.name;
    }
  },
  beforeCreate() {
    const self = this;

    window.fbAsyncInit = function() {
      FB.init({
        appId      : '325673938058158',
        cookie     : true,
        xfbml      : true,
        version    : 'v3.2'
      });
        
      FB.AppEvents.logPageView();  
        
      FB.getLoginStatus(function(response) {
         if (response.status === 'connected') {
           FB.api('/me', function(response) {
             // TODO make this more secure
              document.cookie = "userId=" + response.id + ";port=7777;";
              self.isLoggedIn = true;
            });   
         }
      });
    };

    (function(d, s, id){
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) {return;}
      js = d.createElement(s); js.id = id;
      js.src = "https://connect.facebook.net/en_US/sdk.js";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
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
