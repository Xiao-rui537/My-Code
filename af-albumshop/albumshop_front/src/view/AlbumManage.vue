<template>
  <div id="app">
    <link href="https://fonts.googleapis.com/css2?family=Zilla+Slab:wght@700&display=swap" rel="stylesheet">
    <!-- 主布局：Header + Sidebar + Main -->
    <el-container style="height: 100vh; background-color: #E5E4E2;">
      <!-- 顶部标题 -->
      <el-header style="background-color: #E5E4E2; color: #E5E4E2; padding: 20px 50px; display: flex; justify-content: space-between; align-items: center;
                  border-bottom: 1px solid #E5E4E2; position: fixed; top: 0; left: 0; right: 0; z-index: 100;">
        <h2 style="margin: 0; font-weight: 700; color: #7a77b9; font-size: 180%; font-family: 'Zilla Slab', serif;">
          AF Shop Management System
        </h2>
      </el-header>
      <!-- 分割线 -->
      <hr style="border: none; border-top: 2px solid #7a77b9; width: calc(100% - 20px); margin: 60px 0 10px 20px; position: fixed;">
      <!-- 左侧菜单 -->
      <el-aside width="240px" style="background-color: #E5E4E2; padding: 10px; height: 100vh; position: fixed; top: 70px; border-right: 2px solid #7a77b9;">
        <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" @select="handleMenuSelect" style="background-color: #E5E4E2;">
          <el-menu-item index="1" class="menu-item" style="font-size: 16px; font-weight: 500; display: flex; align-items: center; margin-bottom: 10px;">
            <i class="el-icon-shopping-bag-1" style="font-size: 18px; margin-right: 8px;"></i>
            专辑管理
          </el-menu-item>
          <el-menu-item index="2" class="menu-item" style="font-size: 16px; font-weight: 500; display: flex; align-items: center; margin-bottom: 10px;">
            <i class="el-icon-user" style="font-size: 18px; margin-right: 8px;"></i>
            用户管理
          </el-menu-item>
          <el-menu-item index="3" class="menu-item" style="font-size: 16px; font-weight: 500; display: flex; align-items: center; margin-bottom: 10px;">
            <i class="el-icon-data-analysis" style="font-size: 18px; margin-right: 8px;"></i>
            订单管理
          </el-menu-item>
          <el-menu-item index="4" class="menu-item" style="font-size: 16px; font-weight: 500; display: flex; align-items: center;">
            <i class="el-icon-setting" style="font-size: 18px; margin-right: 8px;"></i>
            选项
          </el-menu-item>
        </el-menu>
      </el-aside>

            <!-- 主要内容区域 -->
            <el-main style="margin-left: 240px; padding: 20px 40px; background-color: #E5E4E2; overflow: auto; min-height: 100vh; margin-top: 60px;">
              <!-- 控制显示的内容 -->
              <div v-if="activeMenu === '1'" class="transition-fade">
                <!-- 搜索框和按钮 -->
                <el-row type="flex" align="middle" style="margin-bottom: 20px;">
                  <!-- 左侧按钮：添加专辑 -->
                  <el-col :span="3">
                    <el-button
                        type="primary" round
                        @click="addAlbum()"
                        class="transition-button"
                        style="font-size: 14px; padding: 12px 24px; background-color: #F2C76E; border-color: #F2C76E; display: flex; align-items: center; border-radius: 30px;">
                      <i class="el-icon-circle-plus-outline" style="margin-right: 8px;"></i>
                      添加专辑
                    </el-button>
                  </el-col>

                  <!-- 右侧搜索框和搜索按钮，放在同一个el-col里 -->
                    <el-col :span="24" style="display: flex; justify-content: flex-end; align-items: center; flex-wrap: nowrap;">
                      <span style="margin-right: 20px;">搜索选择</span>
                    <!-- 开关控制搜索条件 -->
                    <el-switch v-model="searchByAlbumName" active-text="专辑名"  active-color="#7a77b9" active-text-color="#F2C76E" style="margin-right: 30px;" />
                    <el-switch v-model="searchByAlbumTranslation" active-text="专辑译名"  active-color="#7a77b9" active-text-color="#F2C76E" style="margin-right: 30px;" />
                    <el-switch v-model="searchBySinger" active-text="歌手"  active-color="#7a77b9" active-text-color="#F2C76E" style="margin-right: 30px;" />
                    <el-switch v-model="searchByLanguage" active-text="语言"  active-color="#7a77b9" active-text-color="#F2C76E" style="margin-right: 30px;" />
                      <el-col :span="10" style="display: flex; align-items: center;">
                    <el-input
                        v-model="searchQuery"
                        placeholder="输入专辑名称搜索"
                        prefix-icon="el-icon-aim"
                        @clear="searchAlbums"
                        style="border-radius: 30px; border: 1px solid #E5E4E2; flex-grow: 1;" />
                    <el-button
                        type="primary" round
                        @click="searchAlbums"
                        class="transition-button"
                        style="font-size: 14px; padding: 12px 24px; background-color: #EA7186; border-color: #EA7186; margin-left: 10px; border-radius: 30px;">
                      <i class="el-icon-search" style="margin-right: 8px;"></i>
                      搜索
                    </el-button>
                  </el-col></el-col>
                </el-row>

                <!-- 专辑表格 -->
                <el-table :data="filteredAlbums"
                          style="width: 100%; margin-top: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); border-radius: 8px; table-layout: fixed;"
                          class="transition-fade">

                  <!-- 专辑ID -->
                  <el-table-column prop="album_id" label="专辑ID" width="70" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 专辑封面 -->
                  <el-table-column label="专辑封面" prop="album_cover" width="80" header-align="center" align="center">
                    <template #default="{ row }">
                      <img :src="row.album_cover" alt="专辑封面" class="album-cover" @click="goToAlbumDetails(row.album_id)"/>
                    </template>
                  </el-table-column>

                  <!-- 专辑名称 -->
                  <el-table-column prop="album_name" label="专辑名称" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 专辑译名 -->
                  <el-table-column prop="album_name_tran" label="专辑译名" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 专辑分类 -->
                  <el-table-column prop="album_title" label="黑胶样式" :cell-style="cellStyle" header-align="center" align="center" ></el-table-column>

                  <!-- 发行日期 -->
                  <el-table-column prop="album_release_day" label="发行日期" :cell-style="cellStyle" header-align="center" align="center" ></el-table-column>

                  <!-- 价格 -->
                  <el-table-column prop="album_price" label="价格" :cell-style="cellStyle" header-align="center" align="center" ></el-table-column>

                  <!-- 打折价格 -->
                  <el-table-column prop="album_sale" label="打折价格" :cell-style="cellStyle" header-align="center" align="center" ></el-table-column>

                  <!-- 歌手 -->
                  <el-table-column prop="album_singer" label="歌手" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 歌曲数量 -->
                  <el-table-column prop="album_song" label="歌曲数量" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 语言 -->
                  <el-table-column prop="album_language" label="语言" :cell-style="cellStyle" header-align="center" align="center"></el-table-column>

                  <!-- 操作 -->
                  <el-table-column label="操作" width="150" :cell-style="cellStyle" header-align="center" align="center">
                    <template v-slot="scope">
                      <div class="action-buttons">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="editAlbum(scope.row)"
                            class="transition-button"
                            style="background-color: #F2C76E; border-color: #F2C76E;">
                        </el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle
                            @click="deleteAlbum(scope.row)"
                            class="transition-button"
                            style="background-color: #EA7186; border-color: #EA7186;">
                        </el-button>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>

              <!-- 用户管理部分 -->
              <div v-else-if="activeMenu === '2'" class="transition-fade">
                <!-- 搜索框和按钮 -->
                <el-row type="flex" align="middle" style="margin-bottom: 20px;">
                  <!-- 左侧按钮：添加用户 -->
                  <el-col :span="3">
                    <el-button
                        type="primary" round
                        @click="addUser()"
                        class="transition-button"
                        style="font-size: 14px; padding: 12px 24px; background-color: #F2C76E; border-color: #F2C76E; display: flex; align-items: center; border-radius: 30px;">
                      <i class="el-icon-circle-plus-outline" style="margin-right: 8px;"></i>
                      添加用户
                    </el-button>
                  </el-col>

                  <!-- 右侧搜索框和搜索按钮 -->
                  <el-col :span="24" style="display: flex; justify-content: flex-end; align-items: center; flex-wrap: nowrap;">
                    <el-col :span="10" style="display: flex; align-items: center;">
                      <el-input
                          v-model="searchQuery"
                          placeholder="请输入用户名、账号或手机号搜索"
                          prefix-icon="el-icon-aim"
                          @clear="searchUsers"
                          style="border-radius: 30px; border: 1px solid #E5E4E2; flex-grow: 1;" />
                      <el-button
                          type="primary" round
                          @click="searchUsers"
                          class="transition-button"
                          style="font-size: 14px; padding: 12px 24px; background-color: #EA7186; border-color: #EA7186; margin-left: 10px; border-radius: 30px;">
                        <i class="el-icon-search" style="margin-right: 8px;"></i>
                        搜索
                      </el-button>
                    </el-col>
                  </el-col>
                </el-row>

                <!-- 用户列表 -->
                <div v-if="activeMenu === '2'" class="transition-fade">
                  <div class="user-list">
                    <!-- 使用 v-for 动态渲染每个用户 -->
                    <div v-for="(user) in filteredUsers" :key="user.user_id" class="user-card">
                      <div class="user-info">
                        <h3 style="margin-top: -1px;">用户名: {{ user.user_name }}</h3>
                        <p><strong>账号:</strong>{{ user.user_account }}</p>
                        <p><strong>密码:</strong>{{ user.user_password }}</p>
                        <p><strong>用户ID:</strong> {{ user.user_id }}</p>
                        <p><strong>性别:</strong> {{ user.user_gender }}</p>
                        <p><strong>出生日期:</strong> {{ user.user_birth }}</p>
                        <p><strong>家乡:</strong> {{ user.user_hometown }}</p>
                        <p><strong>手机号:</strong> {{ user.user_phone }}</p>
                        <p><strong>权限:</strong> {{ user.user_isadmin ? '管理员' : '普通用户' }}</p>
                      </div>
                      <div class="user-actions">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="editUser(user)"
                            class="transition-button"
                            style="background-color: #F2C76E; border-color: #F2C76E;">
                        </el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle
                            @click="deleteUser(user)"
                            class="transition-button"
                            style="background-color: #EA7186; border-color: #EA7186;">
                        </el-button>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
        <div v-else-if="activeMenu === '3'" class="transition-fade">
          <el-row type="flex" align="middle" style="margin-bottom: 20px;">
            <!-- 左侧按钮：添加订单 -->
            <el-col :span="3">
              <el-button
                  type="primary"
                  round
                  @click="openOrderDialog()"
                  class="transition-button"
                  style="font-size: 14px; padding: 12px 24px; background-color: #F2C76E; border-color: #F2C76E; display: flex; align-items: center; border-radius: 30px;">
                <i class="el-icon-circle-plus-outline" style="margin-right: 8px;"></i>
                添加订单
              </el-button>
            </el-col>

            <!-- 右侧搜索框和搜索按钮，放在同一个el-col里 -->
            <el-col :span="24" style="display: flex; justify-content: flex-end; align-items: center; flex-wrap: nowrap;">
              <!-- 搜索框 -->
              <el-col :span="10" style="display: flex; align-items: center;">
                <el-input
                    v-model="searchQuery"
                    placeholder="请输入收货人、电话、账号或付款方式搜索"
                    prefix-icon="el-icon-aim"
                    @clear="searchOrders"
                    style="border-radius: 30px; border: 1px solid #E5E4E2; flex-grow: 1;" />
                <el-button
                    type="primary" round
                    @click="searchOrders"
                    class="transition-button"
                    style="font-size: 14px; padding: 12px 24px; background-color: #EA7186; border-color: #EA7186; margin-left: 10px; border-radius: 30px;">
                  <i class="el-icon-search" style="margin-right: 8px;"></i>
                  搜索
                </el-button>
              </el-col>
            </el-col>
          </el-row>
          <div class="order-list">
            <!-- 遍历订单数据，使用卡片显示每个订单 -->
            <el-row gutter="20">
              <el-col :span="8" v-for="(order) in filteredOrders" :key="order.order_id" style="margin-bottom: 20px;">
                <el-card class="order-card" :body-style="{ padding: '20px' }">
                  <div class="order-content">
                    <h3 style="margin-top: -1px;">订单ID: {{ order.order_id }}</h3>
                    <p><strong>收货人:</strong> {{ order.order_receiver }}</p>
                    <p><strong>地址:</strong> {{ order.order_address }}</p>
                    <p><strong>联系电话:</strong> {{ order.order_phone }}</p>
                    <p><strong>订购账号:</strong> {{ order.order_user_account }}</p>
                    <p><strong>购买专辑:</strong>
                      {{order.albumName}}
                    </p>
                    <p><strong>价格:</strong> ¥{{ order.order_price }}</p>
                    <p><strong>付款方式:</strong> {{ order.order_pay_type }}</p>

                    <div class="order-actions" style="display: flex; justify-content: space-between; align-items: center;">
                      <el-button
                          type="primary"
                          icon="el-icon-edit"
                          circle
                          @click="editOrder(order)"
                          class="transition-button"
                          style="background-color: #F2C76E; border-color: #F2C76E;">
                      </el-button>
                      <el-button
                          type="danger"
                          icon="el-icon-delete"
                          circle
                          @click="deleteOrder(order)"
                          class="transition-button"
                          style="background-color: #EA7186; border-color: #EA7186;">
                      </el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </div>
        <div v-else-if="activeMenu === '4'" class="transition-fade" style="display: flex; justify-content: center; align-items: center; flex-direction: column; height: 100vh;">
          <div style="background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); text-align: center; width: 100%; max-width: 700px;">
            <p style="font-size: 28px; font-weight: bold; color: #333; margin-bottom: 20px;">你好管理员，每天都要有好心情哦^_^!</p>
              <el-calendar v-model="value" style="margin-bottom: 20px;"></el-calendar>
                  <div style="display: flex; justify-content: space-between; width: 100%; max-width: 700px; padding: 20px 0;">
                    <button
                        @click="goToHome"
                        class="transition-button"
                        style="
                        padding: 12px 50px;
                        font-size: 16px;
                        background-color: #F2C76E;
                        border: 2px solid #F2C76E;
                        color: white;
                        border-radius: 4px;
                        cursor: pointer;
                        margin-right: 15px; /* 给第一个按钮加间隔 */
                      "
                    >
                      点我跳转到商城
                    </button>

                    <button
                        @click="goToLogin"
                        class="transition-button"
                        style="
                        padding: 12px 50px;
                        font-size: 16px;
                        background-color: #7a77b9;
                        border: 2px solid #7a77b9;
                        color: white;
                        border-radius: 4px;
                        cursor: pointer;
                      "
                    >
                      点我退出下班
                    </button>
                  </div>
                </div>
              </div>

        <!-- 添加/编辑专辑的对话框 -->
        <el-dialog
            :visible="dialogVisible"
            title="专辑"
            @close="dialogVisible = false"
            style="border-radius: 8px;"
            class="transition-fade">
          <el-form :model="currentAlbum" ref="albumForm" label-width="120px">
            <!-- 图片上传组件 -->
            <el-form-item label="专辑封面" :label-width="formLabelWidth" style="margin-bottom: 24px;">
              <el-upload
                  class="upload-demo"
                  :action="''"
              :file-list="fileList"
              list-type="picture-card"
              :before-upload="beforeUpload"
              :on-change="handleFileChange"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :auto-upload="false"
              :key="uploadKey"
              >
              <el-button size="small" type="primary" round style="background-color: #F2C76E; border-color: #F2C76E;">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="专辑ID" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_id" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="专辑名称" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="专辑译名" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_name_tran" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="黑胶样式" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_title" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="专辑类别ID" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_category_id" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="发行日期" :label-width="formLabelWidth">
              <el-date-picker v-model="currentAlbum.album_release_day" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="价格" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_price" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="打折价格" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_sale" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="歌手" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_singer" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="专辑介绍" :label-width="formLabelWidth">
              <el-input type="textarea" v-model="currentAlbum.album_introduction" autocomplete="off" rows="4"></el-input>
            </el-form-item>
            <el-form-item label="歌曲数量" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_song" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="歌曲列表" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_song_name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="语言" :label-width="formLabelWidth">
              <el-input v-model="currentAlbum.album_language" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div style="display: flex; justify-content: flex-end; margin-top: 40px;">
            <el-button type="primary" round @click="dialogVisible = false" style="background-color: #EA7186; border-color: #EA7186; color: #ffffff; margin-left: 20px;">取消</el-button>
            <el-button type="primary" round @click="submitAlbumForm" style="background-color: #F2C76E; border-color: #F2C76E; color: #ffffff;">保存</el-button>
          </div>
        </el-dialog>

              <!-- 添加用户表单 -->
              <el-dialog
                  :visible="addUserDialogVisible"
                  title="用户"
                  @close="resetForm"
                  style="border-radius: 8px;">
                <el-form :model="newUser" ref="userForm" label-width="120px">
                  <!-- 用户名 -->
                  <el-form-item label="用户名" :label-width="formLabelWidth" required>
                    <el-input v-model="newUser.user_name" autocomplete="off"></el-input>
                  </el-form-item>

                  <!-- 用户账号 -->
                  <el-form-item label="用户账号" :label-width="formLabelWidth" required>
                    <el-input v-model="newUser.user_account" autocomplete="off"></el-input>
                  </el-form-item>

                  <!-- 密码 -->
                  <el-form-item label="密码" :label-width="formLabelWidth" required>
                    <el-input v-model="newUser.user_password" autocomplete="off"></el-input>
                  </el-form-item>

                  <!-- 用户性别 -->
                  <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="newUser.user_gender" placeholder="请选择性别">
                      <el-option label="男" value="男"></el-option>
                      <el-option label="女" value="女"></el-option>
                    </el-select>
                  </el-form-item>

                  <!-- 用户手机号 -->
                  <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-input v-model="newUser.user_phone" autocomplete="off"></el-input>
                  </el-form-item>

                  <!-- 出生日期 -->
                  <el-form-item label="出生日期" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="newUser.user_birth"
                        type="date"
                        placeholder="请选择出生日期"
                        style="width: 100%;">
                    </el-date-picker>
                  </el-form-item>

                  <!-- 家乡 -->
                  <el-form-item label="家乡" :label-width="formLabelWidth">
                    <el-input v-model="newUser.user_hometown" autocomplete="off"></el-input>
                  </el-form-item>

                  <!-- 用户角色 -->
                  <el-form-item label="权限" :label-width="formLabelWidth">
                    <el-select v-model="newUser.user_isadmin" placeholder="请选择权限">
                      <el-option label="管理员" :value="true"></el-option>
                      <el-option label="普通用户" :value="false"></el-option>
                    </el-select>
                  </el-form-item>

                  <div style="text-align: right; margin-top: 20px;">
                    <el-button type="primary" round @click="addUserDialogVisible = false" style="background-color: #EA7186; border-color: #EA7186; color: #fff;">取消</el-button>
                    <el-button type="primary" round @click="submitUpdatedUser" style="background-color: #F2C76E; border-color: #F2C76E; color: #fff;">保存</el-button>
                  </div>
                </el-form>
              </el-dialog>
              <!-- 编辑订单弹出框 -->
              <el-dialog
                  title="订单"
                  :visible="OrderDialogVisible"
                  width="50%"
                  @close="resetForm">

                <el-form :model="orderForm" ref="formData">
                  <el-form-item label="收货人" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_receiver"></el-input>
                  </el-form-item>

                  <el-form-item label="地址" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_address"></el-input>
                  </el-form-item>

                  <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_phone"></el-input>
                  </el-form-item>

                  <el-form-item label="订购账号" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_user_account"></el-input>
                  </el-form-item>

                  <el-form-item label="购买专辑" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_album_id"></el-input>
                  </el-form-item>

                  <el-form-item label="价格" :label-width="formLabelWidth">
                    <el-input v-model="orderForm.order_price"></el-input>
                  </el-form-item>

                  <el-form-item label="付款方式" :label-width="formLabelWidth">
                    <el-select v-model="orderForm.order_pay_type" placeholder="请选择付款方式">
                      <el-option label="银联储蓄卡" value="银联储蓄卡"></el-option>
                      <el-option label="微信" value="微信"></el-option>
                      <el-option label="支付宝" value="支付宝"></el-option>
                      <el-option label="数字人民币" value="数字人民币"></el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
                <div style="text-align: right; margin-top: 20px;">
                  <el-button
                      type="primary"
                      round
                      @click="OrderDialogVisible = false"
                      style="background-color: #EA7186; border-color: #EA7186; color: #fff;">
                    取消
                  </el-button>
                  <el-button
                      type="primary"
                      round
                      @click="saveOrder"
                      style="background-color: #F2C76E; border-color: #F2C76E; color: #fff;">
                    保存
                  </el-button>
                </div>
              </el-dialog>
            </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios'; // 导入 axios，用于发送 HTTP 请求

