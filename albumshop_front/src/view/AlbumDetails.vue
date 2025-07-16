<template>
  <div class="album-details">
    <div v-if="album" class="album-container">
      <!-- 专辑封面图片 -->
      <div class="album-image">
        <img :src="album.album_cover" alt="Album Cover" />
      </div>

      <!-- 专辑内容 -->
      <div class="album-info">
        <h2>{{ album.album_name }}</h2>
        <p><strong>专辑译名:</strong> {{ album.album_name_tran }}</p>
        <p><strong>歌手:</strong> {{ album.album_singer }}</p>
        <p><strong>发布时间:</strong> {{ album.album_release_day }}</p>
        <p><strong>原价:</strong> {{ album.album_price }}</p>
        <p><strong>折扣价:</strong> {{ album.album_sale }}</p>
        <p><strong>黑胶样式:</strong> {{ album.album_title }}</p>
        <p><strong>分类:</strong> {{ categoryName }}</p>
        <p><strong>专辑介绍:</strong> {{ album.album_introduction }}</p>
        <p><strong>语种:</strong> {{ album.album_language }}</p>
        <p><strong>专辑歌曲数目:</strong> {{ album.album_song }}</p>
        <p><strong>专辑歌曲列表:</strong></p>
        <ul class="album-song-list">
          <li v-for="(song, index) in filteredAlbumSongs" :key="index" style="padding-left: 20px;">
            {{ index + 1 }}. {{ song }}
          </li>
        </ul>
      </div>

      <!-- 购买按钮 -->
      <div class="purchase-button">
        <button @click="showPurchaseForm = true">购买专辑</button>
      </div>
    </div>

    <!-- 弹出购买表单 -->
    <el-dialog
        :visible="showPurchaseForm"
        width="50%"
        @close="closeForm"
        :before-close="handleBeforeClose"
        title="填写订单信息"
    >
      <el-form :model="order" ref="form" label-width="100px">
        <el-form-item label="收件人" :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]">
          <el-input v-model="order.order_receiver" placeholder="请输入姓名" />
        </el-form-item>

        <el-form-item label="购买账号" :rules="[{ required: true, message: '请输入账号', trigger: 'blur' }]">
          <el-input v-model="order.order_user_account" placeholder="请输入账号" />
        </el-form-item>

        <el-form-item label="联系电话" :rules="[{ required: true, message: '请输入电话', trigger: 'blur' }]">
          <el-input v-model="order.order_phone" placeholder="请输入电话" />
        </el-form-item>

        <el-form-item label="收货地址" :rules="[{ required: true, message: '请输入收货地址', trigger: 'blur' }]">
          <el-input v-model="order.order_address" placeholder="请输入收货地址" />
        </el-form-item>

        <el-form-item label="付款方式" :rules="[{ required: true, message: '请选择付款方式', trigger: 'blur' }]">
          <el-select v-model="order.order_pay_type" placeholder="请选择付款方式">
            <el-option label="银联储蓄卡" value="银联储蓄卡"></el-option>
            <el-option label="微信" value="微信"></el-option>
            <el-option label="支付宝" value="支付宝"></el-option>
            <el-option label="数字人民币" value="数字人民币"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 底部按钮 -->
      <div class="dialog-footer">
        <el-button @click="closeForm" type="primary" round class="cancel-btn">取消</el-button>
        <el-button
            type="primary" round
            @click="submitOrder"
            style="background-color: #333333; border-color: #333333;"
            class="submit-order-btn"
        >
          提交订单
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'; // 导入 axios，用于发送 HTTP 请求

