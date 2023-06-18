// 是否登录判断

import { computed, onMounted, ref, onActivated } from "vue";
import { systemStore } from "@/store/system"
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter } from "vue-router";

function user() {
	const store = systemStore()
	const isLogin = ref(false)
	const userId = ref()
	const userToken = ref()
	const userName = ref()
	const router = useRouter()
	onActivated(() => {
		// 获取用户基本信息
		if (store.keepLogin === true) {
			if (JSON.stringify(store.userLocal) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userLocal.access_token.split(":")[0]
				userToken.value = store.userLocal.access_token
				userName.value = store.userLocal.user_id
			}
		} else {
			if (JSON.stringify(store.userSession) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userSession.access_token.split(":")[0]
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
			}
		}
	})
	onMounted(() => {
		console.log(JSON.stringify(router.currentRoute.value.path) + "--")
		// 获取用户基本信息
		if (store.keepLogin === true) {
			if (JSON.stringify(store.userLocal) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userLocal.access_token.split(":")[0]
				userToken.value = store.userLocal.access_token
				userName.value = store.userLocal.user_id
			}
		} else {
			if (JSON.stringify(store.userSession) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userSession.access_token.split(":")[0]
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
			}
		}
	})
	// 个人中心-退出登录
const logout = () => {
  ElMessageBox.confirm(
    '真的要退出登录吗?',
    '退出登录',
    {
      confirmButtonText: '确认',
      cancelButtonText: '再想想',
      type: 'warning',
    }
  )
    .then(() => {
      ElMessage({
        type: 'success',
        message: '账号已成功退出',
      })
      localStorage.clear()
      sessionStorage.clear()
			store.userSession = {user_id:"",access_token:""}
			store.userLocal = {user_id:"",access_token:""}
			isLogin.value = false
			router.replace('/')
    })
    .catch(() => {
      console.log("算了，没退出")
    })
}
	return {
		isLogin, userId, userToken, userName, logout
	}
}

export default user
