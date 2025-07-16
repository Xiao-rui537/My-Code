<template>
  <div class="home">
    <!-- 导航栏 -->
    <header class="navbar">
    <div class="logo">AF Shop</div>
    <nav class="nav-links">
      <a href="#" @click.prevent="setTab('hot')">Hot</a>
      <a href="#" @click.prevent="setTab('category')">Category</a>
      <a href="#" @click.prevent="setTab('album')">Album</a>
      <a href="#" @click.prevent="setTab('search')">Search</a>
      <a href="#" @click.prevent="setTab('my')">My</a>
    </nav>
    <div class="navbar-actions">
      <div class="search-icon">
        <i class="fas fa-search"></i>
      </div>
      <div class="sign-out">
        <a href="#" @click="signOut" style="color: #fff; text-decoration: none; font-size: 16px;">Sign Out</a>
      </div>
    </div>
  </header>

    <!-- 只在 Hot 部分显示图片，其他部分不显示 -->
    <section :class="['hero', { visible: currentTab === 'hot' }]">
      <div class="hero-content">
        <h1>THE TORTURED POETS DEPARTMENT</h1>
        <a href="#" @click.prevent="goToAlbumDetails(2)">Learn More</a>
      </div>
    </section>

    <section class="content">
      <!-- Hot 部分 -->
      <div v-if="currentTab === 'hot'" class="features">
        <div class="feature-item">
          <h2>Taylor Swift</h2>
          <p>The eleventh studio album released on April 19, 2024</p>
        </div>
        <div class="feature-item">
          <h2>The Anthology</h2>
          <p>Produced with Jack Antonoff and Aaron Dessner</p>
        </div>
      </div>

      <!-- Category 部分 -->
      <template>
      <div v-if="currentTab === 'category'" class="category-content">
        <div class="category-header">
          <h2 style="font-size: 48px; font-weight: bold; text-align: center; font-family: 'Playfair Display', serif;">CATEGORY</h2>
          <hr class="category-divider" style=" margin-top: 10px; margin-bottom: 35px; border-color: rgba(128, 128, 128, 0.5);">
        </div>
        <div class="category-container" :class="{'moved': hasSearched}">
          <el-row :gutter="40" justify="center" style="margin-bottom: 60px;">
            <el-col :span="8" v-for="(item, index) in paginatedCategories" :key="index" :style="{ marginBottom: '30px' }">
              <el-card :body-style="{ padding: '0px' }" class="category-card">
                <div class="image-wrapper" style="position: relative;">
                  <img :src="item.cat_image" class="image" alt="Card Image">
                  <!-- 右下角的按钮 -->
                  <el-button
                      class="round-button"
                      size="mini"
                      type="primary"
                      style="position: absolute; right: 10px; bottom: 10px; z-index: 10;"
                  @click.stop="goToSearch(item.cat_id)"
                  >
                  <i class="el-icon-view"></i>
                  </el-button>
                </div>
                <div style="padding: 20px;">
                  <span style="font-size: 18px; font-weight: bold;">{{ item.cat_name }}</span>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <!-- 分页组件 -->
          <el-pagination
              v-if="categories.length > pageSize"
              :current-page="currentPage"
              :page-size="pageSize"
              :total="categories.length"
              @current-change="handlePageChange"
              layout="prev, pager, next, jumper"
              style="text-align: center; margin-top: 30px;">
          </el-pagination>
        </div>
      </div>
    </template>

      <!-- Album 部分 -->
      <template>
        <div v-if="currentTab === 'album'" class="album-content">
          <div class="album-header">
            <h2 style="font-size: 48px; font-weight: bold; text-align: center; font-family: 'Playfair Display', serif;">ALBUM</h2>
            <hr class="album-divider" style="margin-top: 10px; margin-bottom: 35px; border-color: rgba(128, 128, 128, 0.5);">
          </div>

          <div class="album-container">
            <el-row :gutter="40" justify="center" style="margin-bottom: 40px;">
              <el-col :span="8" v-for="(item, index) in paginatedAlbums" :key="index" :style="{ marginBottom: '30px' }">
                <el-card :body-style="{ padding: '0px' }" class="album-card" style="cursor: pointer;">
                  <div class="image-wrapper">
                    <img :src="item.album_cover" class="image" alt="Album Cover">
                    <!-- 圆形按钮 -->
                    <el-button
                        class="round-button"
                        size="mini"
                        type="primary"
                        @click.stop="goToAlbumDetails(item.album_id)"
                    >
                      <i class="el-icon-view"></i> <!-- 使用 el-icon-view 图标 -->
                    </el-button>
                  </div>
                  <div style="padding: 20px;">
                    <span style="font-size: 20px; font-weight: bold;">{{ item.album_name }}</span>
                    <div class="bottom clearfix" style="display: flex; justify-content: space-between; align-items: flex-end; margin-top: 20px;">
                      <!-- Left side: Artist and Release Date -->
                      <div style="display: flex; flex-direction: column; align-items: flex-start;">
                        <span style="font-size: 18px; color: #999;">{{ item.album_singer }}</span>
                        <span style="font-size: 14px; color: #999;">{{ item.album_release_day }}</span>
                      </div>
                      <!-- Right side: Value -->
                      <div style="display: flex; align-items: flex-end;">
                        <span style="font-size: 18px; font-weight: bold;">Value: {{ item.album_sale }}</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>

            <!-- 分页组件 -->
            <el-pagination
                v-if="allAlbums.length > pageSize"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="allAlbums.length"
                @current-change="handlePageChange"
                layout="prev, pager, next, jumper"
                style="text-align: center; margin-top: 30px;">
            </el-pagination>
          </div>
        </div>
      </template>

      <!-- Search 部分 -->
      <template>
        <div v-if="currentTab === 'search'" class="search-content">
          <div class="search-container" :class="{'moved': hasSearched}">
            <!-- 添加一个父容器来居中搜索框 -->
            <div class="search-wrapper" style="display: flex; justify-content: center; margin-top: 30px; margin-bottom: 30px;">
              <el-input
                  v-model="searchQuery"
                  placeholder="Search by album"
                  suffix-icon="el-icon-search"
                  clearable
                  @input="handleSearch"
                  :style="{ width: '400px' }">
              </el-input>
            </div>

            <div v-if="!searchQuery && !hasSearched" class="search-placeholder" style="font-size: 32px;">
              Enter Something To Search
            </div>

            <el-row :gutter="40" justify="center" style="margin-bottom: 40px;">
              <el-col :span="8" v-for="(item, index) in paginatedSearchResults" :key="index" :style="{ marginBottom: '30px' }">
                <el-card :body-style="{ padding: '0px' }" class="search-card" style="cursor: pointer;">
                <div class="image-wrapper">
                    <img :src="item.album_cover" class="image" alt="Card Image">
                  <el-button
                      class="round-button"
                      size="mini"
                      type="primary"
                      @click.stop="goToAlbumDetails(item.album_id)"
                      style="position: absolute; bottom: 10px; right: 10px;">
                    <i class="el-icon-view"></i> <!-- 使用 el-icon-view 图标 -->
                  </el-button>
                  </div>
                  <div style="padding: 20px;">
                    <span style="font-size: 20px; font-weight: bold;">{{ item.album_name }}</span>
                    <div class="bottom clearfix" style="display: flex; justify-content: space-between; align-items: flex-end;">
                      <div style="display: flex; flex-direction: column; align-items: flex-start;">
                        <span style="font-size: 18px; color: #999;">{{ item.album_singer }}</span>
                        <span style="font-size: 14px; color: #999;">{{ item.album_release_day }}</span>
                      </div>
                      <div style="display: flex; align-items: flex-end;">
                        <span style="font-size: 18px; font-weight: bold;">Value: {{ item.album_sale }}</span>
                      </div>
                      <div class="card-button-wrapper" style="position: absolute; bottom: 20px; right: 20px;">
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>

            <el-pagination
                v-if="searchResults.length > pageSize"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="searchResults.length"
                @current-change="handlePageChange"
                layout="prev, pager, next, jumper"
                style="text-align: center; margin-top: 30px;">
            </el-pagination>
          </div>
        </div>
      </template>


      <!-- My 部分 -->
      <template>
        <div v-if="currentTab === 'my'" class="profile-container">
          <!-- 布局：左侧显示基本信息，右侧显示已购专辑 -->
          <el-row :gutter="20">
            <!-- 左侧卡片：基本信息 -->
            <el-col :span="12">
              <el-card class="profile-card">
                <el-descriptions class="margin-top" title="个人信息" :column="1" border>
                  <template #extra>
                    <el-button type="text" style="color: #333;" @click="openEditDialog">
                      修改
                    </el-button>
                  </template>

                  <!-- 账号部分 -->
                  <el-descriptions-item label="账号">
                    {{ userInfo.user_account }}
                  </el-descriptions-item>

                  <!-- 密码部分 -->
                  <el-descriptions-item label="密码">
                    已隐藏({{ maskedPassword }})
                  </el-descriptions-item>


                  <!-- 用户名部分 -->
                  <el-descriptions-item label="用户名">
                    {{ userInfo.user_name }}
                  </el-descriptions-item>

                  <!-- 性别部分 -->
                  <el-descriptions-item label="性别">
                    {{ userInfo.user_gender }}
                  </el-descriptions-item>

                  <!-- 出生日期部分 -->
                  <el-descriptions-item label="出生日期">
                    {{ userInfo.user_birth }}
                  </el-descriptions-item>

                  <!-- 住址部分 -->
                  <el-descriptions-item label="住址">
                    {{ userInfo.user_hometown }}
                  </el-descriptions-item>

                  <!-- 电话部分 -->
                  <el-descriptions-item label="电话">
                    {{ userInfo.user_phone }}
                  </el-descriptions-item>

                </el-descriptions>
              </el-card>
            </el-col>

            <!-- 右侧卡片：已购专辑 -->
            <el-col :span="12">
              <el-card class="profile-card">
                <el-table :data="albums" style="width: 100%">
                  <!-- 专辑封面 -->
                  <el-table-column label="专辑封面" prop="album_cover" width="80">
                    <template #default="{ row }">
                      <img :src="row.album_cover" alt="专辑封面" class="album-cover" @click="goToAlbumDetails(row.album_id)"/>
                    </template>
                  </el-table-column>

                  <!-- 专辑名称 -->
                  <el-table-column label="专辑名称" prop="album_name"></el-table-column>

                  <!-- 歌手 -->
                  <el-table-column label="歌手" prop="album_singer"></el-table-column>

                  <el-table-column label="语种" prop="album_language"></el-table-column>

                  <!-- 价格 -->
                  <el-table-column label="价格" prop="album_sale"></el-table-column>
                </el-table>
              </el-card>
            </el-col>
          </el-row>

          <!-- 编辑个人信息的弹出框 -->
          <el-dialog
              :visible="dialogVisible"
              width="50%"
              role="dialog"
              aria-labelledby="edit-user-info-dialog-title"
              @close="handleClose"
          >
            <template #title>
              <span style="font-weight: bold; font-size: 22px; text-align: center; display: block;">修改个人信息</span>
            </template>

            <el-form :model="editUserInfo" ref="form" label-width="120px">

              <!-- 用户名 -->
              <el-form-item label="用户名">
                <el-input
                    v-model="editUserInfo.user_name"
                    placeholder="请输入用户名"
                    aria-describedby="user-name-helper">
                </el-input>
              </el-form-item>

              <!-- 密码 -->
              <el-form-item label="密码">
                <el-input
                    v-model="editUserInfo.user_password"
                    placeholder="请输入密码"
                    type="password"
                    aria-describedby="password-helper">
                </el-input>
              </el-form-item>

              <!-- 性别 -->
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="editUserInfo.user_gender">
                  <el-radio label="男" style="margin-right: 20px;">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>

              <!-- 出生日期 -->
              <el-form-item label="出生日期">
                <el-date-picker
                    v-model="editUserInfo.user_birth"
                    type="date"
                    placeholder="选择出生日期"
                    aria-describedby="birth-date-helper">
                </el-date-picker>
              </el-form-item>

              <!-- 住址 -->
              <el-form-item label="住址">
                <el-input
                    v-model="editUserInfo.user_hometown"
                    placeholder="请输入住址"
                    aria-describedby="hometown-helper">
                </el-input>
              </el-form-item>

              <!-- 电话 -->
              <el-form-item label="电话">
                <el-input
                    v-model="editUserInfo.user_phone"
                    placeholder="请输入电话"
                    aria-describedby="phone-helper">
                </el-input>
              </el-form-item>

            </el-form>

            <!-- Dialog Footer -->
            <template #footer>
              <!-- 取消按钮 -->
              <el-button
                  @click="dialogVisible = false"
                  type="primary"
                  round
                  size="medium"
                  style="background-color: #f0f0f0; color: #333; border: 1px solid #f0f0f0;">
                取消
              </el-button>

              <!-- 保存按钮 -->
              <el-button
                  type="primary"
                  @click="submitEdit"
                  round
                  size="medium"
                  style="background-color: #f0f0f0; color: #333; border: 1px solid #f0f0f0;">
                保存
              </el-button>
            </template>
          </el-dialog>
        </div>
      </template>
    </section>
  </div>
