/* 
 * vpApp
 * Here is defined all the shits for votacionPresidente.jsp
 * TODO: Write a better summary.
 */

var candidatos = {
    currentIndex: null,
    primer: {
        itemIndex: null //save at listView and $.click events
    },
    segundo: {
        itemIndex: null //save at listView and $.click events
    }
};

var dataSource = new kendo.data.DataSource({
    transport: {
        read: {
            url: Globals.baseURL + "/votacion/elecciones/electos",
            dataType: "json"
        }
    }
});

$("#listView").kendoListView({
    dataSource: dataSource,
    selectable: "multiple",
    change: function() {
        console.log("changue listview");
        var data = dataSource.view(),
                selected = $.map(this.select(), function(item) {
            var rindex = Math.round($(item).index() / 2); // fuck this, fuck fuck fuck.

            candidatos.currentIndex = $(item).index();
            console.log(" currentIndex: " + candidatos.currentIndex);

            return data[rindex].alumno;
        });
    },
    template: kendo.template($("#template").html())
});

var listView = $("#listView").data("kendoListView");
// IF THERE'S TIME, DO AN APP FOR THIS. with urls, views, templates and shits.

setTimeout(function() {

    $('.candidato').click(function(e) {

        console.log("click event");
        console.log(candidatos);

        if (candidatos.primer.itemIndex === null
                && candidatos.segundo.itemIndex === null) {

            candidatos.primer.itemIndex = candidatos.currentIndex;

        } else if (candidatos.primer.itemIndex !== null
                && candidatos.segundo.itemIndex === null) {

            listView.select(
                    listView.element.children().get(
                    candidatos.primer.itemIndex
                    ));

            candidatos.segundo.itemIndex = candidatos.currentIndex;

        } else if (candidatos.primer.itemIndex !== null &&
                candidatos.segundo.itemIndex !== null) {

            listView.select(
                    listView.element.children().get(
                    candidatos.segundo.itemIndex
                    ));

            /*
             $(listView.element.children().get(
             candidatos.currentIndex
             )).removeClass('k-state-selected');*/
        }

    });

}, 4500);

$('#cancel').click(function() {
    $("#cC").hide("slow");
    $('#listView').show("slow").delay(100);
});