const { $Toast } = require('../../dist/base/index');
Page({

  data: {
    
  },
  bianhua:function(md){
    //console.log(md.detail.detail.value);
    var md_this=this;
    var mdy = md.detail.detail.value;
    if (mdy && mdy!=''){
      md_this.setData({
        md_cue: ''
      });
    } else{
      md_this.setData({
        md_cue: '主题不能为空'
      });
    }
    
  },
  
  sub_msg:function(e){
    var sub_m=this;
    var nchenk = this.data.md_cue;
    if (nchenk==''){
      console.log('提交成功',e.detail.value);
      wx.request({
        url: 'https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=msg_add',
        data: {
          mtitle: e.detail.value.mtitle,
          mdetail: e.detail.value.mdetail,
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
            wx.switchTab({
              url: '/pages/find/find',
            });
          }, 2000);
          
        }
      })
    }else{
      sub_m.setData({
        md_cue: '主题不能为空'
      });
    }
    
    
    
  },
  
  onLoad: function (options) {

  },


  onShareAppMessage: function () {

  }
})