</template>

<script>
import axios from 'axios'; // 导入 axios 用于发送 HTTP 请求
import { debounce } from "lodash"; // 从 lodash 导入 debounce 函数，用于防抖处理

export default {
  data() {
    return {
      // 当前选中的标签，初始值为 'hot'
      currentTab: 'hot', // 默认显示 'Hot' 的内容

      // 分类列表，初始化为空数组
      categories: [],

      // 搜索历史或建议列表，初始化为空数组
      searches: [],

      // 用户已购买的专辑列表，初始化为空数组
      albums: [],

      // 当前页码，初始化为第一页
      currentPage: 1,  // 当前页

      // 每页显示的项目数量，初始化为 6
      pageSize: 6,     // 每页显示的卡片数量

      // 搜索框的查询字符串，初始化为空
      searchQuery: '',

      // 搜索结果存放的数组，初始化为空
      searchResults: [],  // 搜索结果存放的数组

      // 标记是否执行过搜索，初始化为 false
      hasSearched: false,  // 标记是否执行过搜索

      // 控制对话框显示的布尔值，初始化为 false
      dialogVisible: false,

      // 日期选择器的值，初始化为当前日期
      value: new Date(),

      // 用户信息，初始化为空数组（可能应为对象，根据后端数据）
      userInfo: [],

      // 所有专辑数据，初始化为空数组
      allAlbums: [],

      // 用于编辑的用户信息对象，初始化为空字符串
      editUserInfo: { // 用于编辑的用户信息
        user_account: '',
        user_password: '',
        user_name: '',
        user_gender: '',
        user_birth: '',
        user_hometown: '',
        user_phone: ''
      },
    };
  },

  // 组件创建时调用的生命周期钩子
  created() {
    this.fetchAllAlbums();    // 获取所有专辑数据
    this.fetchUserInfo();     // 获取当前用户的信息
    this.fetchAlbums();       // 获取用户已购买的专辑
    this.fetchCategories();   // 获取分类列表
  },

  // 监听器，用于监听数据属性的变化
  watch: {
    // 监听 currentTab 的变化
    currentTab(newTab) {
      // 如果切换到 'category' 或 'singer' 标签，重置页码
      if (newTab === 'category' || newTab === 'singer') {
        this.resetPage();
      }
    }
  },

  // 计算属性，用于根据当前页码和页面大小计算要显示的数据
  computed: {
    // 计算当前页显示的分类
    paginatedCategories() {
      const start = (this.currentPage - 1) * this.pageSize; // 起始索引
      const end = start + this.pageSize; // 结束索引
      return this.categories.slice(start, end); // 返回当前页的分类
    },

    // 计算当前页显示的所有专辑
    paginatedAlbums() {
      const start = (this.currentPage - 1) * this.pageSize; // 起始索引
      const end = start + this.pageSize; // 结束索引
      return this.allAlbums.slice(start, end); // 返回当前页的专辑
    },

    // 计算当前页显示的搜索结果
    paginatedSearchResults() {
      const start = (this.currentPage - 1) * this.pageSize; // 起始索引
      const end = this.currentPage * this.pageSize; // 结束索引
      return this.searchResults.slice(start, end); // 返回当前页的搜索结果
    },

    // 返回用户密码的掩码形式，用星号代替每个字符
    maskedPassword() {
      return this.userInfo.user_password.replace(/./g, '*'); // 用 * 替换每个字符
    }
  },

  methods: {
    /**
     * 用户登出方法
     * 将用户重定向到登录页面
     */
    signOut() {
      this.$router.push('/login'); // 跳转到 login 页面
    },

    /**
     * 设置当前选中的标签
     * @param {String} tab - 要设置的标签名
     */
    setTab(tab) {
      this.currentTab = tab; // 更新 currentTab 的值
    },

    /**
     * 处理页码变化的方法
     * @param {Number} page - 新的页码
     */
    handlePageChange(page) {
      this.currentPage = page; // 更新当前页码
    },

    /**
     * 重置当前页码到第一页
     */
    resetPage() {
      this.currentPage = 1; // 每次选择分类或专辑时，重置页码到第一页
    },

    /**
     * 关闭对话框的方法
     */
    handleClose() {
      this.dialogVisible = false; // 手动关闭对话框
    },

    /**
     * 打开编辑用户信息的对话框
     * 并初始化编辑表单的数据
     */
    openEditDialog() {
      // 深拷贝 userInfo 到 editUserInfo，避免直接修改原数据
      this.editUserInfo = JSON.parse(JSON.stringify(this.userInfo)); // 使用 JSON 方法进行深拷贝
      this.dialogVisible = true; // 显示对话框
    },

    /**
     * 提交编辑后的用户信息
     * 发送 PUT 请求更新用户信息
     */
    submitEdit() {
      // 发送 PUT 请求到更新用户信息的接口
      axios.put('http://localhost:8080/update', this.editUserInfo) // 修改为 PUT 请求
          .then(response => {
            if (response.data.success) { // 如果后端响应成功
              this.userInfo = { ...this.editUserInfo }; // 更新 userInfo 为编辑后的信息
              this.dialogVisible = false; // 关闭对话框
              this.$message.success('信息更新成功！'); // 显示成功消息
            } else { // 如果后端响应失败
              this.$message.error('更新失败，请稍后再试'); // 显示错误消息
            }
          })
          .catch(error => { // 捕获请求错误
            this.$message.error('发生错误，请稍后再试'); // 显示错误消息
            console.error(error); // 在控制台输出错误信息
          });
    },

    /**
     * 获取分类列表的方法
     * 发送 GET 请求到分类列表接口
     */
    fetchCategories() {
      axios.get('http://localhost:8080/category/list')  // 假设后端接口是 '/category/list'
          .then(response => {
            this.categories = response.data;  // 假设后端返回的数据是分类数组
          })
          .catch(error => {
            console.error('Error:', error); // 在控制台输出错误信息
          });
    },

    /**
     * 获取当前用户信息的方法
     * 发送 GET 请求到用户信息接口
     */
    async fetchUserInfo() {
      try {
        const userId = localStorage.getItem('userId'); // 从 localStorage 获取用户 ID
        const response = await axios.get(`http://localhost:8080/info${userId}`); // 发送请求
        this.userInfo = response.data; // 更新 userInfo 为获取到的数据
      } catch (error) {
        console.error('Failed to fetch user info:', error); // 在控制台输出错误信息
      }
    },

    /**
     * 根据分类 ID 进行搜索的方法
     * @param {Number} catId - 分类的 ID
     */
    goToSearch(catId) {
      this.currentTab = 'search';  // 切换到 'search' 选项卡
      this.searchQuery = `*${catId}`;  // 设置搜索框为 *catId 格式，表示按分类 ID 搜索
      this.debouncedSearch();  // 调用防抖后的搜索方法
    },

    /**
     * 获取用户已购买的专辑列表
     * 发送 GET 请求到已购专辑接口
     */
    async fetchAlbums() {
      try {
        const userId = localStorage.getItem('userId'); // 从 localStorage 获取用户 ID

        // 确保 userId 存在且有效
        if (!userId) {
          console.error('User ID is not found.'); // 输出错误信息
          return; // 如果没有找到 userId，终止请求
        }

        // 发送 GET 请求获取已购专辑数据
        const response = await axios.get(`http://localhost:8080/albums/buy${userId}`);

        // 更新已购专辑数据
        this.albums = response.data;
      } catch (error) {
        console.error('Failed to fetch albums:', error); // 在控制台输出错误信息
      }
    },

    /**
     * 跳转到指定专辑的详情页
     * @param {Number} albumId - 专辑的 ID
     */
    goToAlbumDetails(albumId) {
      // 使用路径参数传递 albumId，跳转到 'details' 路由
      this.$router.push({ name: 'details', params: { albumId: albumId } });
    },

    /**
     * 获取所有专辑数据的方法
     * 发送 GET 请求到所有专辑的列表接口
     */
    async fetchAllAlbums() {
      try {
        const response = await axios.get('http://localhost:8080/albums/list'); // 修改为你的后端 API 地址
        this.allAlbums = response.data; // 获取的所有专辑数据存入 allAlbums

        // 可选：根据需要返回当前页的专辑
        const start = (this.currentPage - 1) * this.pageSize; // 起始索引
        const end = start + this.pageSize; // 结束索引
        return this.allAlbums.slice(start, end); // 返回当前页的专辑
      } catch (error) {
        console.error('Error fetching albums:', error); // 在控制台输出错误信息
      }
    },

    /**
     * 防抖函数，用于处理搜索输入
     * 延迟 500ms 后执行搜索，避免频繁请求
     */
    debouncedSearch: debounce(function() {
      this.currentPage = 1;  // 每次搜索时重置当前页为第一页
      this.hasSearched = !!this.searchQuery; // 标记是否执行过搜索，根据 searchQuery 是否为空

      const params = {}; // 定义请求参数对象

      // 检查 searchQuery 是否以 '*' 开头，后面跟着数字（例如 '*1'）
      const categoryMatch = this.searchQuery.match(/^\*(\d+)$/);

      // 如果匹配到 *<数字>，表示按分类 ID 搜索
      if (categoryMatch) {
        params.categoryId = categoryMatch[1];  // 使用匹配到的数字作为分类 ID
        this.searchQuery = '';  // 如果是分类查询，清空关键词搜索

        // 发送 GET 请求到分类查询接口，传递分类 ID
        axios.get(`http://localhost:8080/category/category/${params.categoryId}`)
            .then(response => {
              this.searchResults = response.data || []; // 更新搜索结果，如果无数据则设为空数组
            })
            .catch(error => {
              console.error('分类查询请求失败:', error); // 在控制台输出错误信息
              this.searchResults = []; // 请求失败时清空搜索结果
            });
      } else {
        // 如果是普通的关键词搜索
        if (this.searchQuery) {
          params.keyword = this.searchQuery;  // 按关键词搜索

          // 发送 GET 请求到关键词搜索接口，传递搜索关键词
          axios.get('http://localhost:8080/albums/search', { params })
              .then(response => {
                this.searchResults = response.data || []; // 更新搜索结果，如果无数据则设为空数组
              })
              .catch(error => {
                console.error('搜索请求失败:', error); // 在控制台输出错误信息
                this.searchResults = []; // 请求失败时清空搜索结果
              });
        } else {
          // 如果没有输入关键词，清空搜索结果
          this.searchResults = [];
        }
      }
    }, 500),  // 500ms 的防抖延时

    /**
     * 处理搜索输入的方法
     * 调用防抖后的搜索函数
     */
    handleSearch() {
      this.debouncedSearch(); // 调用防抖后的搜索函数
    }
  }
};
</script>

