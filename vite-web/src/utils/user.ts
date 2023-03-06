// 是否登录判断

import { computed, onMounted, ref, onActivated } from "vue";
import { systemStore } from "@/store/system"


function user() {
	const store = systemStore()
	const isLogin = ref(false)
	const userId = ref()
	const userToken = ref()
	const userName = ref()
	onActivated(() => {
		// 获取用户基本信息
		console.log("user.ts onActivated")
		if (store.keepLogin === true) {
			if (JSON.stringify(store.userLocal) === '{}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userLocal.user_id
				userToken.value = store.userLocal.access_token
				userName.value = store.userLocal.user_id
			}
		} else {
			if (JSON.stringify(store.userSession) === '{}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userSession.user_id
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
			}
		}
	})
	onMounted(() => {
		// 获取用户基本信息
		console.log("user.ts onMounted")
		if (store.keepLogin === true) {
			if (JSON.stringify(store.userLocal) === '{}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userLocal.user_id
				userToken.value = store.userLocal.access_token
				userName.value = store.userLocal.user_id
			}
		} else {
			if (JSON.stringify(store.userSession) === '{}') {
				isLogin.value = false
			} else {
				isLogin.value = true
				userId.value = store.userSession.user_id
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
			}
		}
	})
	return {
		isLogin, userId, userToken, userName
	}
}

export default user
