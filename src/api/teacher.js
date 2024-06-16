import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const userRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    console.log(registerData.value);
    return request.post('/user/register',registerData.value);
}


export const teacherRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    console.log(registerData.value);
    return request.post('/teachers',registerData.value);
}

export const teacherInfoService = ()=>{
    return request.get('/teacher/getthisteacherinfo')
}

export const getteacherInfoService = ()=>{
    return request.get('/teachers')
}

export const getteacherlistService = (params)=>{
    console.log(params)
    return request.get('/teacher/getlist',{params})
}

export const getfreetimeListService = (params)=>{
    return request.get('/teacher/freetime/getlist',{params})
}

export const getAlldepartmentService = ()=>{
    return request.get('teachers/departments')
}
export const inserfreetimeService = (param)=>{

    return request.post("/freeTimes",param.value)
}

export const deleteATeacherService = (param)=>{
    return request.post("/teachers/delete",param)
}

export const deletefreetimeService = (param)=>{
    return request.post("/freeTimes/deletefreetimebyslot",param)
}


export const searchallapplicationService = ()=>{
    return request.get("/appointments/teacherapplication")
}

export const selectstudent = (param)=>{
    return request.post("/teacher/selectstudent",param)
}

export const getallfreetimeListService = (params)=>{
    console.log(params)
    return request.get("/freetime/getlist",{params})
}