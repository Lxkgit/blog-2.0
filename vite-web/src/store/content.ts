import { defineStore } from "pinia";


export const contentStore = defineStore({
    id: "content",
    state: () => ({
        article: {},
        docContent: {}
    }),
    getters: {
        getArticle(): any {
            return this.article;
        },
        getDocContent() :any {
            return this.docContent;
        }
    },
    actions: {
       setArticle(article: any) {
            this.article = article
       },
       setDocContent(docContent: any) {
            this.docContent = docContent
       }
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: 'article',
                storage: localStorage,
                paths: ['article']
            },
            {
                key: 'docContent',
                storage: localStorage,
                paths: ['docContent']
            }
        ]
    }
})