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
                        message: 'Please enter your name'
                    }
                }
            },

            phone_number: {
                validators: {
                    notEmpty: {
                        message: 'Please enter your phone number'
                    },
                    regexp: {
                        regexp: /^\+7(\s+)?\(?([0-9]{3})\)?(\s+)?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$/,
                        message: 'Please enter all digits'
                    }
                }
            },

            date_of_birth: {
                validators: {
                    notEmpty: {
                        message: 'Please enter your date of birth'
                    },
                    regexp: {
                        regexp: /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/,
                        message: 'Please enter the correct date'
                    }
                }
            },

            password: {
                validators: {
                    notEmpty: {
                        message: 'Please enter your password'
                    },
                    regexp: {
                        regexp: /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]{0,})(?=\S+$).{8,24}$/,
                        message: 'Password must have 8 characters, and also one big and one small letter'
                    }
                }
            },

            confirm_password: {
                validators: {
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    notEmpty: {
                        message: 'Please confirm password'
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