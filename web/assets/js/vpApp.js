/* 
 * vpApp
 * Here is defined all the shits for votacionPresidente.jsp
 * TODO: Write a better summary.
 */

var dataSource = new kendo.data.DataSource({
    transport: {
        read: {
            url: Globals.baseURL + "/votacion/presidente/electos",
            dataType: "json"
        }
    }
});

$("#listView").kendoListView({
    dataSource: dataSource,
    selectable: "single",
    change: function() {
        var data = dataSource.view(),
                selected = $.map(this.select(), function(item) {
            var rindex = Math.round($(item).index() / 2); // fuck this, fuck fuck fuck.
            console.log(data[rindex]);
            return data[rindex].alumno;
        });
    },
    template: kendo.template($("#template").html())
});


// IF THERE'S TIME, DO AN APP FOR THIS. with urls, views, templates and shits.

setTimeout(function() {
    $('.candidato').dblclick(function(e) {
        var rindex = Math.round($(this).index() / 2);
        var candidato = dataSource.view()[rindex];
        $("#cImage").find('img').attr('src', candidato.imagen_src);
        $("#cAlumno").text(candidato.alumno);
        $("#cNombre").text(candidato.nombre);
        //the chosen one.
        $("#tothechosenone").find('input#cName').val(candidato.nombre);
        $("#tothechosenone").find('input#cImg').val(candidato.imagen_src);
        $("#tothechosenone").find('input#cAlumnus').val(candidato.alumno);
        $("#tothechosenone").find('input#cUID').val($(this).attr('data-uid'));
        //show the chosen one.
        $('#listView').slideUp("slow");
        $("#cC").slideDown("slow").delay(100);
    });
}, 4500);

$('#cancel').click(function() {
    $("#cC").hide("slow");
    $('#listView').show("slow").delay(100);
});