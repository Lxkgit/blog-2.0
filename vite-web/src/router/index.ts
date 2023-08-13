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
            path: '/home',
            name: 'Home1',
            component: () => import('@/views/home/Home.vue'),
            meta: {
                title: 'Home',
                keepAlive: true,
                isAuth: false
            }
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
            path: '/document',
            name: 'Document',
            component: () => import('@/views/note/Document.vue'),
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
            path: '/loginRegister',
            name: 'LoginRegister',
            component: () => import('@/views/personal/LoginRegister.vue'),
            meta: {
                title: '登录&注册',
                keepAlive: false,
                isAuth: false
            }
        },
        // {
        //     path: '/setPassword',
        //     name: 'SetPassword',
        //     component: () => import('@/views/personal/SetPassword.vue'),
        //     meta: {
        //         title: '重置密码',
        //         keepAlive: false,
        //         isAuth: false
        //     }
        // },
        {
            path: '/admin',
            name: 'AdminIndex',
            component: () => import('@/views/personal/Personal.vue'),
            children: [
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
                    path: 'article/type',
                    name: 'articleType',
                    component: () => import('@/views/admin/article/type/AdminArticleType.vue'),
                    meta: {
                        title: ' 文章分类',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'article/label',
                    name: 'articleLabel',
                    component: () => import('@/views/admin/article/label/AdminArticleLabel.vue'),
                    meta: {
                        title: ' 文章标签',
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
                        title: '文档管理',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'doc/editor',
                    name: 'DocEditor',
                    component: () => import('@/views/admin/doc/DocEditor.vue'),
                    meta: {
                        title: '文章编辑',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'file',
                    name: 'File',
                    component: () => import('@/views/admin/file/UserFile.vue'),
                    meta: {
                        title: '个人云盘',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'setting/web',
                    name: 'WebSetting',
                    component: () => import('@/views/admin/setting/WebSetting.vue'),
                    meta: {
                        title: '网站设置',
                        keepAlive: false,
                        isAuth: true,
                    },
                },
                {
                    path: 'setting/user',
                    name: 'UserSetting',
                    component: () => import('@/views/admin/setting/UserSetting.vue'),
                    meta: {
                        title: '个人设置',
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
    // if (to.path) {
    //     if (window._hmt) {
    //         window._hmt.push()
    //     }
    // }
    next()
})

router.afterEach(() => {
    Nprogress.done()
})

export default router;