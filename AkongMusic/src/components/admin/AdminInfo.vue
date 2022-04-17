<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="12" :xs="24">
        <el-card class="card-text" shadow="always">
          <h3>1</h3>
          <h3>用户总数</h3>
        </el-card>
      </el-col>

      <el-col :span="12" :xs="24">
        <el-card class="card-text" shadow="always">
          <h3>10</h3>
          <h3>歌曲总数</h3>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "AdminInfo"
}
</script>

<style>
.card-text {
  text-align: center;
  color: gray;
}
</style>
