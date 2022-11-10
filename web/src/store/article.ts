import { defineStore } from "pinia";


export const articleStore = defineStore({
    id: "article",
    state: () => ({
        article: {}
    }),
    getters: {
        getArticle() {
            return this.article;
        }
    },
    actions: {
       setArticle(article) {
            this.article = article
       }
    }
})