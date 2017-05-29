$(document).ready(function(){
    var date_input=$('input[name="date_of_birth"]');
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    date_input.datepicker({
        format: 'yyyy-mm-dd',
        container: container,
        language: "ru",
        todayHighlight: false,
        autoclose: true,
        orientation: "bottom auto"
    })
});