<style scoped>
/* 通用样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  background-color: #f1f1f1;
}

/* 导航栏 */
.navbar {
  display: flex;
  justify-content: space-between; /* 两端对齐，logo在左，导航和搜索图标在右 */
  align-items: center;
  background-color: #000;
  color: #fff;
  padding: 15px 30px;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000;
}

.navbar .logo {
  font-size: 36px;
  font-weight: bold;
}

.navbar .nav-links {
  display: flex;
  gap: 30px;
  justify-content: center;
  flex-grow: 1;
}

.navbar .nav-links a {
  color: #fff;
  text-decoration: none;
  font-size: 19px;
}

.navbar .nav-links a:hover {
  text-decoration: underline;
}

.navbar .search-icon {
  font-size: 20px;
}

/* 主图区域 */
.hero {
  margin-top: 80px; /* 导航栏的高度 */
  height: 80vh;
  background: url('/Users/xiaorui/Documents/albumshop_front/src/assets/img/TS1.jpg') no-repeat center center;
  background-size: cover;
  position: relative;
  color: #fff;
  text-align: center;
  display: none; /* 默认不显示 */
}

.hero.visible {
  display: block; /* 只有当 .visible 被添加时才显示 */
}

.hero .hero-content {
  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translateX(-50%);
}

.hero h1 {
  font-size: 40px;
  font-weight: bold;
  margin-bottom: 30px;
}