export default {
  data() {
    return {
      // 当前专辑的详细信息，初始化为 null
      album: null,

      // 专辑所属分类的名称，初始化为 null
      categoryName: null,  // 分类名称

      // 控制购买表单弹出层的显示与隐藏，初始化为 false
      showPurchaseForm: false,  // 控制购买表单弹出显示

      // 订单信息对象，存储用户填写的订单详情
      order: {  // 订单信息
        order_receiver: '',        // 收件人姓名
        order_user_account: '',    // 用户账号
        order_address: '',         // 收件地址
        order_phone: '',           // 收件人电话
        order_pay_type: '银联储蓄卡', // 支付方式，默认选择“银联储蓄卡”
        order_price: '',           // 订单价格，将在提交时自动填充
      },
    };
  },

  // 组件创建时调用的生命周期钩子
  async created() {
    try {
      // 从路由参数中获取 albumId，并调用 fetchAlbumDetails 方法获取专辑详情
      await this.fetchAlbumDetails(this.$route.params.albumId);

      // 如果成功获取到专辑信息且专辑有分类 ID，则调用 fetchCategoryName 方法获取分类名称
      if (this.album && this.album.album_category_id) {
        await this.fetchCategoryName(this.album.album_category_id);
      }
    } catch (error) {
      // 捕获并记录创建过程中的任何错误
      console.error('Error during creation:', error);
    }
  },

  // 监听路由更新的钩子，用于在路由参数变化时重新获取专辑详情
  beforeRouteUpdate(to, from, next) {
    const newAlbumId = to.params.albumId; // 获取新的 albumId
    if (newAlbumId !== this.$route.params.albumId) {
      // 如果 albumId 发生变化，调用 fetchAlbumDetails 方法获取新专辑详情
      this.fetchAlbumDetails(newAlbumId);
    }
    next(); // 继续路由导航
  },

  computed: {
    /**
     * 计算属性：将专辑的歌曲名称字符串分割成数组
     * 假设歌曲名称以 '/' 分隔
     * @returns {Array} - 专辑歌曲名称数组
     */
    albumSongs() {
      // 分割字符串并去除多余的空格
      return this.album.album_song_name.split('/').map(song => song.trim());
    },

    /**
     * 计算属性：过滤掉空字符串或仅包含空格的歌曲名称
     * @returns {Array} - 过滤后的专辑歌曲名称数组
     */
    filteredAlbumSongs() {
      // 过滤掉无效的歌曲名称
      return this.albumSongs.filter(song => song && song.trim() !== '');
    },
  },

  methods: {
    /**
     * 获取专辑详情的方法
     * 发送 GET 请求到后端 API，根据 albumId 获取专辑的详细信息
     * @param {String|Number} id - 专辑的 ID
     */
    async fetchAlbumDetails(id) {
      try {
        const response = await axios.get(`http://localhost:8080/albums/${id}`); // 发送 GET 请求
        this.album = response.data; // 将返回的数据赋值给 album
      } catch (error) {
        console.error('Failed to fetch album details:', error); // 在控制台输出错误信息
        this.album = null; // 设置 album 为 null，表示未能成功获取数据
      }
    },

    /**
     * 获取分类名称的方法
     * 发送 GET 请求到后端 API，根据 categoryId 获取分类的名称
     * @param {String|Number} categoryId - 分类的 ID
     */
    async fetchCategoryName(categoryId) {
      try {
        const response = await axios.get(`http://localhost:8080/category/${categoryId}`); // 发送 GET 请求
        this.categoryName = response.data; // 将返回的数据赋值给 categoryName
      } catch (error) {
        console.error("无法加载分类名称:", error); // 在控制台输出错误信息
        this.categoryName = "无法加载分类名称"; // 设置默认值，表示无法获取分类名称
      }
    },

    /**
     * 提交订单的方法
     * 发送 POST 请求到后端 API，将订单信息提交
     */
    async submitOrder() {
      try {
        // 构建订单数据对象
        const orderData = {
          order_album_id: this.album.album_id,  // 订单包含的专辑 ID
          ...this.order,                         // 合并用户填写的订单信息
          order_price: this.album.album_sale,    // 将专辑的销售价格作为订单价格
        };

        // 发送 POST 请求提交订单
        const response = await axios.post('http://localhost:8080/orders/submit', orderData);

        if (response.status === 200) { // 如果响应状态码为 200，表示成功
          this.$message.success(response.data.message);  // 显示后端返回的成功消息
          this.closeForm();  // 调用 closeForm 方法关闭购买表单弹出层
        }
      } catch (error) {
        console.error("订单提交失败:", error); // 在控制台输出错误信息

        // 根据错误响应显示不同的错误消息
        if (error.response && error.response.data && error.response.data.message) {
          this.$message.error(error.response.data.message); // 显示后端返回的错误消息
        } else {
          this.$message.error('订单提交失败，请重试。'); // 显示默认错误消息
        }
      }
    },

    /**
     * 关闭购买表单弹出层的方法
     * 将 showPurchaseForm 设置为 false，隐藏表单
     */
    closeForm() {
      this.showPurchaseForm = false;  // 关闭表单
    },

    /**
     * 处理弹出层关闭前的逻辑
     * 验证表单，如果验证失败则阻止关闭
     * @param {Function} done - 完成关闭操作的回调函数
     */
    handleBeforeClose(done) {
      // 通过表单引用进行验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          done(); // 如果验证通过，继续关闭弹出层
        } else {
          console.log('表单验证失败'); // 在控制台输出验证失败的信息
        }
      });
    },
  },

  // 组件挂载完成后调用的生命周期钩子
  mounted() {
    // 从路由参数中获取 albumId，并调用 fetchAlbumDetails 方法获取专辑详情
    this.fetchAlbumDetails(this.$route.params.albumId);

    // 如果专辑存在且有分类 ID，则调用 fetchCategoryName 方法获取分类名称
    if (this.album && this.album.album_category_id) {
      this.fetchCategoryName(this.album.album_category_id);
    }
  },
};
</script>

