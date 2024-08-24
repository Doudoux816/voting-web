<template>
  <Navbar />
  <RouterView />
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import Navbar from './views/Navbar.vue';
</script>

<style scoped>

</style>
