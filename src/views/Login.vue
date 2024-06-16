<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref ,watch} from 'vue'
import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const reisteacher = ref(false)
//定义数据模型
const registerData = ref({
    email: '',
    password: '',
    isTeacher : false,
    rePassword: '',
    department: '',
    researchArea: '',
    title: '',
    officeAddress: '',
    name: '',
    grade: '',
    major: '',
    studentId: ''
})

const radioGroupChange = (val) => {
  registerData.isteacher = val;
  console.log(registerData.isteacher)

}
const adminlogin = ()=>{
    router.push('/adminlogin')
}
//校验密码的函数

const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

//定义表单校验规则
/*
const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]
}
*/

//调用后台接口,完成注册

import { userRegisterService, userLoginService} from '@/api/user.js'
import { teacherRegisterService} from '@/api/teacher.js'
import { studentRegisterService} from '@/api/student.js'
const register = async () => {
    //registerData是一个响应式对象,如果要获取值,需要.value
    let result = await userRegisterService(registerData);
    /* if (result.code === 0) {
        //成功了
        alert(result.msg ? result.msg : '注册成功');
    }else{
        //失败了
        alert('注册失败')
    } */
    //alert(result.msg ? result.msg : '注册成功');
    if(result.msg == "success"){
        console.log(registerData.value.isTeacher)
        if(registerData.value.isTeacher===true){
        let result1 = await teacherRegisterService(registerData);
    }else{
        let result1 = await studentRegisterService(registerData);
    }
        ElMessage.success("注册成功")
    }
    else{
        ElMessage.success("注册失败，邮箱已被注册！")
    }
}


//绑定数据,复用注册表单的数据模型
//表单数据校验
//登录函数

import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const login =async ()=>{
    //调用接口,完成登录
   let result =  await userLoginService(registerData);
   console.log(result);
    /*if(result.data.success==true){
    alert(result.msg? result.msg : '登录成功')
   }else{
    alert('登录失败')
   } 
   */
   //alert(result.msg? result.msg : '登录成功')
   if (result.code === 1) {
            // 显示成功消息
            ElMessage.success(result.success ? result.msg : '登录成功')
            
            // 存储 token
            tokenStore.setToken(result.data)
            
            // 检查是否为老师并跳转到相应页面
            if (result.msg==="teacher") {
                router.push('/teacher')
            } else {
                router.push('/student')
            }
        } else {
            // 处理失败情况
            ElMessage.error(result.msg ? result.msg : '登录失败')
        }
   //把得到的token存储到pinia中
}


//定义函数,清空数据模型的数据

const clearRegisterData = ()=>{
    registerData.value={
        email: '',
    password: '',
    isteacher : false,
    rePassword: '',
    department: '',
    researchArea: '',
    title: '',
    officeAddress: '',
    name: '',
    grade: '',
    major: '',
    studentId: ''
    }
}

</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户邮箱" v-model="registerData.email"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                        v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-if="registerData.isTeacher">
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的姓名"
                        v-model="registerData.name"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-else>
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的姓名"
                        v-model="registerData.name"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-if="registerData.isTeacher">
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的专业"
                        v-model="registerData.department"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-else="registerData.isTeacher">
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的专业"
                        v-model="registerData.major"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-if="registerData.isTeacher">
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的研究领域"
                        v-model="registerData.researchArea"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" v-else="registerData.isTeacher">
                    <el-input :prefix-icon="Lock"  placeholder="请输入您的学号"
                        v-model="registerData.studentId"></el-input>
                </el-form-item>
                <el-radio-group  @change="radioGroupChange" class="ml-4">
                    <el-radio value = flase size="large" @click="registerData.isTeacher = false;">学生</el-radio>
                    <el-radio value = true size="large" @click="registerData.isTeacher = true;">老师</el-radio>
                </el-radio-group>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户邮箱" v-model="registerData.email"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
                        注册 →
                    </el-link>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="adminlogin();clearRegisterData()">
                        管理员登录 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: //url('@/assets/logo.jpg') no-repeat 60% center / 240px auto,
            url('@/assets/R-B.png') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>