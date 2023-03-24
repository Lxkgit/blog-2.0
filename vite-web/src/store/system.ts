import { defineStore } from "pinia";

export const systemStore = defineStore('system', {
  state: () => ({
    // markdown目录(sessionStorage)
    outline: {},
    // markdown目录是否显示
    outlineShow: true,
    // 登录后跳转页
    nextPath: '/admin',
    // 是否保持登录
    keepLogin: false,
    // 保持登录用户信息(localStorage)
    userLocal: {},
    // 临时登录用户信息(sessionStorage)
    userSession: {},
    // 个人中心导航栏是否折叠
    asideMenuFold: false,
    // 默认主题色
    theme: '#409eff',
    // 导航栏样式
    navigation: 'auto',
    // 当前激活的导航栏菜单id
    menuIndex: "1",
    // 是否开启深色模式
    isDark: false,
    // 用户是否登录
    isLogin: false,
    
    sideBar: false,
  }),
  getters: {

  },
  actions: {
    // 设置markdown目录内容
    setOutline(value: any) {
      this.outline = value
    },
    // 设置markdown是否显示
    setOutlineShow() {
      this.outlineShow = !this.outlineShow
    },
    // 设置登录后跳转的地址
    setNextPath(path: any) {
      this.nextPath = path
    },
    // 是否保持登录
    setKeepLogin(value: any) {
      this.keepLogin = value
    },
    // 用户信息（保持登录）
    setUserLocal(value: any) {
      this.userLocal = value
    },
    // 用户信息（临时存储）
    setUserSession(value: any) {
      this.userSession = value
    },
    // 个人中心导航栏是否折叠
    setAsideMenuFold(value: any) {
      this.asideMenuFold = value
    },
    // 设置主题色
    setTheme(value: any) {
      this.theme = value
    },
    // 设置导航栏模式
    setNavigation(value: any) {
      this.navigation = value
    },
    // 设置导航栏当前激活的菜单id
    setMenuIndex(value: any) {
      this.menuIndex = value
    },
    // 设置深色模式
    setDark(value: any) {
      this.isDark = value
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'theme',
        storage: localStorage,
        paths: ['theme']
      },
      {
        key: 'isDark',
        storage: localStorage,
        paths: ['isDark']
      },
      {
        key: 'navigation',
        storage: localStorage,
        paths: ['navigation']
      },
      {
        key: 'keepLogin',
        storage: localStorage,
        paths: ['keepLogin']
      },
      {
        key: 'userLocal',
        storage: localStorage,
        paths: ['userLocal']
      },
      {
        key: 'userSession',
        storage: sessionStorage,
        paths: ['userSession']
      },


      
    ]
  }
})