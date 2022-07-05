import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        component: () => import("/src/pages/home/HomePage.vue"),
    },
    {
        path: "/about",
        component: () => import("/src/pages/home/About.vue"),
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
