$(document).ready(function () {
    $('#phone_number').blur(function () {
        var phone = $('#phone_number').val();
        $.get('sign-upAjax',{phone_number : phone},function (responseText) {
            $('#phone_status').html(responseText);
        });
    });
});