import { defineStore } from "pinia";

export const blogList = defineStore('blog', {
    state:() => {
        return{
            name: 'asd',
            title: 'asdasd'
        }
    },
    getters: {},
    actions: {}
})