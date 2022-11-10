import console from "console";
import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import { userLoginStore } from "../store/login";
const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        redirect: "/home",
    },
    {
        path: "/home",
        redirect: "/index",
        component: () => import("/src/pages/home/HomePage.vue"),
        children: [
            {
                path: "/index",
                component: () => import("/src/components/home/index/HomeIndex.vue")
            },
            {
                path: "/doc",
                component: () => import("/src/pages/home/DocPage.vue"),
            },
            {
                path: "/nav",
                component: () => import("/src/pages/home/NavPage.vue"),
            },
            {
                path: "/article/articleDetail",
                component: () => import("/src/components/home/index/article/ArticleDetail.vue"),
            }
        ]
    },
    {
        path: "/adminPage",
        redirect: "/admin",
        component: () => import("/src/pages/admin/AdminPage.vue"),
        meta: {
            requireAuth: true
        },
        children: [
            {
                path: "/admin",
                component: () => import("/src/components/admin/index/AdminIndex.vue"),

            },
            {
                path: "/admin/user",
                component: () => import("/src/components/admin/user/AdminUser.vue"),

            },
            {
                path: "/admin/role",
                component: () => import("/src/components/admin/role/AdminRole.vue"),

            },
            {
                path: "/admin/article",
                component: () => import("/src/components/admin/article/AdminArticle.vue"),

            },

            {
                path: "/admin/diary",
                component: () => import("/src/components/admin/diary/AdminDiary.vue"),

            }
        ]
    },
    {
        name: "editor",
        path: "/admin/article/editor",
        component: () => import("/src/components/admin/article/Editor.vue"),
        requireAuth: true,
    },
    {
        path: "/doc",
        component: () => import("/src/components/home/doc/Doc.vue")
    },
    {
        path: "/nav",
        component: () => import("/src/components/home/nav/NavIndex.vue")
    },
    {
        path: "/login",
        component: () => import("/src/pages/LoginPage.vue"),
    },
    {
        path: "/:pathMatch(.*)",
        component: () => import("/src/pages/ErrorPage.vue"),
    },

]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const store = userLoginStore();
    let token = store.getToken;
    if (token.user_id !== 0 && to.path.startsWith("/admin")) {
        // console.log("加载菜单中 ... ")
    }
    if (token.user_id !== 0 && to.path.startsWith("/login")) {
        console.log("login - > admin")
        next({
            path: "/admin"
        })
    }
    if (to.matched.some(r => r.meta.requireAuth)) { // 判断该路由是否需要登录权限
        if (token.user_id !== 0) {
            next()
        } else {
            next({
                path: '/login',
                query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
        }
    } else {
        next()
    }
 
})

// const initAdminMenu = (router, store) => {

// }

// const formatRoutes = (routes) => {

// }



export default router
