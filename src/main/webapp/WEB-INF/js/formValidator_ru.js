$(document).ready(function() {
    $('#reg_form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u0432\u0430\u0448\u0435 \u0438\u043C\u044F'
                    }
                }
            },

            phone_number: {
                validators: {
                    notEmpty: {
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043D\u043E\u043C\u0435\u0440 \u0442\u0435\u043B\u0435\u0444\u043E\u043D\u0430'
                    },
                    regexp: {
                        regexp: /^\+7(\s+)?\(?([0-9]{3})\)?(\s+)?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$/,
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u0432\u0441\u0435 \u0446\u0438\u0444\u0440\u044B'
                    }
                }
            },

            date_of_birth: {
                validators: {
                    notEmpty: {
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u0432\u0430\u0448\u0443 \u0434\u0430\u0442\u0443 \u0440\u043E\u0436\u0434\u0435\u043D\u0438\u044F'
                    },
                    regexp: {
                        regexp: /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/,
                        message: '\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043A\u043E\u0440\u0440\u0435\u043A\u0442\u043D\u0443\u044E \u0434\u0430\u0442\u0443'
                    }
                }
            },

            password: {
                validators: {
                    notEmpty: {
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C'
                    },
                    regexp: {
                        regexp: /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]{0,})(?=\S+$).{8,24}$/,
                        message: '\u041F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0441\u043E\u0441\u0442\u043E\u044F\u0442\u044C \u0438\u0437 8 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432, \u0430 \u0442\u0430\u043A\u0436\u0435 \u0438\u043C\u0435\u0442\u044C \u043E\u0434\u043D\u0443 \u043C\u0430\u043B\u0435\u043D\u044C\u043A\u0443\u044E \u043E\u0434\u043D\u0443 \u0431\u043E\u043B\u044C\u0448\u0443\u044E \u043B\u0430\u0442\u0438\u043D\u0441\u043A\u0443\u044E \u0431\u0443\u043A\u0432\u0443'
                    }
                }
            },

            confirm_password: {
                validators: {
                    identical: {
                        field: 'password',
                        message: '\u041F\u043E\u043B\u044F \u043F\u0430\u0440\u043E\u043B\u0435\u0439 \u0434\u043E\u043B\u0436\u043D\u044B \u0441\u043E\u0432\u043F\u0430\u0434\u0430\u0442\u044C'
                    },
                    notEmpty: {
                        message: '\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C'
                    }
                }
            },


        }
    })


        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
            $('#reg_form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function(result) {
                console.log(result);
            }, 'json');
        });


});
