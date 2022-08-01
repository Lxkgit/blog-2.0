import { defineStore } from "pinia";

export const tagStore = defineStore('tag', {
    state: () => ({
        tags: [
            {
                active: "false",
                path: "/admin",
                name: "首页"
            }
        ]
    }),
    getters: {},
    actions: {}
})