export default {
  data() {
    return {
      // 专辑列表，存储所有从后端获取的专辑
      albums: [],

      // 过滤后的专辑列表，根据搜索或筛选条件更新
      filteredAlbums: [],

      // 搜索查询字符串，用户在搜索框中输入的内容
      searchQuery: '',

      // 控制添加或编辑专辑对话框的可见性
      dialogVisible: false,

      // 控制订单对话框的可见性
      OrderDialogVisible: false,

      // 上传文件列表，用于处理专辑封面图片
      fileList: [],

      // 当前选中的专辑，用于编辑或查看详细信息
      currentAlbum: {},

      // 表单标签的宽度，用于样式设置
      formLabelWidth: '120px',

      // 当前激活的菜单项，默认为 '1'
      activeMenu: '1', // 默认选中菜单项

      // 搜索选项控制变量，决定搜索的类别
      searchByAlbumName: true, // 控制是否搜索专辑名
      searchByAlbumTranslation: false, // 控制是否搜索专辑译名
      searchBySinger: false, // 控制是否搜索歌手
      searchByLanguage: false, // 控制是否搜索语言

      // 总记录数，用于分页
      totalRecords: 0,

      // 当前页显示的专辑数据，支持分页
      paginatedAlbums: [],  // 当前页的专辑数据

      // 用于强制刷新上传组件，每次上传后更新该值
      uploadKey: Date.now(), // 用于强制刷新组件

      // 过滤后的用户数据，根据搜索或筛选条件更新
      filteredUsers: [], // 过滤后的用户数据

      // 所有的用户数据，从后端获取
      users: [],         // 所有的用户数据

      // 表格单元格的样式设置
      cellStyle: { padding: '10px' },

      // 单元格的内边距，用于布局
      padding: '30px', // 每个单元格的内边距

      // 单元格内容的文本对齐方式
      textAlign: 'center', // 单元格内容居中

      // 控制添加用户弹框的可见性
      addUserDialogVisible: false,  // 控制添加用户弹框显示

      // 搜索用户的选项控制变量
      searchByUserAccount: true,
      searchByPhone: false,
      searchByPayType: false,
      searchByReceiver: false,

      // 过滤后的订单数据，根据搜索或筛选条件更新
      filteredOrders: [],

      // 标识是否需要重新上传图片
      isImageUploaded: false, // 标识是否需要重新上传图片

      // 当前日期值，可能用于日期选择器
      value: new Date(),

      // 新用户表单数据，用于添加或编辑用户
      newUser: {
        user_name: '',
        user_account: '',
        user_password: '',
        user_gender: '',
        user_birth: '',
        user_phone: '',
        user_isadmin: '', // 默认普通用户
      },

      // 订单表单数据，用于添加或编辑订单
      orderForm: {
        order_receiver: '',
        order_address: '',
        order_phone: '',
        order_user_account: '',
        order_album_id: '',
        order_price: '',
        order_pay_type: '',
      },
    };
  },

  watch: {
    // 监听 currentAlbum 的变化，每次修改当前专辑时触发
    currentAlbum: {
      handler(newValue) {
        if (newValue.album_cover) {
          // 如果当前专辑有封面图片，加载到 fileList 中
          this.fileList = [
            {
              name: '专辑封面',
              url: newValue.album_cover, // 设定封面图片 URL
            },
          ];
        }
      },
      deep: true, // 深度监听，确保监听对象内部属性的变化
    },
  },

  methods: {
    /**
     * 跳转到主页的方法
     * 使用 Vue Router 的 $router.push 方法导航到 'home' 路由
     */
    goToHome() {
      this.$router.push({ name: 'home' });
    },

    /**
     * 跳转到登录页面的方法
     * 使用 Vue Router 的 $router.push 方法导航到 '/login' 路由
     */
    goToLogin() {
      this.$router.push('/login');
    },

    /**
     * 获取专辑列表的方法
     * 发送 GET 请求到后端专辑列表接口，更新 albums 和 filteredAlbums
     */
    fetchAlbums() {
      // 假设后端API是 /api/albums，返回一个专辑列表
      axios.get('http://localhost:8080/albums/list')  // 调用后端API
          .then(response => {
            // 请求成功后，将数据存储到 albums 中
            this.albums = response.data;
            // 初始化时，filteredAlbums 同步为 albums 的数据
            this.filteredAlbums = [...this.albums]; // 确保 initial filteredAlbums 同步
          })
          .catch(error => {
            console.error('获取专辑列表失败:', error);
            // 可以处理错误（例如，弹出提示）
            this.$message.error('获取专辑列表失败，请稍后再试。');
          });
    },

    /**
     * 搜索专辑的方法
     * 根据用户的搜索条件和选项发送搜索请求，更新 filteredAlbums
     */
    async searchAlbums() {
      if (this.searchQuery) { // 如果有搜索查询
        try {
          // 动态构建查询条件
          const searchCriteria = {
            query: this.searchQuery  // 将 searchQuery 作为查询条件传递给后端
          };

          // 根据开关状态，选择性地添加字段
          if (this.searchByAlbumName) {
            searchCriteria.searchByAlbumName = true;
          }
          if (this.searchByAlbumTranslation) {
            searchCriteria.searchByAlbumTranslation = true;
          }
          if (this.searchBySinger) {
            searchCriteria.searchBySinger = true;
          }
          if (this.searchByLanguage) {
            searchCriteria.searchByLanguage = true;
          }

          // 发送 POST 请求到后端，查询条件作为请求体传递
          const response = await axios.post(`http://localhost:8080/albums/searchAlbum`, searchCriteria);

          // 更新 filteredAlbums 为后端返回的数据
          this.filteredAlbums = response.data;

        } catch (error) {
          console.error('搜索专辑时发生错误:', error);
          this.$message.error('搜索专辑时发生错误，请稍后再试。');
          this.filteredAlbums = []; // 如果出错，清空过滤结果
        }
      } else {
        // 如果没有输入查询，返回所有专辑（可以选择发送一个无条件地请求，或者在前端缓存）
        this.filteredAlbums = [...this.albums];
      }
    },

    /**
     * 处理菜单选择的方法
     * 更新 activeMenu，以反映当前选中的菜单项
     * @param {String} index - 选中的菜单项的索引
     */
    handleMenuSelect(index) {
      this.activeMenu = index;
    },

    /**
     * 打开添加专辑弹窗的方法
     * 重置当前状态，并显示对话框以添加新专辑
     */
    addAlbum() {
      this.resetState(); // 清空状态
      this.dialogType = "create"; // 设置对话框类型为创建
      this.currentAlbum = {}; // 确保当前专辑数据为空
      this.dialogVisible = true; // 显示对话框
    },

    /**
     * 打开编辑专辑弹窗的方法
     * 填充当前专辑数据，并显示对话框以编辑专辑
     * @param {Object} album - 要编辑的专辑对象
     */
    editAlbum(album) {
      this.resetState(); // 清空状态
      this.dialogType = "edit"; // 设置对话框类型为编辑
      this.currentAlbum = { ...album }; // 填充表单数据，使用展开运算符进行浅拷贝
      this.dialogVisible = true; // 显示对话框
    },

    /**
     * 删除专辑的方法
     * 调用 saveAlbum 方法，传入 'delete' 操作类型和要删除的专辑
     * @param {Object} album - 要删除的专辑对象
     */
    deleteAlbum(album) {
      // 调用保存函数并传入 'delete' 和要删除的专辑
      this.saveAlbum('delete', album);
    },

    /**
     * 提交专辑表单的方法
     * 验证表单后，根据对话框类型调用 saveAlbum 方法
     */
    submitAlbumForm() {
      // 使用 Vue 的表单验证机制，通过 $refs 获取表单引用并验证
      this.$refs.albumForm.validate(async (valid) => {
        if (valid) { // 如果表单验证通过
          const action = this.dialogType === "create" ? "create" : "edit"; // 确定操作类型
          await this.saveAlbum(action, this.currentAlbum); // 调用保存专辑方法
          this.dialogVisible = false; // 关闭弹窗
        } else {
          this.$message.error("表单验证失败，请检查输入！"); // 显示错误消息
        }
      });
    },

    /**
     * 处理文件选择的方法
     * 更新 fileList 并标记需要上传图片
     * @param {File} file - 选中的文件
     * @param {Array} fileList - 当前的文件列表
     */
    handleFileChange(file, fileList) {
      this.fileList = fileList; // 更新文件列表
      this.isImageUploaded = false; // 标记需要上传
    },

    /**
     * 手动上传图片的方法
     * 将选中的图片上传到后端，并返回图片的 URL
     * @returns {String|null} - 上传成功时返回图片 URL，否则返回 null
     */
    async uploadImage() {
      // 如果没有新图片，直接返回 null
      if (this.fileList.length === 0 || !this.fileList[0]?.raw) {
        return null; // 跳过上传
      }

      const formData = new FormData();
      formData.append("file", this.fileList[0].raw); // 将文件添加到 FormData

      try {
        // 根据当前专辑是否存在 album_id，决定上传的 URL
        const url = this.currentAlbum.album_id
            ? `http://localhost:8080/images/${this.currentAlbum.album_id}`
            : "http://localhost:8080/images/";

        // 发送 POST 请求上传图片
        const response = await axios.post(url, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        if (response.status === 200 && response.data.url) {
          this.isImageUploaded = true; // 标记图片已上传
          this.$message.success("图片上传成功！");
          return response.data.url; // 返回图片的 URL
        } else {
          this.$message.error("图片上传失败！");
          return null;
        }
      } catch (error) {
        console.error("图片上传失败：", error);
        this.$message.error("图片上传失败，请检查网络连接！");
        return null;
      }
    },

    /**
     * 保存专辑的方法
     * 根据操作类型（create, edit, delete）执行不同的逻辑
     * @param {String} actionType - 操作类型，'create' | 'edit' | 'delete'
     * @param {Object} [album=null] - 要操作的专辑对象，仅在删除时使用
     */
    async saveAlbum(actionType, album = null) {
      try {
        if (actionType === "create") { // 创建专辑
          // 发送 POST 请求创建新专辑
          const response = await axios.post("http://localhost:8080/add", this.currentAlbum);
          if (response.status === 201) { // HTTP 201 Created
            this.currentAlbum.album_id = response.data.album_id; // 更新当前专辑的 ID
            this.$message.success("专辑创建成功！");

            // 如果选择了新图片，则上传图片
            if (this.fileList.length > 0) {
              const imageUrl = await this.uploadImage(); // 上传图片并获取 URL
              if (imageUrl) {
                this.currentAlbum.album_cover = imageUrl; // 更新专辑封面 URL

                // 发送 PUT 请求更新专辑封面
                await axios.put(
                    `http://localhost:8080/albums/change/${this.currentAlbum.album_id}`,
                    this.currentAlbum
                );
                this.$message.success("专辑封面已更新！");
              }
            }
          }
        } else if (actionType === "edit") { // 编辑专辑
          let imageUrl = null;

          // 如果选择了新图片，则上传
          if (this.fileList.length > 0) {
            imageUrl = await this.uploadImage();
          }

          // 如果上传成功，更新封面 URL；否则保留当前封面
          if (imageUrl) {
            this.currentAlbum.album_cover = imageUrl;
          }

          // 发送 PUT 请求更新专辑信息
          const response = await axios.put(
              `http://localhost:8080/albums/change/${this.currentAlbum.album_id}`,
              this.currentAlbum
          );
          if (response.status === 200) { // HTTP 200 OK
            this.$message.success("专辑更新成功！");
          }
        } else if (actionType === "delete") { // 删除专辑
          // 确保要删除的专辑存在 album_id
          if (!album?.album_id) {
            this.$message.error("专辑 ID 不能为空，无法删除！");
            return;
          }

          // 发送 DELETE 请求删除专辑
          const response = await axios.delete(
              `http://localhost:8080/albums/delete/${album.album_id}`
          );
          if (response.status === 204) { // HTTP 204 No Content
            this.$message.success("专辑删除成功！");
          } else {
            this.$message.error("专辑删除失败！");
            return;
          }
        }

        // 刷新专辑列表，确保最新数据
        this.fetchAlbums();
        this.resetState(); // 清空状态
      } catch (error) {
        console.error("操作失败：", error);
        this.$message.error(`操作失败：${error.response?.data?.message || "请稍后再试！"}`);
      }
    },

    /**
     * 重置当前状态的方法
     * 清空文件列表、图片上传标志和当前专辑数据
     */
    resetState() {
      this.fileList = []; // 清空文件列表
      this.isImageUploaded = false; // 重置图片上传标志
      this.currentAlbum = {}; // 清空当前专辑数据
    },

    /**
     * 上传前的检查方法
     * 验证文件类型和大小，确保上传的是有效的图片
     * @param {File} file - 选中的文件
     * @returns {Boolean} - 如果通过验证，返回 true；否则返回 false，阻止上传
     */
    beforeUpload(file) {
      const isImage = file.type.startsWith("image/"); // 检查文件类型是否为图片
      if (!isImage) {
        this.$message.error("只能上传图片！");
        return false; // 阻止上传
      }
      const isLt2M = file.size / 1024 / 1024 < 2; // 检查文件大小是否小于 2MB
      if (!isLt2M) {
        this.$message.error("图片大小不能超过 2MB！");
        return false; // 阻止上传
      }
      return true; // 允许上传
    },

    /**
     * 处理图片上传成功的方法
     * 更新文件列表并显示成功消息
     * @param {Object} response - 上传响应数据
     * @param {File} file - 上传的文件
     * @param {Array} fileList - 当前的文件列表
     */
    handleUploadSuccess(response, file, fileList) {
      this.$message.success("图片上传成功！");
      this.fileList = fileList; // 更新文件列表
    },

    /**
     * 处理图片上传失败的方法
     * 显示错误消息并记录错误
     * @param {Object} err - 上传错误信息
     */
    handleUploadError(err) {
      this.$message.error("图片上传失败！");
      console.error("上传失败：", err);
    },

    /**
     * 跳转到指定专辑详情页的方法
     * 使用 Vue Router 的 $router.push 方法导航到 'details' 路由，并传递 albumId 参数
     * @param {Number} albumId - 要查看详情的专辑 ID
     */
    goToAlbumDetails(albumId) {
      // 使用路径参数传递 albumId，跳转到 'details' 路由
      this.$router.push({ name: 'details', params: { albumId: albumId } });
    },

    /**
     * 点击添加用户按钮时显示弹框的方法
     * 设置 addUserDialogVisible 为 true，显示添加用户弹框
     */
    addUser() {
      this.addUserDialogVisible = true;
    },

    /**
     * 提交添加或更新用户数据的方法
     * 根据 newUser 是否包含 user_id，决定使用 POST 还是 PUT 请求
     */
    submitUpdatedUser() {
      let url = ''; // API URL
      let method = ''; // 请求方法

      if (this.newUser.user_id) {
        // 如果有 user_id，说明是更新操作，使用 PUT 请求
        url = 'http://localhost:8080/update';
        method = 'put';
      } else {
        // 如果没有 user_id，说明是创建新用户，使用 POST 请求
        url = 'http://localhost:8080/user/add';
        method = 'post';
      }

      // 根据判断选择合适的请求方法
      axios({
        method: method,
        url: url,
        data: this.newUser
      })
          .then(response => {
            if (response.data.success) { // 如果后端响应成功
              const successMessage = this.newUser.user_id ? '用户信息更新成功' : '新用户创建成功';
              this.$message.success(successMessage); // 显示成功消息
              this.addUserDialogVisible = false; // 隐藏弹框
              this.fetchUsers();  // 更新用户列表
            } else {
              // 如果后端响应失败，显示错误消息
              this.$message.error(response.data.message || (this.newUser.user_id ? '更新用户信息失败' : '创建用户失败'));
            }
          })
          .catch(error => {
            // 捕获请求错误，显示错误消息
            const errorMessage = error.response ? error.response.data.message : (this.newUser.user_id ? '更新用户信息失败' : '创建用户失败');
            this.$message.error(errorMessage);
          });
    },

    /**
     * 获取用户列表的方法
     * 发送 GET 请求到后端用户列表接口，更新 users 和 filteredUsers
     */
    fetchUsers() {
      axios.get('http://localhost:8080/list') // 调用后端API获取用户列表
          .then(response => {
            this.users = response.data; // 保存所有用户数据
            this.filteredUsers = response.data; // 初始化时将所有数据赋值给 filteredUsers
          })
          .catch(error => {
            console.error(error);  // 打印错误信息
            // 判断错误对象的具体内容，并显示相应的错误消息
            if (error.response) {
              // 如果服务器返回了错误响应
              this.$message.error(`错误码: ${error.response.status} - ${error.response.data.message || '获取用户列表失败'}`);
            } else if (error.request) {
              // 如果请求没有得到响应
              this.$message.error('网络请求失败，未收到响应');
            } else {
              // 其他错误
              this.$message.error(`请求失败: ${error.message}`);
            }
          });
    },

    /**
     * 搜索用户的方法
     * 根据 searchQuery 过滤 users，更新 filteredUsers
     */
    searchUsers() {
      if (this.searchQuery) { // 如果有搜索查询
        this.filteredUsers = this.users.filter(user => {
          // 确保每个字段是字符串，并避免 null 或 undefined
          const userName = user.user_name || '';  // 如果是 null 或 undefined，替换为空字符串
          const userAccount = user.user_account || '';
          const userPhone = user.user_phone || '';

          // 进行包含检查，返回匹配的用户
          return userName.includes(this.searchQuery) ||
              userAccount.includes(this.searchQuery) ||
              userPhone.includes(this.searchQuery);
        });
      } else {
        this.filteredUsers = this.users;  // 如果没有输入搜索条件，显示所有用户
      }
    },

    /**
     * 编辑用户的方法
     * 填充表单数据并显示编辑用户弹框
     * @param {Object} user - 要编辑的用户对象
     */
    editUser(user) {
      // 填充表单数据，使用展开运算符进行浅拷贝
      this.newUser = { ...user };
      this.addUserDialogVisible = true; // 显示弹框
    },

    /**
     * 删除用户的方法
     * 发送 DELETE 请求到后端删除指定用户，并更新用户列表
     * @param {Object} user - 要删除的用户对象
     */
    deleteUser(user) {
      // 发送 DELETE 请求到后端删除用户
      axios.delete(`http://localhost:8080/deleteUser/${user.user_id}`)
          .then(() => {
            this.$message.success('用户删除成功'); // 显示成功消息
            this.fetchUsers();  // 更新用户列表
          })
          .catch((error) => {
            // 捕获请求错误，显示错误消息
            const errorMessage = error.response ? error.response.data.message : '删除用户失败';
            this.$message.error(errorMessage);
          });
    },

    /**
     * 重置表单的方法
     * 清空 newUser 和 orderForm 的数据，重置 currentAlbum，并隐藏弹框
     */
    resetForm() {
      // 重置 newUser 数据
      this.newUser = {
        user_name: '',
        user_account: '',
        user_password: '',
        user_gender: '',
        user_birth: '',
        user_phone: '',
        user_isadmin: '', // 默认普通用户
      };

      // 重置 orderForm 数据
      this.orderForm = {
        order_receiver: '',
        order_address: '',
        order_phone: '',
        order_user_account: '',
        order_album_id: '',
        order_price: '',
        order_pay_type: '',
      };

      // 重置 currentAlbum 数据
      this.currentAlbum = {
        album_name: '',
        album_singer: '',
        album_release_day: '',
        album_price: '',
        album_name_tran: '',
        album_title: '',
        album_category_id: '',
        album_sale: '',
        album_cover: '', // 用来保存上传的图片路径
        album_introduction: '',
        album_song: '',
        album_song_name: '',
        album_language: '',
        fileList: [], // 用来保存上传的图片文件列表
        orders: [], // 保存所有订单
      };

      // 更新 uploadKey 以强制刷新上传组件
      this.uploadKey += 1;

      // 隐藏用户和订单弹框
      this.addUserDialogVisible = false;
      this.OrderDialogVisible = false;
    },

    /**
     * 搜索订单的方法
     * 根据 searchQuery 过滤 orders，更新 filteredOrders
     */
    searchOrders() {
      if (this.searchQuery) { // 如果有搜索查询
        this.filteredOrders = this.orders.filter(order => {
          // 确保每个字段是字符串，并避免 null 或 undefined
          const userAccount = order.order_user_account || ''; // 如果是 null 或 undefined，替换为空字符串
          const phone = order.order_phone || '';
          const payType = order.order_pay_type || '';
          const receiver = order.order_receiver || '';

          // 进行包含检查，返回匹配的订单
          return userAccount.includes(this.searchQuery) ||
              phone.includes(this.searchQuery) ||
              payType.includes(this.searchQuery) ||
              receiver.includes(this.searchQuery);
        });
      } else {
        this.filteredOrders = this.orders;  // 如果没有输入搜索条件，显示所有订单
      }
    },

    /**
     * 打开添加或编辑订单弹窗的方法
     * 根据是否传入订单对象，决定是创建新订单还是编辑现有订单
     * @param {Object|null} [order=null] - 要编辑的订单对象，若为 null 则创建新订单
     */
    openOrderDialog(order = null) {
      if (order) { // 如果传入了订单对象，表示编辑
        // 编辑模式，填充表单数据，使用展开运算符进行浅拷贝
        this.orderForm = { ...order };
      } else { // 如果没有传入订单对象，表示创建新订单
        // 创建新订单，清空表单
        this.orderForm = {
          order_id: null,
          order_receiver: '',
          order_phone: '',
          order_address: '',
          order_user_account: '',
          order_album_id: '',
          order_price: '',
          order_pay_type: ''
        };
      }
      this.OrderDialogVisible = true; // 显示订单弹窗
    },

    /**
     * 保存订单的方法
     * 根据 orderForm 是否包含 order_id，决定是创建还是更新订单
     */
    saveOrder() {
      if (this.orderForm.order_id) {
        // 如果有订单 ID，调用更新订单方法
        this.updateOrder(this.orderForm);
      } else {
        // 否则调用创建订单方法
        this.createOrder(this.orderForm);
      }
    },

    /**
     * 创建订单的方法
     * 发送 POST 请求到后端创建新订单，并更新订单列表
     * @param {Object} order - 要创建的订单对象
     */
    createOrder(order) {
      axios.post('http://localhost:8080/orders/add', order)
          .then((response) => {
            if (response.status === 201) { // HTTP 201 Created
              this.fetchOrders(); // 更新订单列表
              this.OrderDialogVisible = false; // 隐藏弹窗
              this.$message.success('订单创建成功'); // 显示成功消息
            }
          })
          .catch((error) => {
            console.error('创建订单失败:', error);
            this.$message.error('创建订单失败'); // 显示错误消息
          });
    },

    /**
     * 更新订单的方法
     * 发送 PUT 请求到后端更新指定订单，并更新订单列表
     * @param {Object} order - 要更新的订单对象
     */
    updateOrder(order) {
      axios.put(`http://localhost:8080/orders/update/${order.order_id}`, order)
          .then((response) => {
            if (response.status === 200) { // HTTP 200 OK
              // 找到要更新的订单在 orders 数组中的索引
              const index = this.orders.findIndex(item => item.order_id === order.order_id);
              if (index !== -1) {
                // 使用 splice 更新该订单的数据
                this.orders.splice(index, 1, response.data);
              }

              // 更新筛选后的订单列表
              this.fetchOrders();
              this.$message.success('订单更新成功'); // 显示成功消息
              this.OrderDialogVisible = false; // 隐藏弹窗
            }
          })
          .catch((error) => {
            console.error('更新订单失败:', error);
            // 显示详细的错误消息，优先使用后端提供的消息
            this.$message.error(error.response?.data?.message || '更新订单失败');
          });
    },

    /**
     * 获取订单列表的方法
     * 发送 GET 请求到后端订单列表接口，更新 orders 和 filteredOrders
     */
    fetchOrders() {
      axios
          .get('http://localhost:8080/orders/list') // 调用后端API获取订单列表
          .then((response) => {
            this.orders = response.data; // 保存所有订单数据
            this.filteredOrders = [...this.orders]; // 初始化时将所有数据赋值给 filteredOrders

            // 获取每个订单的专辑信息
            this.orders.forEach((order) => {
              this.getAlbumName(order.order_album_id, order); // 为每个订单获取专辑名
            });
          })
          .catch((error) => {
            console.error('获取订单列表失败:', error);
            this.$message.error('获取订单列表失败'); // 显示错误消息
          });
    },

    /**
     * 编辑订单的方法
     * 打开订单弹窗，并填充订单数据
     * @param {Object} order - 要编辑的订单对象
     */
    editOrder(order) {
      this.openOrderDialog(order); // 调用打开订单弹窗的方法，传入要编辑的订单
    },

    /**
     * 删除订单的方法
     * 发送 DELETE 请求到后端删除指定订单，并更新订单列表
     * @param {Object} order - 要删除的订单对象
     */
    deleteOrder(order) {
      axios.delete(`http://localhost:8080/orders/delete/${order.order_id}`) // 发送 DELETE 请求
          .then(() => {
            this.$message.success('订单删除成功'); // 显示成功消息
            this.fetchOrders(); // 更新订单列表
          })
          .catch((error) => {
            console.error('删除订单失败:', error);
            // 显示详细的错误消息，优先使用后端提供的消息
            this.$message.error(error.response?.data?.message || '删除订单失败');
          });
    },

    /**
     * 获取专辑名的方法
     * 根据专辑 ID 获取专辑名称，并绑定到对应订单上
     * @param {Number} albumId - 专辑的 ID
     * @param {Object} order - 订单对象，将专辑名绑定到该订单
     */
    async getAlbumName(albumId, order) {
      try {
        const response = await axios.get(`http://localhost:8080/album/${albumId}`); // 发送 GET 请求获取专辑信息
        order.albumName = response.data.album_name; // 将专辑名绑定到对应订单上
      } catch (error) {
        console.error('获取专辑信息失败:', error);
        order.albumName = '未知专辑'; // 如果获取专辑失败，设置为默认值
      }
    },
  },

  mounted() {
    this.fetchAlbums(); // 初始化加载专辑数据
    this.fetchUsers(); // 初始化用户列表
    this.fetchOrders(); // 初始化订单列表
  }
};
</script>

<style scoped>


/* 添加过渡动画 */
.transition-button {
  transition: transform 0.4s ease, background-color 0.6s ease;
}

.transition-button:hover {
  transform: scale(1.05);
  background-color: #BD90EA; /* 鼠标悬浮时改变颜色 */
}

.el-menu-item:hover {
  background-color: #E5E4E2;  /* 铂色背景 */
  color: #000;  /* 字体颜色 */
}

.el-menu-item.is-active {
  background-color: #D1D1D1;  /* 激活状态背景颜色 */
  color: #7A77B9;  /* 激活状态字体颜色 */
}

.menu-item {
  font-size: 16px;
  font-weight: 500;
  display: flex;
  align-items: center;
  padding: 10px 20px;
  border-radius: 8px;
  transition: box-shadow 0.3s ease, background-color 0.3s ease;
}

.el-menu-item:hover {
  transform: scale(1.15); /* 放大整个菜单项 */
  background-color: #D1D1D1;
}

.el-menu-item i:hover {
  transform: scale(1.25); /* 放大图标 */
}

.el-table .el-table__row:hover {
  background-color: #E5E4E2;
}

.album-cover {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
  cursor: pointer;
}

.transition-fade {
  transition: opacity 0.3s ease;
  opacity: 1;
}

.transition-fade[v-if] {
  opacity: 0;
}

.user-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.user-card {
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.user-info {
  margin-bottom: 20px;
}

.user-info p {
  margin: 5px 0;
}

.user-actions {
  display: flex;
  justify-content: space-between;
}

.order-card {
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}
</style>