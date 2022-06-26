import { createRouter, createWebHistory} from 'vue-router';
import routes from './router.config'

export const router = createRouter({
    history: createWebHistory(), // createWebHistory => 不带#号,需后端支持  createWebHashHistory带#号
    routes
});
