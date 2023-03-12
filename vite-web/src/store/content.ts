import { defineStore } from "pinia";


export const contentStore = defineStore({
    id: "content",
    state: () => ({
        article: {}
    }),
    getters: {
        getArticle(): any {
            return this.article;
        }
    },
    actions: {
       setArticle(article: any) {
            this.article = article
       }
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: 'article',
                storage: localStorage,
                paths: ['article']
            }
        ]
    }
})