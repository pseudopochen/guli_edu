<template>
  <div class="app-container">
    Add Teacher
    <el-form label-width="120px">
      <el-form-item label="teacher name">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="teacher ranking">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          min="0"
        />
      </el-form-item>
      <el-form-item label="teacher level">
        <el-select v-model="teacher.level" clearable placeholder="choose">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="teacher career">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="teacher intro">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >Save</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacherApi from "@/api/edu/teacher";
export default {
  data() {
    return {
      teacher: {
        name: "",
        sort: 0,
        level: 1,
        career: "",
        intro: "",
        avatar: "",
      },
      saveBtnDisabled: false,
    };
  },
  created() {
    this.init();
  },
  watch: {
    $route(to, from) {
      this.init();
    },
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.getInfo(this.$route.params.id);
      } else {
        this.teacher = {};
      }
    },
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then((response) => {
        this.teacher = response.data.teacher;
      });
    },
    saveOrUpdate() {
      if (!this.teacher.id) {
        this.saveTeacher();
      } else {
        this.updateTeacher();
      }
    },
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then((response) => {
        this.$message({
          type: "success",
          message: "update teacher success!",
        });
        this.$router.push({ path: "/teacher/table" });
      });
    },
    saveTeacher() {
      teacherApi.addTeacher(this.teacher).then((response) => {
        this.$message({
          type: "success",
          message: "add teacher success!",
        });
        this.$router.push({ path: "/teacher/table" });
      });
    },
  },
};
</script>