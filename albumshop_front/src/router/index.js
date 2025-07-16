import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/view/LoginC.vue"
import Manage from "@/view/AlbumManage.vue"
import Home from "@/view/AlbumHome.vue"
import Register from "@/view/RegisterC.vue"
import Details from "@/view/AlbumDetails.vue"

// 注册 Vue Router 插件
Vue.use(Router)

// 定义路由配置
const router = new Router({
  routes: [
    {
      path: '/', // 路由路径
      name: 'login', // 路由名称
      component: Login, // 对应的 Vue 组件
    },
    {
      path: '/home',
      name: 'home',
      component: Home, // 主页组件
    },
    {
      path: '/manage',
      name: 'manage',
      component: Manage, // 专辑管理页面
      meta: { requiresAuth: true }, // 需要认证的路由元信息
    },
    {
      path: '/register',
      name: 'register',
      component: Register, // 用户注册页面
    },
    {
      path: '/details/:albumId',  // 动态路径参数 :albumId
      name: 'details',
      component: Details, // 专辑详情页面
      props: true  // 自动将路径参数作为 props 传递给组件
    },
  ]
});

// 导出路由配置
export default router;
