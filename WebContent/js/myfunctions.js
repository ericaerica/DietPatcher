


    function dtval(d,e) {

        var pK = e ? e.which : window.event.keyCode;
        if (pK == 8) {d.value = substr(0,d.value.length-1); return;}
        var dt = d.value;
        var da = dt.split('/');
        for (var a = 0; a < da.length; a++) {if (da[a] != +da[a]) da[a] = da[a].substr(0,da[a].length-1);}
            if (da[0] > 12) {da[1] = da[0].substr(da[0].length-1,1);da[0] = '0'+da[0].substr(0,da[0].length-1);}
        if (da[1] > 31) {da[2] = da[1].substr(da[1].length-1,1);da[1] = '0'+da[1].substr(0,da[1].length-1);}
        if (da[2] > 9999) da[1] = da[2].substr(0,da[2].length-1);
        dt = da.join('/');
        if (dt.length == 2  || dt.length == 5) dt += '/';
        d.value = dt;

        var today = new Date();
        var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();
if(dd<10) {dd='0'+dd} 
    if(mm<10) {mm='0'+mm} 
        today = mm + '/' + dd + '/'+yyyy;
    if (dt.length == 0) dt += today;
    d.value = dt;
}


$('#inputDate').DatePicker({
    format:"Y/m/d",
    date: $('#inputDate').val(),
    current: $('#inputDate').val(),
    starts: 1,
    position: 'r',
    onBeforeShow: function(){
        $('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
    },
    onChange: function(formated, dates){
        $('#inputDate').val(formated);
        $('#inputDate').DatePickerHide();
    }
});

function allowOnlyNumbers(){
                $(this).keydown(function (e) {
                    // Allow: backspace, delete, tab, escape, enter and .
                    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                         // Allow: Ctrl+A, Command+A
                        (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || 
                         // Allow: home, end, left, right, down, up
                        (e.keyCode >= 35 && e.keyCode <= 40)) {
                             // let it happen, don't do anything
                             return;
                    }
                    // Ensure that it is a number and stop the keypress
                    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                        e.preventDefault();
                    }
                });

            }