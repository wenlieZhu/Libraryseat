// pages/booking/booking.js
const { $Toast } = require('../../dist/base/index');
var app = getApp();
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    booktime: [{
      id: 1,
      name: '上午【8:00 ~ 12:00】',
    }, {
      id: 2,
      name: '下午【13:00 ~ 17:00】'
    }, {
      id: 3,
      name: '晚上【18:00 ~ 22:00】'
    }],
    placename: [{
      id: 4001,
      name: '读者自修室',
    }, {
      id: 4002,
      name: '报刊阅览室'
    }, {
      id: 4003,
      name: '第二阅览室'
    }],
    swindow:false,
    spower:false,
    
  },

  

  sel_btime({ detail = {} }) {
    console.log('时段为', detail.value);
    this.setData({
      btime: detail.value,
      btime_cue: ''
    });
  },

  sel_pid({ detail = {} }) {
   // console.log('场地为：', detail.value);
    if (detail.value=='读者自修室'){
      this.setData({
        pname: detail.value,
        pid:4001,
        bplace_cue:''
      });
    console.log('场地为：', this.data.pid);
    }else if (detail.value == '报刊阅览室') {
      this.setData({
        pname: detail.value,
        pid: 4002,
        bplace_cue: ''
      });
      console.log('场地为：', this.data.pid);
    } else if (detail.value == '第二阅览室') {
      this.setData({
        pname: detail.value,
        pid: 4003,
        bplace_cue: ''
      });
      console.log('场地为：', this.data.pid);
    }
  },

  formSubmit(e) {
    // console.log('预约时间', this.data.bdate);
    // console.log('预约时段：', this.data.btime);
    // console.log('场地：', this.data.pid);
    // console.log('其它需求：' , e.detail.value.remark);
    // console.log('靠窗：', this.data.swindow);
    // console.log('插座：', this.data.spower);
    var sub_this=this;
    var bdate_cue = this.data.bdate_cue;
    var btime_cue = this.data.btime_cue;
    var bplace_cue = this.data.bplace_cue;
    if (bdate_cue == '' && btime_cue == '' && bplace_cue==''){
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/bookServlet?opr=add',
        data: {
          uid: this.data.uid,
          bdate: this.data.bdate,
          btime: this.data.btime,
          pid: this.data.pid,
          bremark: e.detail.value.remark,
          swindow: this.data.swindow.bdat,
          spower: this.data.spower,
        },
        success: function (res) {

          if (res.data == true) {
            console.log(res.data);
            $Toast({
              content: '预约成功',
              type: 'success',
              duration: 0,
              mask: false
            });
            setTimeout(() => {
              $Toast.hide();
              wx.redirectTo({
                url: '/pages/bookok/bookok',
              });
              // wx.showToast({
              //   title: '预约成功',
              //   duration: 5000,
              //   mask: true,
              //   complete: function () {
              //     wx.redirectTo({
              //       url: '/pages/bookok/bookok',
              //     })
              //   }
              // })
            }, 2000);
            
          } else {
            wx.showModal({

              content: '需求已满，请重新选择',
              success: function (res) {
                if (res.confirm) {
                  console.log('用户点击确定')
                } else if (res.cancel) {
                  console.log('用户点击取消')
                }
              }
            })
          }

        },
      })
    }else{
      if (bdate_cue!==''){
        sub_this.setData({
          bdate_cue:'*没有选择日期'
        });
      }
      if (btime_cue !== '') {
        sub_this.setData({
          btime_cue: '*没有选择时段'
        });
      }
      if (bplace_cue !== '') {
        sub_this.setData({
          bplace_cue: '*没有选择场室'
        });
      }
    }
    
    
  },

  formReset() {
    console.log('form发生了reset事件')
  },

  

  sel_window(event) {
    var detail = event.detail;
    console.log('靠窗情况：',event.detail.value);
    this.setData({
      swindow: detail.value
    })

  },

  sel_power(event) {
    var detail = event.detail;
    console.log('插座情况：',event.detail.value);
    this.setData({
      spower: detail.value
    })

  },
  
  bindDateChange(e) {
    console.log('预约日期为', e.detail.value)
    this.setData({
      bdate: e.detail.value,
      bdate_cue: ''
    })
  },

  //判断日期
  // bdate_null:function(){
  //   var bdate_this=this;
  //   var bdate_v = this.data.bdate;
  //   if (bdate_v){
  //     bdate_this.setData({
  //       bdate_cue:''
  //     });
  //   }else{
  //     bdate_this.setData({
  //       bdate_cue: '*没有选择日期'
  //     });
  //   }
  // },

  //判断时段
  
  

  onLoad: function (options) {
    var umsg = this;
    // var byear = util.formatyear(new Date());
    // console.log(byear);

    // var bmonth = util.formatmonth(new Date());
    // console.log(bmonth);

    // var bday = util.formatday(new Date());
    // console.log(bday);

    // var bhour = util.formathour(new Date());
    // console.log(bhour);
    // umsg.setData({
    //   byear: byear,
    //   bmonth: bmonth,
    //   bday: bday,
    //   bhour: bhour,
    //   balld: byear + '-' + bmonth + '-' + bday
    // });

    //明天日期
    var next_day = new Date();
    next_day.setTime(next_day.getTime() + 24 * 60 * 60 * 1000);
    var nmonth = (next_day.getMonth() + 1) < 10 ? '0' + (next_day.getMonth() + 1): (next_day.getMonth() + 1);
    var ndate = next_day.getDate() < 10 ? '0' + next_day.getDate() : next_day.getDate();
    var next_date = next_day.getFullYear() + "-" + nmonth + "-" + ndate;
    //console.log('明天'+next_date);
    
    //七天后日期
    var seven_day = new Date();
    seven_day.setTime(seven_day.getTime() + 24 * 60 * 60 * 1000*7);
    var smonth = (seven_day.getMonth() + 1) < 10 ? '0' + (seven_day.getMonth() + 1) : (seven_day.getMonth() + 1);
    var sdate = seven_day.getDate() < 10 ? '0' + seven_day.getDate() : seven_day.getDate();
    var seven_date = seven_day.getFullYear() + "-" + smonth + "-" + sdate;
    //console.log('七天后'+seven_date);
    umsg.setData({
      nextday: next_date,
      sevenday: seven_date
    })
 
    var con = app.globalData.gnumber;
    wx.request({
      url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=loadByUpdate&unumber=' + con,
      // url: 'https://www.zhuwenlie.cn/Libraryseat/userinfoServlet?opr=loadByUpdate&unumber=1500000000',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        // console.log(res.data);
        // console.log('用户id',res.data.ujson[0].uid);
        // console.log('部门id', res.data.ujson[0].did);
        if (res.data){
          umsg.setData({
            uid: res.data.ujson[0].uid,
          })
        } else {
          wx.showModal({
            content: '您还没有登录，请先登录',
            success: function (res) {
              if (res.confirm) {
                wx.redirectTo({
                  url: '/pages/login/login',
                })
              } else if (res.cancel) {
                wx.switchTab({
                  url: '/pages/index/index',
                })
              }
            }
          })
        }
        
        
      },

    })
  },

  
})