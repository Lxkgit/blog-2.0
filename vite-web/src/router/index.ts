import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import { ElMessage } from 'element-plus'
import Nprogress from 'nprogress'
import { systemStore } from '@/store/system'

const router = createRouter({
    // history: createWebHashHistory(),  // hash模式，
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: () => import('@/views/home/Home.vue'),
            meta: {
                title: 'Home',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/search',
            name: 'Search',
            component: () => import('@/views/public/Search.vue'),
            meta: {
                title: '搜索',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/link',
            name: 'Link',
            component: () => import('@/views/public/Link.vue'),
            meta: {
                title: '友情链接',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/applyLink',
            name: 'ApplyLink',
            component: () => import('@/views/public/ApplyLink.vue'),
            meta: {
                title: '申请友链',
                keepAlive: true,
                isAuth: false
            },
        },
        {
            path: '/category/:id',
            name: 'Category',
            component: () => import('@/views/article/Category.vue'),
            meta: {
                title: '文章分类',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/tag/:id',
            name: 'Tag',
            component: () => import('@/views/article/Tag.vue'),
            meta: {
                title: '标签',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/detail/article/:id',
            name: 'ArticleDetail',
            component: () => import('@/views/article/ArticleDetail.vue'),
            meta: {
                title: '文章正文',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/catalog/:id',
            name: 'Catalog',
            component: () => import('@/views/note/Catalog.vue'),
            meta: {
                title: '笔记目录',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/detail/section/:id',
            name: 'SectionDetail',
            component: () => import('@/views/note/SectionDetail.vue'),
            meta: {
                title: '笔记正文',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/classify',
            name: 'Classify',
            component: () => import('@/views/public/Classify.vue'),
            meta: {
                title: '归档',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/message',
            name: 'Message',
            component: () => import('@/views/public/Message.vue'),
            meta: {
                title: '留言板',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/about',
            name: 'About',
            component: () => import('@/views/public/About.vue'),
            meta: {
                title: '关于',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/loginRegister',
            name: 'LoginRegister',
            component: () => import('@/views/personal/LoginRegister.vue'),
            meta: {
                title: '登录&注册',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/OAuth/:platform',
            name: 'OAuth',
            component: () => import('@/views/personal/OAuth.vue'),
            meta: {
                title: '第三方登录授权页',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/setPassword',
            name: 'SetPassword',
            component: () => import('@/views/personal/SetPassword.vue'),
            meta: {
                title: '重置密码',
                keepAlive: false,
                isAuth: false
            }
        },
        {
            path: '/admin',
            name: 'AdminIndex',
            component: () => import('@/views/personal/Personal.vue'),
            children: [
                {
                    path: "",
                    redirect: "/admin/index",
                    isAuth: true,
                    keepAlive: false,
                },
                {
                    path: 'index',
                    name: 'AdminIndex',
                    component: () => import('@/views/personal/MyIndex.vue'),
                    meta: {
                        title: '个人中心',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'article',
                    name: 'Article',
                    component: () => import('@/views/admin/article/AdminArticle.vue'),
                    meta: {
                        title: '文章管理',
                        keepAlive: false,
                        isAuth: true,
                        
                    },
                },
                {
                    path: 'article/editor',
                    name: 'ArticleEditor',
                    component: () => import('@/views/admin/article/ArticleEditor.vue'),
                    meta: {
                        title: '文章编辑',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'role',
                    name: 'Role',
                    component: () => import('@/views/admin/role/AdminRole.vue'),
                    meta: {
                        title: '角色管理',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'user',
                    name: 'User',
                    component: () => import('@/views/admin/user/AdminUser.vue'),
                    meta: {
                        title: '用户管理',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'diary',
                    name: 'Diary',
                    component: () => import('@/views/admin/diary/AdminDiary.vue'),
                    meta: {
                        title: '日记管理',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'doc',
                    name: 'Doc',
                    component: () => import('@/views/admin/doc/AdminDoc.vue'),
                    meta: {
                        title: '笔记管理',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
               
            ]
        },
        {
            path: '/404',
            name: '404',
            component: () => import('@/views/404.vue'),
            meta: {
                title: '404-页面找不到',
                keepAlive: true,
                isAuth: false
            }
        },
        {
            path: '/:pathMatch(.*)',
            redirect: '/404'
        },
    ]
})


// 路由导航守卫
router.beforeEach((to, from, next) => {
    const store = systemStore()
    Nprogress.start()
    if (to.path) {
        if (window._hmt) {
            window._hmt.push()
        }
    }
    next()
})

router.afterEach(() => {
    Nprogress.done()
})

export default router;