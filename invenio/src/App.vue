<template>
  <div id="app">
    <Sidebar v-if="this.isLoggedIn"/>
    <LoginPage v-if="!this.isLoggedIn" v-on:logged-in="setLoggedIn"/>
    <button class="btn btn-primary" v-if="this.isLoggedIn" v-on:click="logout">Log Out</button>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'
import LoginPage from './components/LoginPage.vue'

export default {
  name: 'app',
  components: {
    Sidebar,
    LoginPage
  },
  data: function () {
    return {
      isLoggedIn: false
    }
  },
  methods: {
    setLoggedIn: function (response) {
      this.isLoggedIn = true;
    },
    logout: function() {
      FB.logout();
      this.isLoggedIn = false;
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
            self.isLoggedIn = true;
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
html {
  height: 100%;
}
body {
  height: 100%;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: lightgray;
  height: 100%;
}
.form-container {
  background-color: white;
  display: inline-block;
}
.clickable {
  cursor: pointer;
}
.pull-right {
  float: right;
}
</style>
