<template>
	<a-menu v-model:selectedKeys="current" mode="horizontal" style="display: inline-block; width: 100%;">
		<div style="display: inline-block; margin-left: 5%;">
			<a-menu-item key="home" style="display: inline-block;">
				<home-outlined />
				<a href="#/" rel="noopener noreferrer">
					首页
				</a>
			</a-menu-item>
			<a-menu-item key="history" style="display: inline-block;">
				<calendar-outlined />
				<a href="#/timeline" rel="noopener noreferrer">
					归档
				</a>
			</a-menu-item>
			<a-menu-item key="about" style="display: inline-block;">
				<contacts-outlined />
				<a href="#/about" rel="noopener noreferrer">
					关于我
				</a>
			</a-menu-item>
			<a-menu-item key="doc" style="display: inline-block;">
				<folder-outlined />
				<a href="#/doc" rel="noopener noreferrer">
					文档
				</a>
			</a-menu-item>
			<a-menu-item key="nav" style="display: inline-block;">
				<ie-outlined />
				<a href="#/nav" rel="noopener noreferrer">
					导航
				</a>
			</a-menu-item>
		</div>
		<div style="display: inline-block; float: right; margin-right: 8%;">
			<a-menu-item key="login" style="display: inline-block;">
				<a href="#/login" rel="noopener noreferrer">
					登录
				</a>
			</a-menu-item>
			<a-menu-item key="register" style="display: inline-block;">
				<a href="#/" rel="noopener noreferrer">
					注册
				</a>
			</a-menu-item>
		</div>
	</a-menu>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import { HomeOutlined, CalendarOutlined, ContactsOutlined, FolderOutlined, IeOutlined } from '@ant-design/icons-vue';

const current = ref<string[]>(['home']);


</script>