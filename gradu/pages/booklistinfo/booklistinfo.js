// pages/booklistinfo/booklistinfo.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },
  can_book:function(can){
    var thebid = this.data.uuid;
    //console.log(this.data.uuid);
    wx.showModal({
      title: '取消预约',
      content: '您确定要取消本次预约吗？',
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定')
          wx.request({
            url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=cancelbook',
            data: {
              bid: thebid,
              btid: 6003
            },
            header: {
              'content-type': 'application/json;application/x-www-form-urlencoded;charset=utf-8',
            },
            success(res) {
              console.log(res.data);
              if (res.data == true) {
                wx.showToast({
                  title: '取消成功',
                  duration: 5000,
                  mask: true,
                  complete: function () {
                    wx.reLaunch({
                      url: '/pages/booklist/booklist',
                    })
                  }
                })
              }

            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
    
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   //console.log(options);
    
    var that=this;
    that.setData({
      uuid: options.bid
    })
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=loadlistinfo&bid=' + options.bid + '&pid=' + options.pid + '&sid=' + options.sid,
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        //console.log(res.data.bjson[0]);
        //console.log(res.data.sjson[0]);
        //console.log(res.data.pjson[0]);
        var swindow = res.data.sjson[0].swindow;
        var spower = res.data.sjson[0].spower;
        swindow == 'true' ? swindow = '靠窗' : swindow = '不靠窗';
        spower == 'true' ? spower = '有插座' : spower = '无插座';
        
        that.setData({
          blist: res.data.bjson[0],
          slist: res.data.sjson[0],
          plist: res.data.pjson[0],
          theswindow: swindow,
          thespower: spower,
        })
      }
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