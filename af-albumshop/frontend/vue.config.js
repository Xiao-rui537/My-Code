const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
})
module.exports = {
  runtimeCompiler: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // Spring Boot 后端服务地址
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '',  // 去掉 /api 前缀
        },
      },
    },
  },
};

