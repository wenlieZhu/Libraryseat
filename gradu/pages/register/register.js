// pages/register/register.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    position: 'left',
    fruit: [{
      id: 1,
      name: '男',
    }, {
      id: 2,
      name: '女'
    },
    ],
    dname_default:0,
    did:9000,
    utname_default:0
  },

  handleFruitChange({ detail = {} }) {
    this.setData({
      current: detail.value,
      usex_cue: ''
    });
    console.log("性别:", detail.value);
  },

  sel_usertype: function (e) {
    console.log('用户类型，携带下标为', e.detail.value);
    console.log('用户类型，携带值为', this.data.utlist[e.detail.value].utid);
    this.setData({
      utname_default: e.detail.value,
      utid: this.data.utlist[e.detail.value].utid,
      utype_cue: ''

    })
  },

  sel_departinfo: function (e) {
    console.log('所在单位，携带下标为', e.detail.value);
    console.log('所在单位，携带值为', this.data.dlist[e.detail.value].did);
    this.setData({
      dname_default: e.detail.value,
      did: this.data.dlist[e.detail.value].did,
      udepart_cue: ''
    })
  },
  

  sub_register:function(reg){
    // var that = this;
    // console.log("得到的数据：",reg.detail.value);
    // console.log("性别:", that.data.current);
    // console.log("所在单位:", that.data.did);
    // console.log("用户类型:", that.data.utid);
    var that = this;
    var uname_cue = this.data.uname_cue;
    var unumber_cue = this.data.unumber_cue;
    var fupasswd_cue = this.data.fupasswd_cue;
    var upasswd_cue = this.data.upasswd_cue;
    var usex_cue = this.data.usex_cue;
    var uphone_cue = this.data.uphone_cue;
    var utype_cue = this.data.utype_cue;
    var udepart_cue = this.data.udepart_cue;
    if (uname_cue == '' && unumber_cue == '' && fupasswd_cue == '' && upasswd_cue == '' && usex_cue == '' && uphone_cue == '' && utype_cue == '' && udepart_cue == ''){
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=add',
        data: {
          uname: reg.detail.value.uname,
          unumber: reg.detail.value.unumber,
          upasswd: reg.detail.value.upasswd,
          urex: that.data.current,
          uphone: reg.detail.value.uphone,
          utid: that.data.utid,
          did: that.data.did,
          uemail: reg.detail.value.uemail,
          utphone: reg.detail.value.utphone,
          ucard: reg.detail.value.ucard,
          uqq: reg.detail.value.uqq,
          uwechat: reg.detail.value.uwechat,
          uremark: reg.detail.value.uremark,
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded;charset=utf-8' // 默认值
        },

        method: 'POST',
        success(res) {
          console.log(res.data);
          wx.reLaunch({
            url: '/pages/login/login',
          })
        }
      })
    }else{
      if (uname_cue !== '') {
        that.setData({
          uname_cue: '*姓名不能为空'
        });
      }
      if (unumber_cue !== '') {
        that.setData({
          unumber_cue: '*学号不能为空'
        });
      }
      if (fupasswd_cue !== '') {
        that.setData({
          fupasswd_cue: '*密码不能为空'
        });
      }
      if (upasswd_cue !== '') {
        that.setData({
          upasswd_cue: '*两次密码不一致'
        });
      }
      if (usex_cue !== '') {
        that.setData({
          usex_cue: '*性别未设置'
        });
      }
      if (uphone_cue !== '') {
        that.setData({
          uphone_cue: '*手机号码不能为空'
        });
      }
      if (utype_cue !== '') {
        that.setData({
          utype_cue: '*用户类型未设置'
        });
      }
      if (udepart_cue !== '') {
        that.setData({
          udepart_cue: '*所在单位未设置'
        });
      }
    }
    
  },

  mov_login: function(e){
    wx.redirectTo({
      url: '/pages/login/login',
    })
  },

  mov_index: function (e) {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  //判断姓名input
  uname_null:function(una){
    var uname_this = this;
    var uname_v = una.detail.detail.value;
    if (uname_v && uname_v != '') {
      uname_this.setData({
        uname_cue: ''
      });
    } else {
      uname_this.setData({
        uname_cue: '*姓名不能为空'
      });
    }
  },

  //判断学号input
  unumber_null: function (unm) {
    var unumber_this = this;
    var unumber_v = unm.detail.detail.value;
    if (unumber_v && unumber_v != '') {
      unumber_this.setData({
        unumber_cue: ''
      });
    } else {
      unumber_this.setData({
        unumber_cue: '*学号不能为空'
      });
    }
  },

  //判断初次密码input
  fupasswd_null:function(fup){
    var fupasswd_this = this;
    var fupasswd_v = fup.detail.detail.value;
    if (fupasswd_v && fupasswd_v != '') {
      fupasswd_this.setData({
        fupasswd_cue: '',
        fupasswd: fupasswd_v
      });
    } else {
      fupasswd_this.setData({
        fupasswd_cue: '*密码不能为空',
        fupasswd:''
      });
    }
  },

  //判断确认密码input
  upasswd_null: function (upa) {
    var upasswd_this = this;
    var upasswd_v = upa.detail.detail.value;
    var f_upasswd = this.data.fupasswd;
    if (upasswd_v && upasswd_v != '' && upasswd_v == f_upasswd) {
      upasswd_this.setData({
        upasswd_cue: '',
      });
    } else {
      upasswd_this.setData({
        upasswd_cue: '*两次密码不一致',
      });
    }
  },

  //判断性别input
  usex_null:function(){
    var usex_this=this;
    var usex_v = this.data.current;
    if (usex_v){
      usex_this.setData({
        usex_cue:''
      });
    }else{
      usex_this.setData({
        usex_cue: '*性别未设置'
      });
    }
  },

  //判断手机号input
  uphone_null: function (uph) {
    var uphone_this = this;
    var uphone_v = uph.detail.detail.value;
    if (uphone_v && uphone_v != '') {
      uphone_this.setData({
        uphone_cue: ''
      });
    } else {
      uphone_this.setData({
        uphone_cue: '*手机号码不能为空'
      });
    }
  },

  //判断用户类型input
  utype_null: function () {
    var utype_this = this;
    var utype_v = this.data.utid;
    if (utype_v) {
      utype_this.setData({
        utype_cue: ''
      });
    } else {
      utype_this.setData({
        utype_cue: '*用户类型未设置'
      });
    }
  },

  //判断所在单位input
  udepart_null: function () {
    var udepart_this = this;
    var udepart_v = this.data.did;
    if (udepart_v==9000) {
      udepart_this.setData({
        udepart_cue: '*所在单位未设置'
      });
    } else {
      udepart_this.setData({
        udepart_cue: ''
      });
    }
  },
  
  
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var umsg = this;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=loadByadd',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        umsg.setData({
          dlist: res.data.djson,
          utlist:res.data.utjson,
        })
      },

    })
  },

  
})