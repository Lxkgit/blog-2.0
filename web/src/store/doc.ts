import { defineStore } from "pinia";


export const docStore = defineStore({
    id: "doc",
    state: () => ({
        docId: 0,
        doc: {}
    }),
    getters: {
        getDocId(): any {
            return this.docId
        },
        getDoc(): any {
            return this.doc;
        }
    },
    actions: {
        setDocId(id: any) {
            this.docId = id
        },

        setDoc(doc: any) {
            this.doc = doc
        }
    },
    // persist: {
    //     enabled: true,
    //     strategies: [
    //         {
    //             key: 'doc',
    //             storage: localStorage,
    //             paths: ['doc']
    //         }
    //     ]
    // }
})