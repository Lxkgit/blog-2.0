<template>
	<div class="menu">
		<div class="menu-nav">
			<div><a>首页</a></div>
			<div><a>文档</a></div>
			<div><a>导航</a></div>
		</div>
		<div class="menu-search">
			<input type="submit" value="搜索">
		</div>
		<div class="menu-login">
			<div><a>登录</a></div>
		</div>
	</div>
</template>

<style scoped>
	.menu {
		background-color: bisque;
		height: 3rem;
		text-align: center;
	}
	.menu div {
		display: inline;
	}
	.menu-nav {
		
	}
	.menu-nav div {
		display: inline;
		margin: 1rem;
	}
	.menu-search input{
		display: inline;
	}
	.menu-login div{
		display: inline;
	}


</style>

<script setup lang="ts">
import { ref } from 'vue';
const loginFlag = ref<boolean>(false)

</script>