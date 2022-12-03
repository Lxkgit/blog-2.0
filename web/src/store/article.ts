import { defineStore } from "pinia";


export const articleStore = defineStore({
    id: "article",
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
    }
})