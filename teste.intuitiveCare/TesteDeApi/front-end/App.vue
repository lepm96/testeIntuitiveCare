<template>
  <div id="app">
    <OperadoraList />
  </div>
</template>

<script>
import OperadoraList from './components/OperadoraList.vue';

export default {
  name: 'App',
  components: {
    OperadoraList
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
</style>