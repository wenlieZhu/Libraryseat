// pages/bookok/bookok.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  goindex:function(e){
    wx.reLaunch({
      url: '/pages/index/index',
    })
  },

  onPullDownRefresh: function () {
    this.onLoad();
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.stopPullDownRefresh();
    var useat = this;
    var guid = app.globalData.guid;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=oklist&uid='+guid,
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res){
        console.log(res.data.ubjson);
        // console.log("预约号",res.data.ubjson[0].bid);
        // console.log("预约时间", res.data.ubjson[0].bdate);
        // console.log("预约时段", res.data.ubjson[0].btime);
        // console.log("场室名", res.data.pjson[0].pname);
        // console.log("座位名", res.data.ssjson[0].sname);
        // console.log("靠窗情况", swindow);
        // console.log("插座情况", spower);
        // console.log("其它需求", res.data.ubjson[0].bremark);
        var swindow = res.data.ssjson[0].swindow;
        var spower = res.data.ssjson[0].spower;
        swindow == 'true' ? swindow = '靠窗' : swindow = '不靠窗';
        spower == 'true' ? spower = '有插座' : spower = '无插座';
        useat.setData({
          blist: res.data.ubjson[0],
          slist:res.data.ssjson[0],
          plist: res.data.pjson[0],
          theswindow: swindow,
          thespower: spower,
        })
      }
    })
  }

  
})