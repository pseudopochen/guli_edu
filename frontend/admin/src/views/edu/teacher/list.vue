<template>
  <div class="app-container">
    Teacher List
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="Name" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="Level">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="Create Time">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="choose create time"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="choose end time"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >Search</el-button
      >
      <el-button type="default" @click="resetData()">Clear</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="ID" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="Name" width="80" />

      <el-table-column label="Title" width="80">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="Intro" />

      <el-table-column prop="gmtCreate" label="Create Time" width="160" />

      <el-table-column prop="sort" label="Sort" width="60" />

      <el-table-column label="Action" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >Update</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >Delete</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import teacher from "@/api/edu/teacher";
export default {
  data() {
    return {
      list: null,
      page: 1,
      limit: 5,
      total: 0,
      teacherQuery: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList(page = 1) {
      this.page = page;
      teacher
        .getTeacherListPage(this.page, this.limit, this.teacherQuery)
        .then((response) => {
          this.list = response.data.rows;
          console.log(this.list);
          this.total = response.data.total;
          console.log(this.total);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.teacherQuery = {};
      this.getList();
    },
    removeDataById(id) {
      this.$confirm(
        "This operation will delete teacher data permanently, continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        teacher.deleteTeacherById(id).then((response) => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "delete success!",
          });
          //回到列表页面
          this.getList();
        });
      }); //点击取消，执行catch方法
    },
  },
};
</script>
