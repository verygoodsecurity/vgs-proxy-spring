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

        $('#spinner').show();
        $.ajax({
            type: 'POST',
            url: 'INBOUND_ROUTE_URL/cards', // See README.md
            contentType: 'application/json',
            data: json,
            success: function (response) {
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
