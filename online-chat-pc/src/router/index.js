import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'// nprogress样式文件

Vue.use(Router)

const router = new Router({
    routes: [
        // {
        //     path:'/',
        //     redirect: '/login'
        //
        // },
        {
            path: '/login',
            component: () => import('@/views/login')
        },
        {
            path: '/',
            component: () => import('@/views/chat/chat-room'),

        },
        {
            path: '/friend',
            component: () => import('@/views/friend/friend-chat'),
        },
        {
            path: '/test',
            component: () => import('@/components/testWs')
        },
        {
            path: '/application',
            component: () => import('@/components/friend/application')
        }
    ]
})

export default router;

router.beforeEach((to, from, next) => {
    // 开启进度条
    NProgress.start();
    console.log(to);
    // to表示将要访问的路径
    // from表示从哪个路径跳转而来
    // next是一个函数，表示放行 next()放行，next('/login') 强制跳转
    if (to.path === '/login') return next();
    // 获取token
    const tokenStr = window.localStorage.getItem('token');
    if (!tokenStr) return next('/login');
    next();
});

//当路由跳转结束后
router.afterEach(() => {
    // 关闭进度条
    NProgress.done()
})
