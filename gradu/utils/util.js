const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  //const second = date.getSeconds()

  // return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
  return [year].map(formatNumber)+'年'+ 
  [month].map(formatNumber)+ '月'+
  [day].map(formatNumber)+'日 ' + [hour, minute].map(formatNumber).join(':')
}
//年
const formatyear = date => {
  const year = date.getFullYear();
  return [year].map(formatNumber)+'';
}
//月
const formatmonth = date => {
  const month = date.getMonth() + 1
  return [month].map(formatNumber) + '';
}
//日
const formatday = date => {
  const day = date.getDate()
  return [day].map(formatNumber) + '';
}
//时
const formathour = date => {
  const hour = date.getHours()
  return [hour].map(formatNumber) + '';
}


const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime: formatTime,
  formatyear: formatyear,
  formatmonth: formatmonth,
  formatday: formatday,
  formathour: formathour
}
