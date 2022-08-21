import { defineStore } from "pinia";

export const userLoginStore = defineStore('login', {
    state:() => {
        return{
            user: null,
        }
    },
    getters: {
        getUser() {
            this.user = JSON.stringify(localStorage.getItem("user"));
            return localStorage.getItem("user") === null ? '' : JSON.parse(localStorage.getItem("user"));
        }
    },
    actions: {}
})