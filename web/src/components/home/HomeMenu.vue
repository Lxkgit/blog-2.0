<template>
	<header>
		<div class='header_main'>
			<div class='header_l'>
				<h1 class='logo'>
					<img src="../../assets/logo.png" />
				</h1>
				<ul>
					<li :class="{'active': activePath==='index'}">
						<router-link to="/index" @click="addClass('index')">首 页</router-link>
					</li>
					<li :class="{'active': activePath==='doc'}">
						<router-link to="/doc" @click="addClass('doc')">文 档</router-link>
					</li>
					<li :class="{'active': activePath==='nav'}">
						<router-link to="/nav" @click="addClass('nav')">导 航</router-link>
					</li>
				</ul>
			</div>
			<div class='header_r'>
				<div class='search'>
					<input type="text" placeholder="搜索文章类型" />
					<i class="fa fa-search" aria-hidden="true"></i>
				</div>
				<div class='login'>
					<router-link to="/login" v-if="!isLogin">
						登录
					</router-link>
					<router-link to="/admin" @click="tags.activeTag('/admin')" v-else>
						进入后台
					</router-link>
				</div>
			</div>
		</div>
	</header>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { selectUserById } from '../../api/login';
import { userLoginStore } from '../../store/login';
import { tagsStore } from "../../store/tag"

const store = userLoginStore()
const tags = tagsStore();
let isLogin = ref(false);
let activePath = ref("index")

onMounted(() => {
	let userId = store.token.user_id
	if(userId !== 0 && userId !== undefined) {
		selectUserById(userId).then((res: any) => {
			if(res.code === 200) {
				isLogin.value = true;
			}
    	});
	} else {
		isLogin.value = false;
	}
});

const addClass = (path: string) => {
    activePath.value = path
}


</script>

<style scoped>
header {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 50px;
	background-color: #FFFFFF;
	box-shadow: 0 5px 6px rgba(0, 0, 0, 0.16);
}

.header_main {
	display: flex;
	justify-content: space-between;
	align-items: center;
	width: 1400px;
	min-width: 900px;
	height: 100%;
	margin: 0 auto;
}

.header_l {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.logo {
	width: 137px;
	height: 40px;
	margin-right: 51px;
}

.logo img {
	width: 100%;
	height: 100%;
}

.header_l ul {
	display: flex;
	height: 50px;
}

.header_l ul li {
	list-style-type: none;
	line-height: 50px;
	font-weight: 500;
	cursor: pointer;
}

.header_l ul li a{
	text-decoration: none;
}

.header_l ul li+li {
	margin-left: 19px;
	font-size: 16px;
}

.active {
	position: relative;
	color: #3585FF!important;
}

.active:after {
	content: '';
	position: absolute;
	bottom: 0;
	left: 0;
	width: 140%;
	margin-left: -20%;
	height: 3px;
	background-color: #3585FF!important;
}

.header_r {
	display: flex;
	align-items: center;
}

.header_r .search {
	display: flex;
	align-items: center;
	padding: 0 10px;
	width: 300px;
	height: 33px;
	background-color: #F0F2F4;
	border-radius: 5px;
}

.header_r .search input {
	width: 280px;
	height: 43px;
	border: none;
	background: transparent;
	outline: none;
}

.header_r .search i {
	cursor: pointer;
}



.header_r .shop {
	display: flex;
	align-items: center;
	margin-left: 42px;
}

.header_r .login {
	margin-left: 79px;
	font-size: 16px;
	cursor: pointer;
}

.header_r .login a{
	text-decoration: none;
}
</style>

