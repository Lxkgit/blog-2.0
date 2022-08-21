import { defineStore } from "pinia";


export const articleStore = defineStore('tag', {
    
    state: () => ({
        article: {
            id: 0,
            userId:0,
            title:"",
            contentMd:"-",
            contentHtml:"-",
            articleType:"",
            articleLabel:"",
            articleStatus:1,
            browseCount:0,
            likeCount:0,
            createTime:"",
            updateTime:""
        }
    }),
    getters: {},
    actions: {
       setArticle(value: string|string[]) {
            article = JSON.parse(value)
            console.log(" ++ " + this.article)
       }
    }
})