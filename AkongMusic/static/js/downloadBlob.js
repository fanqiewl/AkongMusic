/**
 * 获取页面文件名
 * @param url 文件url
 * @param fileName 文件名
 */
function downloadUrlFile(url, fileName) {
  // 设置一个编号
  let id = new Date().getTime();

  // 添加数据到vue的集合中
  window.wv.$data.downloadList.push({
    id: id,
    name: fileName,
    loaded: 0,
    total: 0
  });

  const xhr = new XMLHttpRequest();
  xhr.open('GET', url, true);
  xhr.responseType = 'blob';
  xhr.onload = () => {
    if (xhr.status === 200) {
      // 获取文件blob数据并保存
      saveAs(xhr.response, fileName);
    }
  };

  xhr.onprogress = function (evt) {
    if (this.readyState != 4) {
      //evt.loaded;  //已经上传大小情况
      //evt.total; 附件总大小
      let loaded = evt.loaded;
      let tot = evt.total;

      // 遍历集合获取到当前数据
      window.wv.$data.downloadList.forEach(o => {
        // 根据编号判断是否是当前数据
        if (o.id == id) {
          // 设置数据
          o.loaded = loaded;
          o.total = tot;
        }
      });
    } else {
      // 下载已完成
      // 遍历集合获取到当前数据
      window.wv.$data.downloadList.forEach(o => {
        // 根据编号判断是否是当前数据
        if (o.id == id) {
          // 设置进度为完成
          o.loaded = o.total;
        }
      });
    }

  }

  xhr.send();
}

/**
 * URL方式保存文件到本地
 * @param data 文件的blob数据
 * @param name 文件名
 */
function saveAs(data, name) {
  let export_blob = new Blob([data]);

  if (window.navigator.msSaveOrOpenBlob) {
    // ie的下载
    navigator.msSaveBlob(export_blob, name);
  } else {
    // 非ie的下载
    let urlObject = window.URL || window.webkitURL || window;
    let save_link = document.createElement('a');

    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    save_link.click();
  }
}
