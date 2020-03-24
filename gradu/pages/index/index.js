//index.js
//获取应用实例
const app = getApp();
const { $Toast } = require('../../dist/base/index');
var util = require('../../utils/util.js');
Page({
  data: {
    imgUrls: [
      {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp1.jpg'
      }, {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp2.jpg'
      }, {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp3.jpg'
      }, {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp4.jpg'
      }, {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp5.jpg'
      }, {
        link: '/pages/index/index',
        url: 'https://www.zhuwenlie.cn/Libraryseat/images/libp6.jpg'
      }
    ],
    indicatorDots: true,  //小点
    autoplay: true,  //是否自动轮播
    interval: 5000,  //间隔时间
    duration: 1000,  //滑动时间
    zixiu: '***',
    yuelan: '***',
    dier: '***',
    loadtype:true,
    loadmsg:''
  },
  mov_booking: function (e) {
    wx.navigateTo({
      url: '/pages/booking/booking',
    })
  },
  //触底操作
  onReachBottom: function () {
    var buttomload = this;
    var timer = setTimeout(function () {
      buttomload.setData({
        loadtype: false,
        loadmsg: '加载完成'
      });
    }, 2000);
  },
  
  onShareAppMessage: function () {

  },
  onPullDownRefresh: function () {
    var that = this;
    that.setData({
      loadtype: true,
      loadmsg: ''
    });
    
    this.onLoad();
  },

  onLoad: function () {
    
    wx.stopPullDownRefresh();
    $Toast({
      content: '数据更新成功',
      type: 'success'
    });
    var time = util.formatTime(new Date());
    this.setData({
      time: time
    });
    var seat_sur=this;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=countseat',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        //console.log(res.data.dier[0]);
        seat_sur.setData({
            zixiu: res.data.zixiu[0],
            yuelan: res.data.yuelan[0],
            dier: res.data.dier[0],
        });
      }
    })
  },

  //预约制度
  mov_rule:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=find_book',
    })
  },

  //活动
  mov_activity: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=ins_activity',
    })
  },

  //维普
  mov_vpu: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=ins_vpu',
    })
  }
  
})
