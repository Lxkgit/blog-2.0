<template>
    <div class="navi-bar">
        <div class="right-side">
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
        <el-dialog title="修改密码" v-model="showForget" width="350px">
            <el-form ref="ruleFormRef" :model="ruleForm" label-width="80px" :rules="rules">
                <el-form-item label="原密码" prop="oldPass">
                    <el-input v-model="ruleForm.oldPass" type="password" />
                </el-form-item>
                <el-form-item label="新密码" prop="newPass">
                    <el-input v-model="ruleForm.newPass" type="password" />
                </el-form-item>
                <el-form-item label="重复密码" prop="repeatPass">
                    <el-input v-model="ruleForm.repeatPass" type="password" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="showForget = false">取 消</el-button>
                    <el-button type="primary" @click="submitForm(ruleFormRef)">修改密码</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance } from 'element-plus'

let visible = ref(false); // 显示个人信息设置
let showForget = ref(false); // 显示修改密码弹框

const ruleFormRef = ref<FormInstance>()

const oldPass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入原密码'))
    } else if (value.length < 8) {
        callback(new Error('密码长度最少为8位'))
    } else {
        callback()
    }
}

const newPass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入新密码'))
    } else if (value.length < 8) {
        callback(new Error('密码长度最少为8位'))
    } else {
        callback()
    }
}

const repeatPass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value.length < 8) {
        callback(new Error('密码长度最少为8位'))
    } else if (value !== ruleForm.newPass) {
        callback(new Error("俩次输入密码不匹配"))
    } else {
        callback()
    }
}

const rules = reactive({
    oldPass: [{ validator: oldPass, trigger: 'blur' }],
    newPass: [{ validator: newPass, trigger: 'blur' }],
    repeatPass: [{ validator: repeatPass, trigger: 'blur' }],
})

const ruleForm = reactive({
    oldPass: '',
    newPass: '',
    repeatPass: '',
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            console.log(ruleForm)
            console.log('submit!')
        } else {
            console.log('error submit!')
            return false
        }
    })
}

const logout = () => {

}
</script>

<style scoped>
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

.dialog-footer {
    /* float: right; */
}
</style>
