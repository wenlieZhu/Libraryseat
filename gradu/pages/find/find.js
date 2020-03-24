
Page({

  data: {
    loadtype: true,
    loadmsg: ''
    
  },

  //走进本馆
  find_goin:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=find_goin',
    })
  },

  //失约公示
  find_miss: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_miss',
    })
  },

  //我要留言
  mov_message:function(){
    wx.navigateTo({
      url: '/pages/message/message',
    });
  },

  //失物招领
  mov_findgood:function(){
    wx.navigateTo({
      url: '/pages/findgood/findgood',
    });
  },
  
  //预约制度
  find_book:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=find_book',
    })
  },

  //关于我们
  mov_about: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=about_us',
    })
  },

  //馆内wifi
  find_wifi:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=find_wifi',
    })
  },

  //通知公告
  find_safe:function(){
    wx.navigateTo({
      url: '/pages/article/article?arid=find_safe',
    })
  },

  //新书推荐
  find_new: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_new',
    })
  },

  //服务指南
  find_service: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_service',
    })
  },

  //电子资源
  find_eco: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_eco',
    })
  },

  //学习干货
  find_ganhuo: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_ganhuo',
    })
  },

  //更多咨询
  find_more: function () {
    wx.navigateTo({
      url: '/pages/article/article?arid=find_more',
    })
  },

  onLoad: function (options) {
    wx.stopPullDownRefresh();
    var msg_list=this;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/adminServlet',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      }, 
      success: function (res){
         //console.log(res.data.msg_json);
        msg_list.setData({
          msglist: res.data.msg_json
        });
      }
    })
  },

  onPullDownRefresh: function () {
    var that = this;
    that.setData({
      loadtype: true,
      loadmsg: ''
    });
    this.onLoad();
  },

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

  }
})