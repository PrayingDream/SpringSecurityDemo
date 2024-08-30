<template>
  <div class="body">
    <div class="main-box">
      <!-- 注册 -->
      <div :class="['container', 'container-register', { 'is-txl': isLogin }]">
        <form>
          <h2 class="add__title">注册</h2>
          <div class="form__icons">
            <img class="form__icon" src="@/assets/image/wechat.png" alt="微信登录">
            <img class="form__icon" src="@/assets/image/alipay.png" alt="支付宝登录">
            <img class="form__icon" src="@/assets/image/QQ.png" alt="QQ登录">
            <img class="form__icon" src="@/assets/image/github.png" alt="github登录">
          </div>
          <span class="text">或使用邮箱进行注册</span>
          <el-form ref="add_form_ref" :rules="add_form_rules" :model="add_form">
            <el-form-item  prop="username">
              <el-input class="form__input" type="text" v-model="add_form.username" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item  prop="email">
              <el-input class="form__input" type="text" v-model="add_form.email" placeholder="请输入邮箱"/>
            </el-form-item>
            <el-form-item  prop="phoneNumber">
              <el-input class="form__input" type="text" v-model="add_form.phoneNumber" placeholder="请输入手机号"/>
            </el-form-item>
            <el-form-item  prop="password">
              <el-input class="form__input" type="password" v-model="add_form.password" placeholder="请输入密码"/>
            </el-form-item>
            <el-form-item  prop="confirmPassword">
              <el-input class="form__input" type="password" v-model="add_form.confirmPassword" placeholder="请确认密码"/>
            </el-form-item>
          </el-form>
          <div class="add__form__button">立即注册</div>
        </form>
      </div>
      <!-- 登录 -->
      <div :class="['container', 'container-login', { 'is-txl is-z200': isLogin }]">
        <form>
          <h2 class="title">登录</h2>
          <div class="form__icons">
            <img class="form__icon" src="@/assets/image/wechat.png" alt="微信登录">
            <img class="form__icon" src="@/assets/image/alipay.png" alt="支付宝登录">
            <img class="form__icon" src="@/assets/image/QQ.png" alt="QQ登录">
            <img class="form__icon" src="@/assets/image/github.png" alt="github登录">
          </div>
          <span class="text">或使用用户名登录</span>
          <el-form ref="login_form_ref" :rules="input_rules" :model="login_form">
            <el-form-item prop="username">
              <el-input class="form__input" type="text" v-model="login_form.username" placeholder="用户名/邮箱/手机号"/>
            </el-form-item>
            <el-form-item prop="password">
              <el-input class="form__input" type="password" v-model="login_form.password" placeholder="请输入密码"/>
            </el-form-item>
          </el-form>

          <div class="form__button" @click="Login">立即登录</div>
        </form>
      </div>
      <!-- 切换滑块 -->
      <div :class="['switch', { 'login': isLogin }]">
        <div class="switch__circle"></div>
        <div class="switch__circle switch__circle_top"></div>
        <div class="switch__container">
          <h2>{{ isLogin ? '您好 !' : '欢迎回来 !' }}</h2>
          <p>
            {{
              isLogin
                  ? '如果您还没有账号，请点击下方立即注册按钮进行账号注册'
                  : '如果您已经注册过账号，请点击下方立即登录按钮进行登录'
            }}
          </p>
          <div class="form__button" @click="isLogin = !isLogin">
            {{ isLogin ? '立即注册' : '立即登录' }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {ElNotification} from "element-plus";

const port = 'http://localhost:3000';
let isLogin = ref(true);
// 登录表单
let login_form = reactive(
    {
      username:"",
      password:"",
    }
)
let add_form = reactive(
    {
      username:"",
      password:"",
      confirmPassword:"",
      phoneNumber:"",
      email:"",
    }
)
let login_form_ref = ref()
let add_form_ref = ref()
let token = ref("")
const router = useRouter();

let confirm_validate = (rule, value, callback) => {
  if (value !== add_form.password) {
    console.log(value,add_form.password);
    callback(new Error('两次输入密码不一致!'))
  } else {/**/
    callback()
  }
}

const input_rules={
  username:[
    {
      required: true, message: '请输入用户名', trigger: 'blur'
    }
  ],
  password:[
    {
      required: true, message: '请输入密码', trigger: 'blur'
    }
  ]
}

const add_form_rules={
  username:[
    {
      required: true, message: '请输入用户名', trigger: 'blur'
    }
  ],
  password:[
    {
      required: true, message: '请输入密码', trigger: 'blur'
    }
  ],
  confirmPassword:[
    {
      required:true, message: '请输再次输入密码' ,trigger:'blur'
    },
    {
      validator:confirm_validate,
      trigger:'blur'
    }
  ],
  phoneNumber:[
    {
      required: true, message: '请输入手机号', trigger: 'blur'
    }
  ],
  email:[
    {
      required: true, message: '请输入邮箱', trigger: 'blur'
    }
  ]
}

function Login(){
  login_form_ref.value.validate((valid) => {
    if (valid) {
      axios.post(port + '/user/login', login_form,{
        headers: {
          'Content-Type': 'application/json',
          'x-requested-with': 'XMLHttpRequest',
          'token': token,
        }
      }).then(function (response) {
        console.log(response);
        if (response.data.code === 200) {
          token.value = response.data.data.token;
          sessionStorage.setItem('token',token.value);
          console.log(login_form)
          console.log(token.value)
          router.push({path:"/all"});
        } else {
          ElNotification({
            title: '账号或密码错误',
            message: response.data.message,
            type: 'error',
            duration: 2000
          })
        }
      })
    }
  })

}

</script>

<style lang="scss" scoped>
:deep(.el-input__wrapper){
  background-color:rgba(0,0,0,0);
}
.body {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Montserrat", sans-serif;
  font-size: 12px;
  background-image: url("@/assets/image/background.jpg");
  color: #a0a5a8;

}
.main-box {
  position: relative;
  width: 950px;
  min-width: 950px;
  min-height: 550px;
  height: 550px;
  padding: 25px;
  background-color: #ecf0f3;
  box-shadow: 1px 1px 100px 10PX #ecf0f3;
  border-radius: 12px;
  overflow: hidden;

  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    width: 550px;
    height: 550px;
    padding: 25px;
    background-color: #ecf0f3;
    transition: all 1.25s;

    form {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      width: 100%;
      height: 100%;
      color: #a0a5a8;

      .form__icon {
        object-fit: contain;
        width: 30px;
        margin: 0 5px;
        opacity: .5;
        transition: .15s;

        &:hover {
          opacity: 1;
          transition: .15s;
          cursor: pointer;

        }
      }

      .title {
        font-size: 34px;
        font-weight: 700;
        line-height: 3;
        color: #181818;
      }

      .add__title {
        font-size: 34px;
        font-weight: 700;
        line-height: 1;
        color: #181818;
      }

      .text {
        margin-top: 30px;
        margin-bottom: 12px;
      }

      .form__input {
        width: 350px;
        height: 40px;
        margin: 4px 0;
        padding-left: 0;
        font-size: 13px;
        letter-spacing: 0.15px;
        border: none;
        outline: none;
        // font-family: 'Montserrat', sans-serif;
        background-color: #ecf0f3;
        transition: 0.25s ease;
        border-radius: 8px;
        box-shadow: inset 2px 2px 4px #d1d9e6, inset -2px -2px 4px #f9f9f9;

        &::placeholder {
          color: #a0a5a8;
        }
      }
    }
  }

  .container-register {
    z-index: 100;
    left: calc(100% - 550px);
  }

  .container-login {
    left: calc(100% - 600px);
    z-index: 0;
  }

  .is-txl {
    left: 0;
    transition: 1.25s;
    transform-origin: right;
  }

  .is-z200 {
    z-index: 200;
    transition: 1.25s;
  }

  .switch {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    left: 0;
    height: 550px;
    width: 450px;
    padding: 25px;
    z-index: 250;
    transition: 1.25s;
    background-color: #ecf0f3;
    overflow: hidden;
    box-shadow: 4px 4px 10px #d1d9e6, -4px -4px 10px #f9f9f9;
    color: #a0a5a8;

    .switch__circle {
      position: absolute;
      width: 500px;
      height: 500px;
      border-radius: 50%;
      background-color: #ecf0f3;
      box-shadow: inset 8px 8px 12px #d1d9e6, inset -8px -8px 12px #f9f9f9;
      bottom: -60%;
      left: -60%;
      transition: 1.25s;
    }

    .switch__circle_top {
      top: -30%;
      left: 60%;
      width: 300px;
      height: 300px;
    }

    .switch__container {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      position: absolute;
      width: 400px;
      padding: 50px 55px;
      transition: 1.25s;

      h2 {
        font-size: 34px;
        font-weight: 700;
        line-height: 3;
        color: #181818;
      }

      p {
        font-size: 14px;
        letter-spacing: 0.25px;
        text-align: center;
        line-height: 1.6;
      }
    }
  }

  .login {
    left: calc(100% - 450px);

    .switch__circle {
      left: 0;
    }
  }

  .form__button {
    width: 180px;
    height: 50px;
    border-radius: 25px;
    margin-top: 50px;
    text-align: center;
    line-height: 50px;
    font-size: 14px;
    letter-spacing: 2px;
    background-color: #4b70e2;
    color: #f9f9f9;
    cursor: pointer;
    box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;

    &:hover {
      box-shadow: 2px 2px 3px 0 rgba(255, 255, 255, 50%),
      -2px -2px 3px 0 rgba(116, 125, 136, 50%),
      inset -2px -2px 3px 0 rgba(255, 255, 255, 20%),
      inset 2px 2px 3px 0 rgba(0, 0, 0, 30%);
    }
  }

  .add__form__button {
    width: 180px;
    height: 50px;
    border-radius: 25px;
    margin-top: 0;
    text-align: center;
    line-height: 50px;
    font-size: 14px;
    letter-spacing: 2px;
    background-color: #4b70e2;
    color: #f9f9f9;
    cursor: pointer;
    box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;

    &:hover {
      box-shadow: 2px 2px 3px 0 rgba(255, 255, 255, 50%),
      -2px -2px 3px 0 rgba(116, 125, 136, 50%),
      inset -2px -2px 3px 0 rgba(255, 255, 255, 20%),
      inset 2px 2px 3px 0 rgba(0, 0, 0, 30%);
    }
  }
}
</style>

