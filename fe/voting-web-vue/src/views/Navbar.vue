<template>
    <nav class="navbar">
      <!-- <div class="navbar-container"> -->
        <div class="navbar-brand">
          <router-link to="/" class="navbar-logo">Logo</router-link>
        </div>
        <ul class="navbar-menu">
          <li class="navbar-item">
            <router-link to="/" class="navbar-link">首頁</router-link>
          </li>
          <li class="navbar-item">
            <router-link to="/about" class="navbar-link">關於我們</router-link>
          </li>
          <li class="navbar-item">
            <router-link to="/services" class="navbar-link">服務</router-link>
          </li>
          <li class="navbar-item">
            <router-link to="/contact" class="navbar-link">聯絡我們</router-link>
          </li>
        </ul>
        <div class="navbar-toggle" @click="toggleMenu">
          <span class="navbar-toggle-icon"></span>
          <span class="navbar-toggle-icon"></span>
          <span class="navbar-toggle-icon"></span>
        </div>
      <!-- </div> -->
    </nav>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const isMenuOpen = ref(false);
  
  const toggleMenu = () => {
    isMenuOpen.value = !isMenuOpen.value;
  };
  </script>
  
  <style scoped>
  .navbar {
    background-color: #00a19b;
    padding: 10px 20px;
    color: #fff;
  }
  
  .navbar-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .navbar-brand .navbar-logo {
    font-size: 24px;
    color: #fff;
    text-decoration: none;
  }
  
  .navbar-menu {
    list-style: none;
    display: flex;
    margin: 0;
    padding: 0;
  }
  
  .navbar-item {
    margin: 0 15px;
  }
  
  .navbar-link {
    color: #fff;
    text-decoration: none;
    font-size: 18px;
  }
  
  .navbar-link:hover {
    text-decoration: underline;
  }
  
  .navbar-toggle {
    display: none;
    flex-direction: column;
    cursor: pointer;
  }
  
  .navbar-toggle-icon {
    width: 30px;
    height: 3px;
    background-color: #fff;
    margin: 3px 0;
  }
  
  /* Responsive design */
  @media (max-width: 768px) {
    .navbar-menu {
      display: none;
      flex-direction: column;
      width: 100%;
      position: absolute;
      top: 60px;
      left: 0;
      background-color: #00a19b;
    }
  
    .navbar-menu.active {
      display: flex;
    }
  
    .navbar-toggle {
      display: flex;
    }
  }
  </style>
  