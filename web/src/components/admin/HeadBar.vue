<template>
    <div class="navi-bar">
        <div class="right-side">
            <div class="version-info">
                <span class="version-item" title="程序版本"> 1.0.0 </span>
            </div>

            <div class="person-info" @click="visible = !visible">
                <span class="person-name">test</span>

                <div v-show="visible" class="contextmenu">
                    <div @click="$router.push('')">
                        
                        个人信息
                    </div>
                    <div @click="showForget = true">
                        
                        修改密码
                    </div>
                    <div @click="logout">
                        
                        退出登录
                    </div>
                </div>
            </div>
        </div>
        <!--忘记密码的dialog-->
        <el-dialog title="修改密码" :visible.sync="showForget" width="350px">
            <!-- 这里必须要写model,后面那个rule里面的prop必须和model对象一致，不然会无法使用-->
            <el-form ref="pass" label-width="80px" :rules="rules" :model="pass">
                <el-form-item prop="oldPass" label="原密码">
                    <el-input v-model="pass.oldPass" type="password" />
                </el-form-item>
                <el-form-item prop="newPass" label="新密码">
                    <el-input v-model="pass.newPass" type="password" />
                </el-form-item>
                <el-form-item prop="repeatPass" label="重复密码">
                    <el-input v-model="pass.repeatPass" type="password" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showForget = false">取 消</el-button>
                <el-button type="primary" @click="">修改密码</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>


export default {
    name: 'HeadBar',
    data() {
        return {
            visible: false, // 显示个人信息设置
            showForget: false, // 显示修改密码弹框
            pass: {
                oldPass: '', // 旧密码
                repeatPass: '', // 重复密码
                newPass: '' // 新密码
            },
            full: false, // 当前是否是全屏状态
            rules: { // 修改密码规则
                oldPass: [{
                    required: true,
                    message: '请填写密码'
                }],
                repeatPass: [{
                    required: true,
                    validator: (rule, value, callback) => {
                        value !== this.pass.newPass ? callback(new Error('两次输入的密码不一致')) : callback()
                    },
                    trigger: 'blur'
                }],
                newPass: [{
                    required: true,
                    message: '请输入新密码'
                }]
            },
        }
    },
    //   const user = {
    //     username: "asd"
    //   }

    mounted() {

    },
    methods: {
        // 侧边栏收缩
        handleClick() {
            // this.$store.commit('tab/changeSideStatue')
        },
        logout() {
            // this.$store.dispatch('vuexLogin/logout').then(resp => {
            //     if (resp.code === 200) {
            //         this.$store.commit('vuexLogin/logout')
            //         this.$router.replace('/index')
            //     }
            // })
        },
    }
}
</script>

<style scoped>
/*版本信息*/
.version-info {
    display: flex;
    flex-wrap: wrap;
    color: #666;
    font-size: 15px;
    margin: 5px;
    align-items: center;
}

.version-info .version-item {
    justify-content: center;
    flex: 1;
}

/*个人头像点击*/
.contextmenu {
    position: fixed;
    top: 48px;
    right: 3px;
    z-index: 3000;
    border: 1px solid #d4d4d5;
    line-height: 1.4285em;
    max-width: 160px;
    background: #fff;
    font-weight: 400;
    font-style: normal;
    color: rgba(0, 0, 0, 0.87);
    border-radius: 3px;
}

.contextmenu div {
    position: relative;
    vertical-align: middle;
    line-height: 1;
    -webkit-tap-highlight-color: transparent;
    padding: 10px 15px;
    color: rgba(0, 0, 0, .87);
    font-size: 14px;
    cursor: pointer;
}

.contextmenu div :hover {
    background: #eee;
}

.navi-bar {
    
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #ffc;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    display: flex;
    flex-wrap: wrap;
    align-items: center;
}

.open-icon {
    width: 20px;
    line-height: 50px;
    padding: 0 15px;
    margin: 0 10px 0 0;
    cursor: pointer;
}

.open-icon:hover {
    background: rgba(0, 0, 0, 0.025);
}

.right-side {
    flex: 1;
    display: flex;
    justify-content: flex-end;
}

.person-avatar {
    width: 40px;
    border-radius: 50%;
}

.person-info {
    cursor: pointer;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    padding: 0 10px 0 0;
}

.person-name {
    font-size: 12px;
    margin: 0 5px;
}
</style>
