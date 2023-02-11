<template>
    <div class="nav">
        <div class="login-block">
            <div class="login">
                <el-form :model="loginForm" :rules="rules" class="login-container" label-position="left"
                    label-width="0px" v-loading="loginForm.loading">
                    <div style="text-align: center;margin-bottom: 5px;">登录</div>
                    <el-form-item prop="username">
                        <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号">
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码">
                        </el-input>
                    </el-form-item>
                    <el-checkbox class="login_remember" v-model="loginForm.checked" label-position="left"><span style="color: #505458;">记住密码</span></el-checkbox>
                    <el-form-item style="width: 100%; display: flex; width: 100%; justify-content: space-around;">
                       <div style="width: 40%; display: flex; justify-content: center;" >
                            <el-button type="primary" v-on:click="login">登录</el-button>
                        </div>
                        <div style="width: 20%; display: flex; justify-content: center;">
                            <router-link to="/register" style="text-decoration: none;">
                                <el-button type="primary">
                                    注册
                                </el-button>
                            </router-link>
                        </div> 
                    </el-form-item>
                </el-form>
                <el-button> 
                    <a href="https://gitee.com/oauth/authorize?client_id=b56892ea476c32e017d279841a6422bd1dce3c5d87985588c206075f8e55e0a4&redirect_uri=https://127.0.0.1:9527/auth/gitee/callback&response_type=code&state=1">Gitee登录</a>
                </el-button>
                <!-- <el-button> 
                    <a href="https://gitee.com/oauth/authorize?client_id=b56892ea476c32e017d279841a6422bd1dce3c5d87985588c206075f8e55e0a4&redirect_uri=https://127.0.0.1:9527/auth/gitee/callback&response_type=code&state=1">Gitee登录</a>
                </el-button> -->
            </div>
        </div>
        <!-- lxkwxkfpt@* -->
    </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import type { FormRules } from 'element-plus'
import { useRoute, useRouter } from 'vue-router';
import { userLoginApi, selectUserById } from '../api/login';
import { userLoginStore } from '../store/login'

const route = useRoute();
const router = useRouter();
const store = userLoginStore();


const loginForm = reactive({
    username: '',
    password: '',
    checked: true,
    loading: false
})

const rules = reactive<FormRules>({
    username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
    password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
})

const login = () => {
    userLoginApi(loginForm.username, loginForm.password).then((res: any) => {
        if (res.code === 200) {
            store.setToken(res.result)
            router.push("/admin")
        }
    });
}
</script>

<style scoped>
.nav {
    /* background: url("../../assets/loginbackground.jpg") no-repeat right bottom; */
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
    z-index: -1;
}

.logo {
    /* background: url("../../assets/logo.png"); */
    width: 80px;
    height: 80px;
    background-size: cover;
    text-align: center;
    /*justify-content: space-between;*/
    /*position: relative;*/
    /*top: 95px;*/
    /*right: -118px;*/
    /*border: rgba(255,255,255,.4) 4px solid;*/
}

.login-container {
    border-radius: 15px;
    margin-top: 200px;
    margin-left: auto;
    margin-right: auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.login_title {
    margin: 0 auto 10px auto;
    text-align: center;
    color: #505458;
}

.login_remember {
    margin-top: 20px;
    margin-bottom: 25px;
    margin-left: 30px;
    display: flex;
    justify-content: left;
}
</style>