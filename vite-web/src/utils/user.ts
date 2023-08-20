// 是否登录判断

import { computed, onMounted, ref, onActivated, watch } from "vue";
import { systemStore } from "@/store/system"
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter } from "vue-router";
import socketUser from "@/utils/socketUser";

function user() {
	let { openSocketUser, closeWebSocketUser } = socketUser()
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
				userId.value = store.userLocal.access_token.split(":")[0]
				userToken.value = store.userLocal.access_token
				isLogin.value = true
			}
		} else {
			if (JSON.stringify(store.userSession) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				userId.value = store.userSession.access_token.split(":")[0]
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
				isLogin.value = true
			}
		}
	})
	onMounted(() => {
		// 获取用户基本信息
		if (store.keepLogin === true) {
			if (JSON.stringify(store.userLocal) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				userId.value = store.userLocal.access_token.split(":")[0]
				userToken.value = store.userLocal.access_token
				userName.value = store.userLocal.user_id
				isLogin.value = true
			}
		} else {
			if (JSON.stringify(store.userSession) === '{"user_id":"","access_token":""}') {
				isLogin.value = false
			} else {
				userId.value = store.userSession.access_token.split(":")[0]
				userToken.value = store.userSession.access_token
				userName.value = store.userSession.user_id
				isLogin.value = true
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
				closeWebSocketUser();
				localStorage.clear()
				sessionStorage.clear()
				store.userSession = { user_id: "", access_token: "" }
				store.userLocal = { user_id: "", access_token: "" }
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
