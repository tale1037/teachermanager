<script setup>
import {
    Edit,
    Delete,
    Check
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章分类数据模型
const categorys = ref([
    {
        "id": 3,
        "categoryName": "cs",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "categoryName": "d",
        "categoryAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "categoryName": "phy",
        "categoryAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//用户搜索输入的老师姓名
const inputname = ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅2攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    articleList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    articleList()
}


//回显文章分类
import { articleCategoryListService, articleListService,articleAddService } from '@/api/article.js'
import { getteacherlistService,getAlldepartmentService} from "@/api/teacher.js"
const articleCategoryList = async () => {
    let result = await getAlldepartmentService();
    console.log(result.data)
    categorys.value = result.data;
}
import {searchallapplicationService} from '@/api/teacher.js'
//获取文章列表数据
const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        inputname: inputname.value ? inputname.value : null,
        state: state.value ? state.value : null
    }
    console.log(inputname.value)
    let result = await searchallapplicationService();
    console.log(result)

    //渲染视图
    total.value = result.data.total;
    articles.value = result.data;

    //处理数据,给数据模型扩展一个属性categoryName,分类名称
    for (let i = 0; i < articles.value.length; i++) {
        let article = articles.value[i];
        for (let j = 0; j < categorys.value.length; j++) {
            if (article.categoryId == categorys.value[j].id) {
                article.categoryName = categorys.value[j].categoryName;
            }
        }
    }
}


articleCategoryList()
articleList();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    name: '',
    department: '',
    email: '',
    researchArea: '',
    password: '123456',
    isTeacher: true
})


//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result)=>{
    articleModel.value.coverImg = result.data;
    console.log(result.data);
}
const handleClick = (param)=>{
    console.log(param);
}
import {deletefreetimeService} from '@/api/teacher.js'
const deleteFreetime = async (param)=>{
    
    let result = await deletefreetimeService(param);
    console.log(result)
    articleList();
}
import {selectstudent} from '@/api/teacher.js'
const select = async (param)=>{
    console.log(param);
    let result = await selectstudent(param);
    ElMessage.success(result.msg ? result.msg : '添加成功');

    //让抽屉消失
    visibleDrawer.value = false;

    //刷新当前列表
    articleList()
}
import {deleteATeacherService} from '@/api/teacher.js'
const deleteTeacher = async (param)=>{
    let result = await deleteATeacherService(param);
    articleList();
}
//添加文章
import {ElMessage} from 'element-plus'
import {teacherRegisterService} from '@/api/teacher.js'
import { userRegisterService, userLoginService} from '@/api/user.js'
const addArticle = async (clickState)=>{
    //把发布状态赋值给数据模型
    //articleModel.value.state = clickState;
    let result = await userRegisterService(articleModel);
    /* if (result.code === 0) {
        //成功了
        alert(result.msg ? result.msg : '注册成功');
    }else{
        //失败了
        alert('注册失败')
    } */
    //alert(result.msg ? result.msg : '注册成功');
    if(result.msg == "success"){
        console.log(articleModel.value.isTeacher)
        let result1 = await teacherRegisterService(articleModel);
        ElMessage.success(result.msg? result.msg:'添加成功');
    }
    
    else{
        ElMessage.success("注册失败，邮箱已被注册！")
    }

    //让抽屉消失
    visibleDrawer.value = false;

    //刷新当前列表
    articleList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>预约申请</span>
            </div>
        </template>
        <!-- 搜索表单 -->

        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="学生邮箱" width="200" prop="studentEmail"></el-table-column>
            <el-table-column label="时间" prop="timeSlot"></el-table-column>
            <el-table-column label="日期" prop="date"> </el-table-column>
            <el-table-column label="操作" width="100">
                <template v-slot="scope">
                <el-button :icon="Check" circle plain type="success" @click="select(scope.row)"></el-button>
              </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加教师" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="教师姓名">
                    <el-input v-model="articleModel.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="教师邮箱">
                    <el-input v-model="articleModel.email" placeholder="请输入教师邮箱"></el-input>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="articleModel.department" placeholder="请选择教师专业"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="articleModel.title" placeholder="请输入教师职称"></el-input>
                </el-form-item>
                <el-form-item label="办公地址">
                    <el-input v-model="articleModel.officeAddress" placeholder="请输入教师办公地址"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addArticle('已发布')">添加</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>