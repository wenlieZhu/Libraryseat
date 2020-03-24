// pages/perset/perset.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dname_default: 0,
  },

  sel_departinfo: function (e) {
    console.log('picker发送选择改变，携带下标为', e.detail.value);
    console.log('picker发送选择改变，携带值为', this.data.dlist[e.detail.value].did);
    this.setData({
      dname_default: e.detail.value,
      did: this.data.dlist[e.detail.value].did,

    })
  },

  sub_perset: function (up){
    // console.log("用户id", this.data.uid);
    // console.log("单位id", this.data.did);
    console.log("得到的数据",up.detail.value);
    // var this_uid = this.data.uid;
    // var udid = this.data.did;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=update',
      data: {
        uid: this.data.uid,
        uname: up.detail.value.uname,
        unumber: up.detail.value.unumber,
        upasswd: up.detail.value.upasswd,
        uphone: up.detail.value.uphone,
        did: this.data.did,
        uemail: up.detail.value.uemail,
        utphone: up.detail.value.utphone,
        ucard: up.detail.value.ucard,
        uqq: up.detail.value.uqq,
        uwechat: up.detail.value.uwechat,
        uremark: up.detail.value.uremark,
      },
      header: {
        'content-type': 'application/json;application/x-www-form-urlencoded;multipart/form-data;charset=utf-8', // 默认值
      },
      success(res) {
        if (res.data == true) {
          console.log(res.data);
          wx.showToast({
            title: '保存成功',
            duration: 5000,
            mask: true,
            complete: function () {
              wx.switchTab({
                url: '/pages/person/person',
              })
            }
          })
        }
      }
    })

    
  },

  mov_index: function (e) {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var umsg = this;
    var con = app.globalData.gnumber;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=loadByUpdate&unumber=' + con,
      //url: 'http://localhost:8085/Libraryseat/userinfoServlet?opr=loadByUpdate&unumber=1500000000',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        console.log(res.data.ujson[0].did);
        // console.log('用户id',res.data.ujson[0].uid);
        // console.log('部门id', res.data.ujson[0].did);
        umsg.setData({
          dlist: res.data.djson,
          ulist: res.data.ujson[0],
          uid: res.data.ujson[0].uid,
          did:res.data.ujson[0].did
        })
      },

    })
  },

  
})