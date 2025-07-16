import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Login from "@/view/LoginC.vue"
import Manage from "@/view/AlbumManage.vue";
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from "axios";
import Register from "@/view/RegisterC.vue";
import Home from "@/view/AlbumHome.vue";
import Details from "@/view/AlbumDetails.vue";

// 注册 ElementUI 插件
Vue.use(ElementUI)

// 注册 VueRouter 插件
Vue.use(VueRouter)

// 定义路由配置
const routes = [
    {
        path: "/login", // 登录页路径
        name: "Login", // 路由名称
        component: Login, // 登录页组件
    },
    {
        path: "/manage", // 管理页路径
        name: "Manage", // 路由名称
        component: Manage, // 管理页组件
    },
    {
        path: "/", // 根路径
        redirect: "/login" // 重定向到登录页
    },
    {
        path: '/register', // 注册页路径
        name: 'register', // 路由名称
        component: Register, // 注册页组件
    },
    {
        path: '/home', // 首页路径
        name: 'home', // 路由名称
        component: Home, // 首页组件
    },
    {
        path: '/details/:albumId', // 专辑详情页路径，动态路径参数 :albumId
        name: 'details', // 路由名称
        component: Details, // 专辑详情页组件
        props: true // 将路径参数作为 props 传递给组件
    },
];

// 创建 VueRouter 实例并启用历史模式
const Router = new VueRouter({
    routes, // 路由配置
    mode: 'history' // 使用 HTML5 历史模式（避免 hash 模式中的 "#"）
});

// Axios 请求拦截器，全局设置 Token
axios.interceptors.request.use(function (config) {
    let token = sessionStorage.getItem('token') // 从 sessionStorage 获取 Token
    if (token) {
        config.headers['token'] = token // 在请求头中添加 Token
    }
    return config
})

// 创建 Vue 实例
new Vue({
    router: Router, // 挂载路由实例
    render: h => h(App), // 渲染根组件
}).$mount('#app'); // 挂载到页面上的 DOM 元素