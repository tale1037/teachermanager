//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    console.log(registerData.value);
    return request.post('/UserPasswords',registerData.value);
}

//提供调用登录接口的函数
export const userLoginService = (loginData)=>{

    return request.post('/user/login',loginData.value);
}


//获取教师详细信息
export const teacherInfoService = ()=>{
    return request.get('/teacher/getthisteacherinfo')
}
//获取学生详细信息
export const studentInfoService = ()=>{
    return request.get('/student/**')
}

export const userInfoService = ()=>{
    return request.get('/youselfnews')
}


//修改个人信息
export const userInfoUpdateService = (userInfoData)=>{
   return request.put('/user/update',userInfoData)
}

//修改头像
export const userAvatarUpdateService = (avatarUrl)=>{
    const params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}
