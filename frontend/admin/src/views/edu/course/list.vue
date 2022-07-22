<template>
  <div class="app-container">
   Course List

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="course name"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="course status">
          <el-option value="Normal" label="published"/>
          <el-option value="Draft" label="not published"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">Search</el-button>
      <el-button type="default" @click="resetData()">Clear</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="order"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="title" width="80" />

      <el-table-column label="status" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'published':'not published' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="videos" />

      <el-table-column prop="gmtCreate" label="create time" width="160"/>

      <el-table-column prop="viewCount" label="view count" width="60" />

      <el-table-column label="actions" width="400" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">edit basic info</el-button>
          </router-link>
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">edit chapters</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">delete</el-button>
        </template>
      </el-table-column>
    </el-table>

  <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>
<script>
//引入调用teacher.js文件
import course from '@/api/edu/course'

export default {
    //写核心代码位置
    // data:{
    // },
    data() { //定义变量和初始值
        return {
          list:null,//查询之后接口返回集合
          page:1,//当前页
          limit:10,//每页记录数
          total:0,//总记录数
          courseQuery:{} //条件封装对象
        }
    },
    created() { //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList() 
    },
    methods:{  //创建具体的方法，调用teacher.js定义的方法
        //讲师列表的方法
        getList() {
            course.getListCourse()
                .then(response =>{//请求成功
                    //response接口返回的数据
                    this.list = response.data.list
                }) 
        },
        resetData() {//清空的方法
            //表单输入项数据清空
            this.courseQuery = {}
            //查询所有讲师数据
            this.getList()
        }
 
    }
}
</script>