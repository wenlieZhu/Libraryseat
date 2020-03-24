// pages/booklist/booklist.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  mov_index: function (e) {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  mov_booklistinfo: function(e){
    //console.log(e.currentTarget.dataset.bid);
    var bid = e.currentTarget.dataset.bid;
    var sid = e.currentTarget.dataset.sid;
    var pid = e.currentTarget.dataset.pid;
    wx.navigateTo({
      url: '/pages/booklistinfo/booklistinfo?bid=' + bid + '&sid=' + sid + '&pid=' + pid,
      
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(app.globalData.guid);
    var theguid = app.globalData.guid;
    var setblist=this;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=loadByuid&uid=' + theguid,
      header: {
        'content-type': 'application/json;application/x-www-form-urlencoded;charset=utf-8',
      },
      success(res) {
       // console.log(res.data.ubjson)
        setblist.setData({
          ublist:res.data.ubjson
          
        })
      }
    })
  },

  
})