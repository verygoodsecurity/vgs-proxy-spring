$(document).ready(function () {
  let togglePanels = function () {
    let array = ['#apply-panel', '#success-panel'];
    $.each(array, function (index, element) {
      $(element).toggle();
    });
  };

  let serializeJSON = function (form) {
    let object = {};

    let array = $(form).serializeArray();
    $.each(array, function () {
      object[this.name] = this.value;
    });

    return JSON.stringify(object);
  };

  $('#user-form').submit(function (event) {
    event.preventDefault();

    let json = serializeJSON(this);
    console.log(json);

    $('#spinner').show();
    $.ajax({
      type: 'POST',
      url: 'https://tntlm8fhgcf.SANDBOX.verygoodproxy.io/cards',
      contentType: 'application/json',
      data: json,
      success: function (response) {
        console.log(response);
        $.each(response, function (name, value) {
          let element = $('#' + name);
          if (element) {
            element.text(value || '');
          }
        });
        togglePanels();
      },
      error: function (jqXHR) {
        alert(jqXHR.responseText);
      },
      complete: function () {
        $('#spinner').hide();
      }
    });
  });

  $('#apply-again-btn').click(function () {
    togglePanels();
  });
});
