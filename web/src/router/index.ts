import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";

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
                path: "/timeline",
                component: () => import("/src/components/home/timeline/TimeLine.vue")
            },
            {
                path: "/about",
                component: () => import("/src/pages/home/About.vue"),
            },
        ]
    },
    {
        path:"/adminPage",
        redirect: "/admin",
        component: () => import("/src/pages/admin/AdminPage.vue"),
        children: [
            {
                path: "/admin",
                component: () => import("/src/components/admin/index/AdminIndex.vue")
            }
        ]
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

const router = createRouter( {
    history: createWebHashHistory(),
    routes
})

export default router
