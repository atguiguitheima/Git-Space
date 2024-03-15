// 此文件专门负责项目的路由
 import VueRouter from "vue-router" 

// 引入组件
import Login from '../views/login/LoginView.vue'
import Register from '../views/register/RegisterView.vue'
import Home from '../views/home/HomeView.vue'
import AdminHome from '../views/home/AdminHome.vue'
import AdminLogin from '../views/login/AdminLoginView.vue'
import Create from '../views/function/CreateView.vue'
import Update from '../views/function/UpdateView.vue'
import Answer from '../views/function/AnswerView.vue'
/* import MainCreate from '../views/function/MainCreateView.vue' */
import { Message } from "element-ui";

// 创建并暴露一个路由器
 const router = new VueRouter({
    mode: 'history',    // 路由模式，该模式不会在地址中显示井号#
    routes: [
        {
            path: '/',          // 路径
            redirect: '/login'  // 重定向
        },
        {
            path: '/login',     // 路径
            // redirect:'/home',
            component: Login    // 跳转到的组件
        },
        {
            path: '/register',     // 路径
            component: Register    // 跳转到的组件
        },
        {
            path: '/home',     // 路径
            component: Home    // 跳转到的组件
        },
        {
            path:'/adminlogin',
            component:AdminLogin
        },
         {
            path:'/create',
            component:Create,
        },
        {
            path:'/adminhome',
            component:AdminHome,
        },
        {
            path:'/update',
            component:Update,
        },
        {
            path:'/answer',
            component:Answer,
        },
      /*   {
            path:'/maincreate',
            component:MainCreate,
        }, */
    ]
}) 



// 导航守卫，前置处理
router.beforeEach((to, from, next) => {
    let isAuthenticated = !!sessionStorage.getItem('userInfo')
    // 如果路由要跳转到除了登录和注册的界面的话就判断是否已经登录，如果没有登录就强制跳到登录界面
    if (to.path !== '/login' && to.path !== '/register'&& to.path !== '/adminlogin' && !isAuthenticated) {
        next({ path: '/login',query:{redirect:to.fullPath} })
        /* Message({
            message: '请先登录！',
            type: "warning",
        }); */
    } else next()
}) 

export default router