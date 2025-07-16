<template>
  <div class="login">
    <!-- 登录卡片 -->
    <el-card class="login_card">
      <!-- 显示商标 -->
      <el-image class="logo_image" :src="logo_url" fit="cover"></el-image>
      <!-- 登录标题 -->
      <p class="login_title">登 录</p>
      <!-- 登录描述信息 -->
      <p class="login_desc">
        <i class="el-icon-shopping-cart-full" style="margin-right: 8px;"></i> 欢迎登录AF唱片商城
      </p>

      <!-- 显示登录错误信息 -->
      <el-alert
          v-if="loginError"
          title="登录失败"
          type="error"
          :description="loginError"
          show-icon
          closable
          @close="loginError = ''"
      ></el-alert>

      <!-- 登录表单 -->
      <el-form ref="ruleForm" :model="form" :rules="rules">
        <!-- 账号输入框 -->
        <el-form-item prop="userAccount">
          <el-input
              placeholder="请输入账号"
              v-model="form.userAccount"
              prefix-icon="el-icon-user"
          />
        </el-form-item>
        <!-- 密码输入框 -->
        <el-form-item prop="userPassword">
          <el-input
              type="password"
              placeholder="请输入密码"
              v-model="form.userPassword"
              prefix-icon="el-icon-lock"
          />
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button
              :loading="loginLoading"
              type="primary"
              class="login-button"
              style="background: #BD90EA; color: white; border-color: #BD90EA; margin-bottom: 16px;"
              @click="submitForm('ruleForm')"
          >
            <i class="el-icon-thumb" style="margin-right: 8px;"></i> 登录
          </el-button>
          <!-- 注册按钮 -->
          <el-form-item>
            <el-button
                type="primary"
                class="register-button"
                style="background: #F2C76E; color: white; border-color: #F2C76E; padding: 12px 24px"
                @click="goToRegister"
            >
              <i class="el-icon-s-custom" style="margin-right: 8px;"></i> 注册
            </el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 页脚 -->
    <p class="footer">Copyright © AF AlbumShop All Rights Reserved</p>
  </div>
</template>

<script>
import axios from 'axios'; // 导入 axios 用于发送 HTTP 请求

export default {
  // 组件的数据部分
  data() {
    return {
      // 商标图片的路径，使用 require 引入本地资源
      logo_url: require("@/assets/img/logo.jpeg"),

      // 表单数据对象，包含用户账号和密码
      form: {
        userAccount: "",  // 用户账号
        userPassword: "", // 用户密码
      },

      // 登录按钮的加载状态，用于显示加载动画
      loginLoading: false,

      // 登录过程中出现的错误信息
      loginError: "",

      // 表单验证规则
      rules: {
        userAccount: [
          { required: true, message: "请输入账号", trigger: "blur" }, // 账号为必填项，失焦时触发验证
        ],
        userPassword: [
          { required: true, message: "请输入密码", trigger: "blur" }, // 密码为必填项，失焦时触发验证
        ],
      },
    };
  },

  // 组件挂载完成后执行的钩子函数
  mounted() {
    this.showNotification(); // 在组件挂载时显示欢迎通知
  },

  // 组件的方法集合
  methods: {
    /**
     * 显示欢迎通知
     * 使用 Element UI 的通知组件显示成功类型的通知
     */
    showNotification() {
      this.$notify.success({
        title: '用户你好 ^_^', // 通知标题
        message: '欢迎来到AF唱片商城，请登录以进入主页', // 通知内容
        showClose: false // 是否显示关闭按钮，设为 false 则不显示
      });
    },

    /**
     * 处理表单提交
     * @param {String} formName - 表单的引用名称，用于验证
     */
    async submitForm(formName) {
      // 使用 Promise 包装表单验证，以便使用 async/await 语法
      const isValid = await new Promise((resolve) => {
        this.$refs[formName].validate((valid) => {
          resolve(valid); // 验证结果
        });
      });

      if (isValid) { // 如果表单验证通过
        this.loginLoading = true; // 设置登录按钮为加载状态
        try {
          // 构建登录请求的 payload，去除前后空格
          const payload = {
            userAccount: this.form.userAccount.trim(),
            userPassword: this.form.userPassword.trim(),
          };

          // 使用 axios 发送 POST 请求到登录接口
          const response = await axios.post('http://localhost:8080/login', null, {
            params: payload, // 请求参数作为 URL 参数发送
          });

          // 根据返回的状态码处理不同的情况
          if (response.data.code === 200) { // 登录成功
            const isAdmin = response.data.isAdmin;  // 判断用户是否为管理员
            // 将用户 Token 和 ID 存储到本地存储中
            localStorage.setItem('userToken', response.data.token);
            localStorage.setItem('userId', response.data.id);
            this.loginError = ""; // 清除任何之前的错误信息

            // 根据用户角色跳转到不同的页面
            if (isAdmin) {
              await this.$router.push("/Manage"); // 管理员跳转到管理页面
            } else {
              await this.$router.push("/home"); // 普通用户跳转到主页
            }
          } else if(response.data.code === 400){ // 登录失败，可能是账号或密码错误
            // 设置错误信息，如果后端返回了具体消息则使用，否则使用默认消息
            this.loginError = response.data.message || "登录失败，请检查用户名和密码";
          }
        } catch (error) { // 处理网络错误或其他异常
          console.error(error); // 在控制台输出错误信息，便于调试
          this.loginError = "网络错误，请稍后再试"; // 设置用户可见的错误信息
        } finally {
          this.loginLoading = false; // 无论成功与否，停止加载状态
        }
      } else { // 如果表单验证未通过
        this.loginError = "表单验证失败，请检查输入项"; // 设置错误信息提示用户
      }
    },

    /**
     * 跳转到注册页面的方法
     * 使用 Vue Router 的 push 方法导航到注册页面路由
     */
    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>
<style scoped>
/* 登录页样式 */
.login {
  width: 100%;
  height: 100%;
  background-image: url("../assets/img/BG.webp");
  position: absolute;
  left: 0;
  top: 0;
  background-size: 100% 100%;
}

/* 登录卡片样式 */
.login_card {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 20%;
  min-width: 300px;
  height: 500px;
  min-height: 500px;
  border-radius: 10px;
  text-align: center;

  /* 初始位置 - 登录卡片开始时在视口下方 */
  transform: translateY(100%);
  animation: slideInUp 1s ease-out forwards;
}

/* 商标样式 */
.logo_image {
  width: 50px;
  height: 50px;
  margin-top: 40px;
}

/* 登录标题样式 */
.login_title {
  font-size: 25px;
  font-weight: bold;
}

/* 登录描述样式 */
.login_desc {
  letter-spacing: 2px;
  color: #999a9a;
}

/* 页脚样式 */
.footer {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 20px;
  text-align: center;
  color: rgb(183, 181, 181);
}

.footer:hover {
  color: white;
}

/* 按钮放大动画 */
.login-button, .register-button {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.login-button:hover, .register-button:hover {
  transform: scale(1.05); /* 放大按钮 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 增加阴影效果 */
}

/* 登录卡片滑动和放大动画 */
@keyframes slideInUp {
  0% {
    transform: translateY(100%) scale(0.9); /* 初始位置并缩小 */
  }
  80% {
    transform: translateY(0) scale(1); /* 飞到中间并恢复正常大小 */
  }
  100% {
    transform: translateY(0) scale(1.05); /* 最终放大一点 */
  }
}

/* 登录和注册按钮样式 */
.login-button, .register-button{
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  transition: transform 0.3s ease;
}
</style>