import { defineStore } from "pinia";

export const systemStore = defineStore('system', {
  state: () => ({
    // markdown目录(sessionStorage)
    outline: {},
    // markdown目录是否显示
    outlineShow: true,
    // 登录后跳转页
    nextPath: '/personal/myIndex',
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
    
    
    sideBar: false,
  }),
  getters: {

  },
  actions: {
    // 设置markdown目录内容
    setOutline(value) {
      this.outline = value
    },
    // 设置markdown是否显示
    setOutlineShow() {
      this.outlineShow = !this.outlineShow
    },
    // 设置登录后跳转的地址
    setNextPath(path) {
      this.nextPath = path
    },
    // 是否保持登录
    setKeepLogin(value) {
      this.keepLogin = value
    },
    // 用户信息（保持登录）
    setUserLocal(value) {
      this.userLocal = value
    },
    // 用户信息（临时存储）
    setUserSession(value) {
      this.userSession = value
    },
    // 个人中心导航栏是否折叠
    setAsideMenuFold(value) {
      this.asideMenuFold = value
    },
    // 设置主题色
    setTheme(value) {
      this.theme = value
    },
    // 设置导航栏模式
    setNavigation(value) {
      this.navigation = value
    },
    // 设置导航栏当前激活的菜单id
    setMenuIndex(value) {
      this.menuIndex = value
    },
    // 设置深色模式
    setDark(value) {
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
      
    ]
  }
})