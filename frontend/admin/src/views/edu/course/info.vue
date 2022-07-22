<template>
  <div class="app-container">
    <h2 style="text-align: center">Publish New Course</h2>

    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="basic info" />
      <el-step title="chapters" />
      <el-step title="publish" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="title">
        <el-input
          v-model="courseInfo.title"
          placeholder=" example: machine learning, from begginner to master"
        />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="categories">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="Level 1 Category"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>

        <!-- 二级分类 -->
        <el-select
          v-model="courseInfo.subjectId"
          placeholder="Level 2 Category"
        >
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="teacher">
        <el-select v-model="courseInfo.teacherId" placeholder="choose">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="total hours">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="totoal course hours"
        />
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="description">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="cover">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover" />
        </el-upload>
      </el-form-item>

      <el-form-item label="price">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="0 for free courses"
        />
        Dollar
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >Save and Next</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";
export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        title: "",
        subjectId: "", //二级分类id
        subjectParentId: "", //一级分类id
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover: "/static/01.jpg",
        price: 0,
      },
      teacherList: [],
      subjectOneList: [],
      subjectTwoList: [],
      BASE_API: process.env.BASE_API,
      courseId: "",
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
        this.courseId = this.$route.params.id;
        this.getInfo();
      } else {
        this.courseInfo = {
          title: "",
          subjectId: "", //二级分类id
          subjectParentId: "", //一级分类id
          teacherId: "",
          lessonNum: 0,
          description: "",
          cover: "/static/01.jpg",
          price: 0,
        };
        this.getListTeacher();
        this.getOneSubject();
      }
    },
    getInfo() {
      course.getCourseInfoId(this.courseId).then((response) => {
        this.courseInfo = response.data.courseInfoVo;
        subject.getSubjectList().then((response) => {
          this.subjectOneList = response.data.list;
          for (let i = 0; i < this.subjectOneList.length; i++) {
            if (this.courseInfo.subjectParentId === this.subjectOneList[i].id) {
              this.subjectTwoList = this.subjectOneList[i].children;
            }
          }
        });
        this.getListTeacher();
      });
    },
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("avatar image must be in JPG format!");
      }
      if (!isLt2M) {
        this.$message.error("avatar image must be smaller than 2MB!");
      }
      return isJPG && isLt2M;
    },
    subjectLevelOneChanged(value) {
      for (let i = 0; i < this.subjectOneList.length; i++) {
        if (value === this.subjectOneList[i].id) {
          this.subjectTwoList = this.subjectOneList[i].children;
          this.courseInfo.subjectId = "";
          break;
        }
      }
    },
    getOneSubject() {
      subject.getSubjectList().then((response) => {
        this.subjectOneList = response.data.list;
      });
    },
    getListTeacher() {
      course.getListTeacher().then((response) => {
        this.teacherList = response.data.items;
      });
    },
    addCourse() {
      course.addCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "add course success!",
        });
        this.$router.push({
          path: "/course/chapter/" + response.data.courseId,
        });
      });
    },
    updateCourse() {
      course.updateCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "update course info success!",
        });
        this.$router.push({
          path: "/course/chapter/" + this.courseId,
        });
      });
    },
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        this.addCourse();
      } else {
        this.updateCourse();
      }
    },
  },
};
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
