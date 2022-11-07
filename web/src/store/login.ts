import { defineStore } from "pinia";


export const userLoginStore = defineStore({
    id: 'login',
    state:() => {
        return{
            user: null,
            token: {
                access_token: "",
                expires_in: 0,
                jti: "",
                refresh_token: "",
                scope: "",
                token_type: "",
                user_id: 0
            }
        }
    },
    getters: {
        // getUser() {
        //     this.user = JSON.stringify(localStorage.getItem("user"));
        //     return localStorage.getItem("user") === null ? '' : JSON.parse(localStorage.getItem("user"));
        // }
        getToken() {
            return this.token;
        }
        
    },
    actions: {
        setToken(token) {
            this.token = token
        }
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: 'blog',
                storage: localStorage,
                paths: ['token']
            }
        ]
    }
})