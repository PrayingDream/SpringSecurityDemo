import { createRouter, createWebHashHistory } from 'vue-router'
import LoginView from '@/views/login/LoginView.vue'
import AddView from "@/views/addView/AddView.vue";
import AllView from "@/views/allView/AllView.vue";
import SelectView from "@/views/selectView/SelectView.vue";
import NotFound from "@/views/404/NotFound.vue";


const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login_index',
    component: LoginView
  },
  {
    path: '/add',
    name: 'add_index',
    component: AddView,
  },
  {
    path: '/all',
    name: 'all_index',
    component: AllView,
  },
  {
    path: '/select',
    name: 'select_index',
    component: SelectView,
  },
  {
    path: '/404',
    name: 'not_found_index',
    component: NotFound,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
