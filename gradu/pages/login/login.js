// pages/login/login.js
const { $Toast } = require('../../dist/base/index');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },
  handleWarning() {
    $Toast({
      content: '该功能尚未开通',
      type: 'warning'
    });
  },

  sub_login: function (e) {
    var sub_l=this;
    var uncheck = this.data.unum_cue;
    var upcheck = this.data.upasswd_cue;
    if (uncheck == '' && upcheck==''){
      console.info('表单提交携带数据', e.detail.value);
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=login',
        data: {
          unumber: e.detail.value.unumber,
          upasswd: e.detail.value.upasswd,
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded;charset=utf-8' // 默认值
        },
        success: function (res) {
          if (res.data == "True") {
            var con = e.detail.value.unumber;
            app.globalData.gnumber = con;

            console.log(app.globalData.gnumber);
            wx.switchTab({
              url: '/pages/person/person',

            })
          } else {
            wx.showModal({
              title: 'Warning',
              content: '账号或密码错误',
              success: function (res) {
                if (res.confirm) {
                  console.log('用户点击确定')
                } else if (res.cancel) {
                  console.log('用户点击取消')
                }
              }
            })
          }
        }
      })
    } else {
      if (unum_cue!=''){
        sub_l.setData({
          unum_cue: '*学号不能为空'
        });
      }
      if (upasswd_cue!=''){
        sub_l.setData({
          upasswd_cue: '*密码不能为空'
        });
      }
      
    }

    
    
  },

  mov_register: function(e){
    wx.redirectTo({
      url: '/pages/register/register',
    })
  },

  mov_index: function (e) {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  unum_full:function(un){
    var unum_this=this;
    var unum_v = un.detail.detail.value;
    if (unum_v && unum_v!=''){
      unum_this.setData({
        unum_cue:''
      });
    }else{
      unum_this.setData({
        unum_cue: '*学号不能为空'
      });
    }
  },

  upasswd_full: function (up) {
    var upasswd_this = this;
    var upasswd_v = up.detail.detail.value;
    if (upasswd_v && upasswd_v != '') {
      upasswd_this.setData({
        upasswd_cue: ''
      });
    } else {
      upasswd_this.setData({
        upasswd_cue: '*密码不能为空'
      });
    }
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  
})