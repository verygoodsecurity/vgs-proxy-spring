$(document).ready(function () {
  $('#success-panel').hide();

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

    $.ajax({
      type: 'POST',
      url: '/cards',
      contentType: 'application/json',
      data: json,
      success: function (response) {
        console.log(response);
        togglePanels();
      }
    });
  });

  $('#apply-again-btn').click(function () {
    togglePanels();
  });
});
