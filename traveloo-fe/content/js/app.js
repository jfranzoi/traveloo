$(document).ready(function(){

    var site = 'http://localhost:8080';

    $.ajax({
        url: site + '/info/status',
        crossDomain: true
    })
    .done(function(data){
        console.log('Status: ' + data);
        $('#current-status').text(data['global']);
    });
});