.hero a {
  margin-top: 20px;
  padding: 12px 24px;
  background-color: #333333;
  color: #E5E5E5;
  text-decoration: none;
  border-radius: 5px;
}

.hero a:hover {
  background-color: #333;
}
/* 特性区域 */
 .features {
   display: flex;
   justify-content: center;
   gap: 40px;
   padding: 50px 0;
   background-color: #fff;
 }

.feature-item {
  width: 250px;
  text-align: center;
}

.feature-item h2 {
  font-size: 24px;
  font-weight: bold;
}

.feature-item p {
  font-size: 16px;
  color: #777;
  margin-top: 10px;
}

/* 分类区域 */
.category-content, .album-content {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.category-container, .album-container {
  margin-bottom: 20px;
  transition: transform 0.3s ease;
}

.category-container.moved, .album-container.moved {
  transform: translateY(10px);
}

/* 搜索框容器 */
.search-placeholder {
  text-align: center;
  font-size: 32px;
  color: #888;
  padding: 40px;
  font-style: italic;
}

/* 卡片样式 */
.category-card, .album-card {
  width: 100%;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  background-color: #fff;
  transition: transform 0.3s ease;
}

.category-card:hover, .album-card:hover {
  transform: scale(1.03);
}

/* 圆形按钮样式 */
.round-button {
  position: absolute;
  right: 10px; /* 距离右侧 10px */
  bottom: 10px; /* 距离底部 10px */
  width: 40px; /* 按钮宽度 */
  height: 40px; /* 按钮高度 */
  padding: 0; /* 去除内边距 */
  border-radius: 50%; /* 圆形 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(128, 128, 128, 0.5); /* 设置半透明灰色背景 */
  border: none; /* 去除边框 */
  color: white; /* 按钮内文字颜色为白色 */
}

.round-button .el-icon-view {
  font-size: 20px; /* 调整图标大小 */
  color: white;    /* 设置图标颜色 */
}

/* 图片容器 */
.image-wrapper {
  position: relative;
  padding-top: 100%;
  overflow: hidden;
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片比例，裁剪多余部分 */
}

.image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片的 1:1 比例，并确保填充容器 */
  border-bottom: 2px solid #f0f0f0;
}

