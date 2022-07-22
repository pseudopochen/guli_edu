<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="info">
        <el-tag type="info">excel template</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/static/categories.xlsx'"
            >click to download an example Excel file</a
          >
        </el-tag>
      </el-form-item>

      <el-form-item label="Choose Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/eduservice/subject/addSubject'"
          name="file"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        >
          <el-button slot="trigger" size="small" type="primary"
            >Choose File</el-button
          >
          <el-button
            :loading="loading"
            style="margin-left: 10px"
            size="small"
            type="success"
            @click="submitUpload"
            >Upload</el-button
          >
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false,
    };
  },
  created() {},
  methods: {
    submitUpload() {
      this.importBtnDisabled = true;
      this.loading = true;
      this.$refs.upload.submit();
    },
    fileUploadSuccess(response) {
      this.loading = false;
      this.$message({
        type: "success",
        message: "upload success!",
      });
      this.$router.push({path: '/subject/list'})
    },
    fileUploadError() {
      this.loading = false;
      this.$message({
        type: "error",
        message: "upload failed!",
      });
    },
  },
};
</script>
