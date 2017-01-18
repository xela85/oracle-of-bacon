<template>
  <div id="app">
      <div class="menu">
        <h1 >Oracle Of Bacon</h1>
      </div>
    <actor-search id="actor-search" :on-search="consultOracle"></actor-search>
    <search-result id="result" :actor-name="actorName" :event-launcher="nodeClick"></search-result>
    <last-10-searches id="last-10-searches" class="box"></last-10-searches>
    <actor-info id="actor-info" :actor-name="nameSelected"></actor-info>
  </div>
</template>

<script>
import 'whatwg-fetch';
import ActorSearch from './components/ActorSearch';
import SearchResult from './components/SearchResult';
import Last10Searches from './components/Last10Searches';
import ActorInfo from './components/ActorInfo';

export default {
  name: 'app',
  data() {
    return {
      actorName: '',
      nameSelected: '',
    };
  },
  components: {
    ActorSearch,
    SearchResult,
    Last10Searches,
    ActorInfo,
  },
  methods: {
    consultOracle(actorName) {
      this.actorName = actorName;
    },
    nodeClick(nameSelected) {
      this.nameSelected = nameSelected;
    },
  },
};
</script>

<style>
  h3, p, ol {
    text-align: center;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #37403F;
  }

  h1 {
    text-align: left;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #bbcba4;
    padding-left: 10px;
  }

  .box {
    padding: 0px 5px 0px 5px;
    margin: 30px;
    background-color:#e8eaea;
    border: solid;
    width: -webkit-fit-content;
    height: -webkit-fit-content;
    width: -moz-fit-content;
    height: -moz-fit-content;
    border:none;
    margin-left: auto;
    margin-right: auto;
  }

  .menu {
    background-color: #5A847E;
    color: #fbffc3;
    position: absolute;
    width: 100%;
    margin: 0px Opx Opx 1Opx;
    top: 0px;
    right: 0px;
  }

  body {
    background: url(./assets/kevin-bacon-background-left.png) no-repeat;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
  }

  #last-10-searches {
    position: absolute;
    background-color: #fbf3c2;
    top: 220px;
    right: 50px;
  }

  #actor-info {
    position: absolute;
    background-color: #fbf3c2;
    top: 85px;
    left: 0px;
    width: 240px;
    height: 720px;
    overflow: hidden;
    border:none;
    visibility: hidden;
  }

  #connected-user {
    position: absolute;
    top: 20px;
    right: 20px;
  }

  *:focus {
    outline: none;
  }

  body {
    margin-top: 120px;
  }

</style>
