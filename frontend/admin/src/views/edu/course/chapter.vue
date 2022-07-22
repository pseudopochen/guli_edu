<template>
  <div class="app-container">
    <h2 style="text-align: center">Publish New Course</h2>

    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="basic info" />
      <el-step title="chapters" />
      <el-step title="publish" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog">Add Chapter</el-button>

    <ul class="chapterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button style="" type="text" @click="openVideo(chapter.id)"
              >add section</el-button
            >
            <el-button style="" type="text" @click="openEditChapter(chapter.id)"
              >update</el-button
            >
            <el-button type="text" @click="removeChapter(chapter.id)"
              >delete</el-button
            >
          </span>
        </p>
        <ul class="chapterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button style="" type="text" @click="openEditVideo(video.id)"
                  >update</el-button
                >
                <el-button type="text" @click="removeVideo(video.id)"
                  >delete</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">Previous</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
          >Next</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="Add Chapter">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="title">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="order">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveOrUpdate">Confirm</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="add video">
      <el-form :model="video" label-width="120px">
        <el-form-item label="video title">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="video order">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="price">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">free</el-radio>
            <el-radio :label="false">default</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="upload video">
          <!-- TODO -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">Cancel</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >Confirm</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
  data() {
    return {
      saveBtnDisabled: false,
      courseId: "",
      chapterVideoList: [],
      chapter: {
        title: "",
        sort: 0,
      },
      video: {
        title: "",
        sort: 0,
        isFree: 0,
        videoSourceId: "",
      },
      dialogChapterFormVisible: false,
      dialogVideoFormVisible: false,
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideo();
    }
  },
  methods: {
    openEditVideo(id) {
      this.dialogVideoFormVisible = true;
      video.getVideo(id).then((response) => {
        this.video = response.data.video;
      });
    },

    updateVideo() {
      video.updateVideo(this.video).then((response) => {
        this.dialogVideoFormVisible = false;
        this.$message({
          type: "success",
          message: "update video success",
        });
        //update the page
        this.getChapterVideo();
      });
    },

    removeVideo(id) {
      this.$confirm("are your sure?", "tips", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        type: "warning",
      }).then(() => {
        video.deleteVideo(id).then((response) => {
          this.$message({
            type: "success",
            message: "delete video success!",
          });
          this.getChapterVideo();
        });
      });
    },

    openVideo(chapterId) {
      this.dialogVideoFormVisible = true;
      this.video.chapterId = chapterId;
      this.video.title = "";
      this.video.sort = 0;
      this.video.free = 0;
      this.videoSourceId = "";
    },

    addVideo() {
      this.video.courseId = this.courseId;

      video.addVideo(this.video).then((response) => {
        this.dialogVideoFormVisible = false;
        this.$message({
          type: "success",
          message: "add video success",
        });
        this.getChapterVideo();
      });
    },

    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.addVideo();
      } else {
        this.updateVideo();
      }
    },

    removeChapter(chapterId) {
      this.$confirm("are your sure?", "tips", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        type: "warning",
      }).then(() => {
        chapter.deleteChapter(chapterId).then((response) => {
          this.$message({
            type: "success",
            message: "delete chapter success!",
          });
          this.getChapterVideo();
        });
      });
    },
    openEditChapter(chapterId) {
      this.dialogChapterFormVisible = true;
      chapter.getChapter(chapterId).then((response) => {
        this.chapter = response.data.chapter;
      });
    },
    openChapterDialog() {
      this.dialogChapterFormVisible = true;
      this.chapter.title = "";
      this.chapter.sort = 0;
    },
    addChapter() {
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then((response) => {
        this.dialogChapterFormVisible = false;
        this.$message({
          type: "success",
          message: "add chapter success",
        });
        //update the page
        this.getChapterVideo();
      });
    },
    updateChapter() {
      chapter.updateChapter(this.chapter).then((response) => {
        this.dialogChapterFormVisible = false;
        this.$message({
          type: "success",
          message: "update chapter success",
        });
        //update the page
        this.getChapterVideo();
      });
    },
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.addChapter();
      } else {
        this.updateChapter();
      }
    },
    getChapterVideo(id) {
      chapter.getAllChapterVideo(this.courseId).then((response) => {
        this.chapterVideoList = response.data.allChapterVideo;
      });
    },

    previous() {
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    next() {
      //跳转到第二步
      this.$router.push({ path: "/course/publish/" + this.courseId });
    },
  },
};
</script>
<style scoped>
.chapterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chapterList li {
  position: relative;
}
.chapterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chapterList .acts {
  position: relative;
  z-index: 99;
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>