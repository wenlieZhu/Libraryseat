// pages/sysset/sysset.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    gnumber: app.globalData.gnumber,
    exit_vis: false,
    mod_vis: false,
    qie_vis: false,
    call_vis: false,
    exit_act: [
      {
        name: '切换账号',
      },
      {
        name: '退出登录'
      }
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  exit_login:function(){
    //console.log(this.data.gnumber);
    this.setData({
      exit_vis: true
    });
  },
  exit_cancel() {
    this.setData({
      exit_vis: false
    });
  },
  
  //退出登录确认框
  mod_confirm() {
    this.setData({
      mod_vis: true
    });
  },

  //切换账号确认框
  qie_confirm() {
    this.setData({
      qie_vis: true
    });
  },

  //退出登录-取消
  mod_close() {
    this.setData({
      mod_vis: false
    });
  },

  //退出登录-确定
  mod_clear(){
    var gn_clear = '未登录';
    var gu_clear = null;
    app.globalData.gnumber = gn_clear;
    app.globalData.guid = gu_clear;
    wx.switchTab({
      url: '/pages/person/person',
    })
  },

  //切换账号-取消
  qie_close() {
    this.setData({
      qie_vis: false
    });
  },

  //切换账号-确定
  qie_clear() {
    var gn_clear = '未登录';
    var gu_clear=null;
    app.globalData.gnumber = gn_clear;
    app.globalData.guid = gu_clear;
    wx.reLaunch({
      url: '/pages/login/login',
    });
  },

  //action-sheet选项
  handleClickItem1({ detail }) {
    //var exit_id=this;
    const index = detail.index + 1;
    if(index==1){
      //console.log(app.globalData.gnumber);
      console.log('切换账号');
      this.qie_confirm();
      
    } else if (index == 2){
      console.log('退出登录');
      this.mod_confirm();
      
    }
  },

  mov_miniset:function(){
    wx.openSetting({
      success(res) {
        console.log(res.authSetting)
      }
    });
  },

  mov_map:function(){
    var youmap=this;
    wx.chooseLocation({
      success:function(di){
        youmap.setData({
          diname: '当前位置：'+di.name,
          diaddress: di.address
        });
      }
    });
  },

  mov_about:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=about_us',
    })
  },

  mov_call: function () {
    this.setData({
      call_vis: false
    });
    wx.switchTab({
      url: '/pages/find/find',
    });
    
  },
  call_open: function () {
    this.setData({
      call_vis: true
    });
  },
  call_close:function(){
    this.setData({
      call_vis: false
    });
  },

  mov_beian:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=beian',
    })
  },
  
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})