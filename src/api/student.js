import request from '@/utils/request.js'

export const userRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    console.log(registerData.value);
    return request.post('/user/register',registerData.value);
}


export const studentRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    console.log(registerData.value);
    return request.post('/students',registerData.value);
}
export const getfreeTimestoappointmment = (param)=>{
    return request.post("/getfreeTimestoappointmment",param);
}