/* 卡片内容 */
.category-card .bottom, .singer-card .bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.category-card .bottom span, .singer-card .bottom span {
  font-size: 14px;
  color: #999;
}

.category-card .bottom , .singer-card .bottom  {
  color: #409EFF;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .category-content, .album-content {
    padding: 10px;
  }

  .el-col :nth-child(3) {
    max-width: 90%;
  }
}

.category-content, .album-content {
  padding-top: 80px;  /* 增加顶部内边距，从而拉开与菜单的距离 */
}

/* 搜索区域 */
/* 容器样式 */
.search-content {
  padding: 80px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

/* 搜索框容器 */
.search-container {
  margin-bottom: 20px; /* 给搜索框和卡片之间增加间隙 */
  transition: transform 0.6s ease;
}

.search-container.moved {
  transform: translateY(40px);
}

.search-card {
  position: relative;  /* This allows us to position the button inside the card */
}

.round-button {
  position: absolute;
  bottom: 10px;
  right: 10px;
  z-index: 10;  /* Ensures the button is above the card content */
}

/* 卡片样式 */
.search-card {
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  background-color: #fff;
  transition: transform 0.3s ease;
}

.search-card:hover {
  transform: scale(1.03);
}

/* 图片容器 */
.image-wrapper {
  position: relative;
  padding-top: 100%;
  overflow: hidden;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-bottom: 2px solid #f0f0f0;
}

/* 卡片内容 */
.search-card .bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.search-card .bottom span {
  font-size: 14px;
  color: #999;
}

.search-card .bottom {
  color: #409EFF;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .search-content {
    padding: 10px;
  }

  .el-col :nth-child(3) {
    max-width: 90%;
  }
}

/* 我的区域 */
 .profile-container {
   padding: 20px;
 }

.profile-card {
  margin-bottom: 20px;
  margin-top: 80px;
}

.album-cover {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.profile-card {
  margin-bottom: 12px; /* 每一项之间的间距 */
  white-space: normal;  /* 确保内容在长文本时会换行 */
  word-wrap: break-word; /* 自动断词，避免溢出 */
}

.el-dialog {
  width: 60%; /* 增加对话框的宽度 */
}

.el-form {
  margin-top: 20px; /* 增加表单的顶部间距 */
}

.el-form-item {
  margin-bottom: 20px; /* 增加每一项之间的间距 */
}

/* 调整输入框的宽度 */
.el-input,
.el-date-picker {
  width: 100%; /* 输入框和日期选择框宽度占满父容器 */
}

</style>