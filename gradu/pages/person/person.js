// pages/person/person.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    gnumber: app.globalData.gnumber,
  },
  onPullDownRefresh: function () {

    this.onLoad();
  },
  mov_login:function(){
    wx.reLaunch({
      url: '/pages/login/login',
    })
  },
  mov_register:function(){
    wx.reLaunch({
      url: '/pages/register/register',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.stopPullDownRefresh();
    var umsg = this;
    var con = app.globalData.gnumber;
    console.log(this.data.ulist);
    console.log('跳转后登录态', app.globalData.gnumber);
    if (app.globalData.gnumber=='未登录'){
      umsg.setData({
        ulist:''
      });
    }
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?'
          + 'opr=loadByUpdate&unumber=' + con,
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          if (res.data){
            var puid = res.data.ujson[0].uid;
            app.globalData.guid = puid;
            //console.log(res.data.ujson[0].uid)
            umsg.setData({
              ulist: res.data.ujson,
              //res代表success函数的事件对，data是固定的，list是数组
            });
          }
          else{
            console.log('用户未登录');
          }
          
        },


      });
    
    
  }

  
  
})