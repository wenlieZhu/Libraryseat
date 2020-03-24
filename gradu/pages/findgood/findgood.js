const { $Toast } = require('../../dist/base/index');
Page({

  data: {
    loadtype: true,
    loadmsg: ''
  },

  onLoad: function (options) {
    wx.stopPullDownRefresh();
    var good_list=this;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=good_select',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      }, 
      success: function (res) {
        //console.log(res.data.good_json);
        good_list.setData({
          goodlist: res.data.good_json
        });
      }
    })
  },
  gname_null: function (gn) {
    var gname_this = this;
    var gname_v = gn.detail.detail.value;
    if (gname_v && gname_v!=''){
      gname_this.setData({
        gname_cue:''
      });
    }else{
      gname_this.setData({
        gname_cue: '物品名不能为空'
      });
    }
  },
  gphone_null: function (gp) {
    var gphone_this = this;
    var gphone_v = gp.detail.detail.value;
    if (gphone_v && gphone_v != '') {
      gphone_this.setData({
        gphone_cue: ''
      });
    } else {
      gphone_this.setData({
        gphone_cue: '联系方式不能为空'
      });
    }
  },
  sub_good:function(e){
    var sub_g = this;
    var gncheck = this.data.gname_cue;
    var gdcheck = this.data.gphone_cue;
    if (gncheck == '' && gdcheck==''){
      //console.log('提交成功', e.detail.value);
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=good_add',
        data: {
          gname: e.detail.value.gname,
          gdetail: e.detail.value.gdetail,
          gphone: e.detail.value.gphone
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        success: function (res){
          console.log(res.data);
          $Toast({
            content: '发表成功',
            type: 'success',
            duration: 0,
            mask: false
          });
          setTimeout(() => {
            $Toast.hide();
          }, 2000);
        },
        complete:function(){
          sub_g.onLoad();
        }
      })
    } else {
      sub_g.setData({
        gname_cue: '物品名不能为空',
        gphone_cue: '联系方式不能为空'
      });
    }
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