import { defineStore } from "pinia";


export const adminStore = defineStore('tag', {
    
    state: () => ({
        tags: [
            {
                active: true,  // 是否激活
                title: '主页', // 任务栏标题
                close: false,  // 是否可以关闭
                path: '/admin' // 当前标签的路径
            }
        ],
        sideBar: false,
        selectedTag: 0,
    }),
    getters: {},
    actions: {
        addTag(name: any,path: any) {
            console.log("addTag name{}, path{}", name, path)
            this.tags.map(item => (item.active = false))
            let flag = false
            for(let i=0; i<this.tags.length; i++) {
                if(this.tags[i].path === path) {
                    flag = true;
                    this.tags[i].active = true;
                }
            }
            if(!flag){
                this.selectedTag = this.tags.length
                this.tags.push({active: true, title: name, close: true, path: path})
            }
            
        },
        delTag(index: any) {
            if(this.tags[index].close){
                if(this.tags[index].active) {
                    this.tags.splice(index, 1)
                    this.tags[0].active = true
                    this.selectedTag = 0
                } else {
                    this.tags.splice(index, 1)
                }
            }
        },
        activeTag(path:any) {
            console.log("path:  ", path)
            this.tags.map(item => (item.active = false));
            for(let i=0; i<this.tags.length; i++) {
                if(this.tags[i].path === path) {
                    this.tags[i].active = true;
                    this.selectedTag = i;
                }
            }
        },
        delAllTags() {
            this.tags.splice(1, this.tags.length-1)
        },
        delOtherTags() {        
            for(let i=0; i<this.tags.length; i++) {
                console.log(JSON.stringify(this.tags[i]))
                if(!this.tags[i].active && this.tags[i].close) {
                    this.tags.splice(i, 1)
                    i--
                }
            }
        },
        saveTags() {
            console.log("save tag")
            sessionStorage.setItem('tags', JSON.stringify(this.tags))
        },
        
        restoreTags() {
            console.log("load tag")
            const tagsJSON: any = sessionStorage.getItem('tags')
            if (tagsJSON != null) {
                this.tags = tagsJSON
            }
            
        }
    }
})