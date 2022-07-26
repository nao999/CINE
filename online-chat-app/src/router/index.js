import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const index = new Router({
    routes: [
        // {
        //     path:'/',
        //     redirect: '/login'
        //
        // },
        {
            path: '/login',
            component: () => import('@/pages/login/login')
        },
        {
            path: '/register',
            component: () => import('@/pages/register/register')
        },
        {
            path: '/',
            component: () => import('@/pages/index/index'),

        },
        {
            path: '/chat/:friendId',
            component: () => import('@/pages/chatRoom/chatRoom'),

        },
        {
            path: '/search',
            component: () => import('@/pages/search/search')
        },
        {
            path: '/searchContent',
            component: () => import('@/pages/search/searchContent')
        },
        {
            path: '/createGroup',
            component: () => import('@/pages/group/group')
        },
        {
            path: '/groupChat/:groupId',
            component: () => import('@/pages/chatRoom/groupChatRoom')
        },
        {
            path: '/groupMsg/:groupId',
            component: () => import('@/pages/group/groupMsg')
        },
        {
            path: '/editGroupName',
            component: () => import('@/pages/group/editGroupName')
        },
        {
            path: '/groupMembers',
            component: () => import('@/pages/group/groupMembers')
        },
        {
            path: '/addGroupMembers',
            component: () => import('@/pages/group/addGroupMembers')
        },
        {
            path: '/showUserMsg',
            component: () => import('@/pages/index/userMsg')
        }
        // {
        //     path: '/friend',
        //     component: () => import('@/views/friend/friend-chat'),
        // },
        // {
        //     path: '/test',
        //     component: () => import('@/components/testWs')
        // },
        // {
        //     path: '/application',
        //     component: () => import('@/components/friend/application')
        // }
    ]
})

export default index;

index.beforeEach((to, from, next) => {
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

