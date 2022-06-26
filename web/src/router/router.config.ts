export default [
    {
        path: '/',
        name: 'root',
        redirect: '/root',
        component: () => import('../App.vue'),
        children:[]
    },
    // {
    //     path: '/btn',
    //     name: 'btn',
    //     component: () => import('../views/btn.vue'),
    // }
]
