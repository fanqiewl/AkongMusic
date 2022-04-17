/**
 *
 * 依赖于JQuery与Bootstrap样式的Alert插件
 *
 */
(function ($) {
  $.extend({
    jqAlert: function (option) {
      let _this = this;
      let settings = {
        type: 'info', //info,success,warning,danger
        content: '提示内容',
        autoClose: true
      };
      let $dom = $('.my_alert-wrapper');
      if ($dom.length === 0) {
        $(document.body).append('<div class="my_alert-wrapper col-lg-5 col-md-7 col-xs-11"></div>');
      }
      $dom = $('.my_alert-wrapper');
      $.extend(settings, option);
      let box = $('<div class="alert alert-dismissible" animation=""></div>');
      box.addClass('alert-' + settings.type);
      let typeIcon = $('<i class="glyphicon my_alert-icon"></i>');
      typeIcon.addClass('glyphicon-' + (settings.type == 'danger' ? 'remove' : (settings.type == 'success' ? 'ok' : settings.type)) + '-sign');
      let contentBox = $('<span class="my_alert-content"></span>');
      contentBox.text(settings.content);
      let closeIcon = $('<button style="right: 0" type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
      box.append(typeIcon).append(contentBox).append(closeIcon);
      $dom.append(box);
      if (settings.autoClose === true) {
        setTimeout(function () {
          box.remove();
        }, 3 * 1000);
      }
      closeIcon.on('click', function () {
        box.remove();
      });
    }
  });
})(jQuery);
