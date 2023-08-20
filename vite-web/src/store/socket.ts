import { defineStore } from "pinia";


export const socketStore = defineStore({
  id: "socketStore",
  state: () => ({
    userId: null
  }),
  getters: {
    getUserId(): any {
      return this.userId
    }
  },
  actions: {
    setUserId(userId: any) {
      this.userId = userId
    },

  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'userId',
        storage: sessionStorage,
        paths: ['userId']
      }
    ]
  }
})