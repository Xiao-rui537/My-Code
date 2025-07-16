<template>
  <div class="register">
    <el-card class="register-card">
      <h2 class="register-title">
        <i class="el-icon-s-custom"></i> 注册账号
      </h2>
      <!-- 显示错误信息 -->
      <el-alert
          v-if="registerError"
          title="注册失败"
          type="error"
          :description="registerError"
          show-icon
          closable
          @close="registerError = ''"
      ></el-alert>

      <el-form :model="form" :rules="rules" ref="registerForm">
        <el-form-item label="账号" prop="account">
          <el-input
              v-model="form.account"
              placeholder="请输入账号"
              prefix-icon="el-icon-user"
          />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              type="password"
              v-model="form.password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
          />
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
          />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="男" value="男">男</el-radio>
            <el-radio label="女" value="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="出生日期" prop="birthdate">
          <el-date-picker
              v-model="form.birthdate"
              type="date"
              placeholder="选择出生日期"
              :picker-options="datePickerOptions"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="住址" prop="address">
          <el-input
              v-model="form.address"
              placeholder="请输入住址"
              prefix-icon="el-icon-location"
          />
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input
              v-model="form.phone"
              placeholder="请输入电话号码"
              prefix-icon="el-icon-phone"
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              :loading="registerLoading"
              class="register-button"
              style="background:#F2C76E; color: white; border-color:#F2C76E; padding: 12px 24px"
              @click="submitForm"
          >
            <i class="el-icon-s-promotion"></i> 注册
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { Message } from 'element-ui'; // 从 element-ui 导入 Message 组件，用于显示全局消息提示
import axios from 'axios'; // 导入 axios 用于发送 HTTP 请求

export default {
  // 组件的响应式数据
  data() {
    return {
      // 注册表单的数据对象，包含用户的各项信息
      form: {
        account: '', // 用户账号
        password: '', // 用户密码
        username: '', // 用户名
        gender: 'male', // 用户性别，默认值为男
        birthdate: '', // 出生日期
        address: '', // 住址
        phone: '', // 电话号码
      },
      registerLoading: false, // 注册按钮的加载状态，用于显示加载动画
      registerError: '', // 注册过程中出现的错误信息
      // 表单验证规则
      rules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }, // 账号为必填项，失焦时触发验证
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }, // 密码为必填项，失焦时触发验证
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }, // 用户名为必填项，失焦时触发验证
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }, // 性别为必选项，选择时触发验证
        ],
        birthdate: [
          { required: true, message: '请选择出生日期', trigger: 'change' }, // 出生日期为必选项，选择时触发验证
        ],
        address: [
          { required: true, message: '请输入住址', trigger: 'blur' }, // 住址为必填项，失焦时触发验证
        ],
        phone: [
          { required: true, message: '请输入电话号码', trigger: 'blur' }, // 电话号码为必填项，失焦时触发验证
          { pattern: /^[1][3-9][0-9]{9}$/, message: '请输入有效的电话号码', trigger: 'blur' }, // 电话号码必须符合中国大陆手机号格式
        ],
      },
      // 日期选择器的配置选项
      datePickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now(); // 禁止选择未来的日期
        },
      },
    };
  },
  methods: {
    /**
     * 处理注册表单的提交
     */
    async submitForm() {
      // 使用 Promise 包装表单验证，以便使用 async/await 语法
      const isValid = await new Promise((resolve) => {
        this.$refs.registerForm.validate((valid) => {
          resolve(valid); // 返回验证结果
        });
      });

      if (isValid) { // 如果表单验证通过
        this.registerLoading = true; // 设置注册按钮为加载状态
        try {
          // 使用 axios 发送 POST 请求到注册接口，发送表单数据作为 JSON
          const response = await axios.post('http://localhost:8080/register', this.form, {
            headers: {
              'Content-Type': 'application/json', // 设置请求头，确保发送的是 JSON 格式
            },
          });

          if (response.data.code === 200) { // 注册成功
            // 使用 Vue Router 导航到登录页面
            this.$router.push('/login');
            // 显示成功消息
            Message.success('注册成功！');
          } else { // 注册失败
            // 设置错误信息，如果后端返回了具体消息则使用，否则使用默认消息
            this.registerError = response.data.message || '注册失败，请稍后重试';
          }
        } catch (error) { // 捕获网络错误或其他异常
          console.error(error); // 在控制台输出错误信息，便于调试
          this.registerError = '网络错误，请稍后再试'; // 设置用户可见的错误信息
        } finally {
          this.registerLoading = false; // 无论成功与否，停止加载状态
        }
      } else { // 如果表单验证未通过
        this.registerError = '表单验证失败，请检查输入项'; // 设置错误信息提示用户
      }
    },
  },
};
</script>

<style scoped>
.register {
  width: 100%;
  height: 100%;
  background-image: url('../assets/img/BG.webp');
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
}

.register-card {
  width: 400px;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  animation: slideIn 0.6s ease-out, zoomIn 0.6s ease-out 0.6s;
  opacity: 1; /* 确保动画结束后保持可见 */
}

@keyframes slideIn {
  0% {
    transform: translateY(100px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

.register-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

.register-button {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  transition: transform 0.3s ease;
}

.register-button:hover {
  transform: scale(1.1);
}

.el-alert {
  margin-bottom: 20px;
}

/* 使用 ::v-deep 深度选择器来覆盖 Element UI 的样式 */
::v-deep .el-radio.is-checked .el-radio__inner {
  background-color: #F2C76E !important; /* 选中的 radio 按钮背景色 */
  border-color: #F2C76E !important; /* 选中的 radio 按钮边框色 */
}

/* 修改选中时的文字颜色 */
::v-deep .el-radio.is-checked .el-radio__label {
  color: #F2C76E !important; /* 选中状态下的文字颜色 */
}
</style>