<style scoped>

.album-details {
  background-color: #f0f0f0; /* 设置背景颜色为浅灰色 */
  padding: 20px; /* 可选，添加内边距使内容不紧贴边缘 */
}

.album-container {
  display: flex;
  align-items: flex-start; /* 垂直方向对齐顶部 */
  justify-content: space-between; /* 水平方向分配空间 */
  margin-top: 20px;
}

.album-image {
  flex: 1;
  margin-right: 20px; /* 给图片和内容之间留些空间 */
}

.album-image img {
  width: 100%; /* 图片宽度占满容器 */
  max-width: 300px; /* 限制最大宽度为300px */
  height: auto; /* 保持比例 */
  border-radius: 8px; /* 可选的，添加圆角效果 */
}

.album-info {
  flex: 2; /* 内容区域占据2倍的宽度 */
  padding-right: 80px; /* 右侧留空白，避免文字紧贴右边 */
  padding-left: 45px;
}

.album-info p {
  margin-bottom: 10px;
}

.album-info h2 {
  margin-bottom: 15px;
}

.album-image {
  flex: 1;
  margin-right: 30px; /* 给图片和内容之间留更多空间 */
  margin-left: 35px; /* 在图片左边添加空白，避免紧贴左边 */
}

.album-image img {
  width: 100%; /* 图片宽度占满容器 */
  max-width: 500px; /* 增加图片的最大宽度，确保图片较大 */
  height: auto; /* 保持比例 */
  border-radius: 8px;
  margin-top: 28px; /* 控制图片和标题之间的空白 */
}

.album-song-list {
  list-style-type: none; /* 去掉默认的列表标记 */
  padding-left: 0;
  line-height: 1.6;
}

.album-song-list li {
  word-wrap: break-word; /* 允许文本换行 */
  white-space: normal; /* 确保文本换行 */
  margin-bottom: 10px; /* 每首歌之间的间距 */
}

.purchase-button {
  position: absolute;
  top: 65px;
  right: 60px;
  z-index: 1000;
}

.purchase-button button {
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 8px;
  background-color: #333333;
  color: white;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.purchase-button button:hover {
  background-color: #555555;
}

.dialog-footer {
  text-align: right; /* 使按钮右对齐 */
}

.dialog-footer .submit-order-btn {
  background-color: #333333;
  border-color: #333333;
}

.dialog-footer .cancel-btn {
  background-color: #b0b0b0; /* 灰色背景 */
  border-color: #b0b0b0; /* 灰色边框 */
  color: #ffffff; /* 白色文字 */
}

.dialog-footer .cancel-btn:hover {
  background-color: #999999; /* 悬停时变为更深的灰色 */
  border-color: #999999; /* 悬停时变为更深的灰色 */
}

</style>
