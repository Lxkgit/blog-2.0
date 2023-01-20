<template>
	<footer>
		<div class="foot_main">
			
			<div class="foot_c">
				<ul>
					<li>关于我</li>
					<li>|</li>
					<li>意见反馈</li>
				</ul>
				<p>
				博客已稳定运行<span v-html='runTimeString'></span><span class="timeJump">(●'◡'●)ﾉ♥</span>
			</p>
			</div>
			
		</div>
	</footer>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';

let runTimeString = ref();

onMounted(() => {
	runTime()
})

const runTime = () => {
	let oldTime = new Date('2021/07/15 00:00:00')
	setInterval(function () {
		let nowTime = new Date()
		let longTime = nowTime.getTime() - oldTime.getTime()
		let days = parseInt(String(longTime / 1000 / 60 / 60 / 24), 10) // 计算剩余的天数
		let hours = parseInt(String(longTime / 1000 / 60 / 60 % 24), 10) // 计算剩余的小时
		let minutes = parseInt(String(longTime / 1000 / 60 % 60), 10) // 计算剩余的分钟
		let seconds = parseInt(String(longTime / 1000 % 60), 10) // 计算剩余的秒数
		runTimeString.value = days + '天' + hours + '小时' + minutes + '分' + seconds + '秒'
	}, 1000)
}
</script>

<style scoped>
footer {
	width: 100%;
	height: 87px;
	background: #F0f0f0;
}

.foot_main {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 100%;
	margin: 10px 0 0;
}

.foot_c {
	display: flex;
	flex-direction: column;
	align-items: center;
	color: black;
	font-size: 14px;
}

.foot_c ul {
	display: flex;
}

.foot_c ul li {
	list-style-type: none;
}

.foot_c ul li:hover {
	color: aqua;
}

.foot_c ul li + li {
	margin-left: 10px;
}
</style>