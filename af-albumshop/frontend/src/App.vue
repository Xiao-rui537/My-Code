<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 定义根容器样式 */
#app {
  margin: 0; /* 去除外边距 */
  padding: 0; /* 去除内边距 */
  box-sizing: border-box; /* 设置边框盒模型 */
}

/* 定义 HTML 和 Body 的基础样式 */
html, body {
  margin: 0; /* 去除默认外边距 */
  padding: 0; /* 去除默认内边距 */
  height: 100%; /* 设置高度为 100% */
  background: linear-gradient(30deg, #E5E4E2, #FFFFFF); /* 设置背景为 30 度线性渐变，颜色从铂色到灰白色 */
}
</style>