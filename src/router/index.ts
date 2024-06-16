import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import LayoutteacherVue from '@/views/LayoutTeacher.vue'
import LayoutstudentVue from '@/views/LayoutStudent.vue'
import teacherinfoVue from '@/views/user/TeacherInfo.vue'
import studentinfoVue from '@/views/user/Studentinfo.vue'
import freetimemanageVue from '@/views/traction/teachersFreetimeOut.vue'
import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs'
import TeacherInfo from '@/views/user/TeacherInfo.vue'
import searchVue from '@/views/student/searchOUT.vue'
import adminVue from '@/views/adminlogin.vue'
import LayoutAdmin from '@/views/LayoutAdmin.vue'
import deleteTeacher from '@/views/admin/ManageTeacher.vue'
import applicationVue from '@/views/traction/searchOUT.vue'
import selectteacherVue from '@/views/student/FreetimeOut.vue'


const routes = [
  { path: '/login', component: LoginVue },{path: '/adminlogin',component:adminVue},
  { path: '/', component: LoginVue},
  {
      path: '/teacher', component: LayoutteacherVue, redirct: '/traction/managefreetime',children: [
        {
          path: '/teacher/info',component: teacherinfoVue
        },
        {
          path: '/traction/managefreetime',component: freetimemanageVue
        },
        {
          path: '/teacher/search',component: applicationVue
        }
      ]
  },
  {
    path: '/student', component: LayoutstudentVue,LayoutteacherVue, redirct: '/student/search', children: [
      {
        path: '/student/info',component: studentinfoVue
      },
      {
        path: '/student/listfreetime',component: selectteacherVue
      },
      {
        path: '/student/search',component: searchVue
      }
    ]
},
{
  path: '/admin', component: LayoutAdmin,LayoutteacherVue, redirct: '/teacher/search', children: [
    {
      path: '/teacher/info',component: teacherinfoVue
    },
    {
      path: '/admin/delete',component: deleteTeacher
    },
    {
      path: '/teacher/search',component: searchVue
    }
  ]
}
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes
})

export default router
