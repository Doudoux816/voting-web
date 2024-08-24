// export default router
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      name: 'pages-voteItemList-link',
      path: '/voteItemList',
      component: () => import('../views/VoteItemList.vue')
    },
    {
      name: 'pages-editVoteItem-link',
      path: '/editVoteItem',
      component: () => import('../views/EditVoteItem.vue')
    }
    ,
  ]
})

export default router

