// pages/article/article.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      arid: options.arid,
    })
    console.log(options.arid);
  },

  platform_agreement:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=platform',
    })
  },

  hide_agreement: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=hide',
    })
  },
  
})