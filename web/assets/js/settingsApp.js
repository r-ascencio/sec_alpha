/* 
 * 
 * ${baseURL}/admin/configs/restart.
 * adminConfigs.jsp
 * require: 'kendoUI.all.js'
 * require: 'utils.js'
 * require: 'jquery.js'
 * 
 */

$(document).ready(function() {
    var questionModal = $("#questionModal").kendoWindow({
        title: "Esta seguro de querer reiniciar el sistema?",
        visible: false,
        width: "300px"
    }).data("kendoWindow");


    $("#btnOpenQstnModal").click(function() {
        $("#questionModal").data("kendoWindow").center().open();
    });

    $("#btnCloseQstnModal").click(function() {
        $("#questionModal").data("kendoWindow").close();
    });

    $("#btnProcceddRestore").click(function() {
        var url = Globals.baseURL + "/admin/configs/restart";

        $.post(url)
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Reinicio completo');
            $("#questionModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnPro..click.

    var RstPModal = $("#RstPModal").kendoWindow({
        title: "Esta seguro de querer reiniciar la votacion por presidentes?",
        visible: false,
        width: "300px"
    }).data("kendoWindow");


    $("#btnOpenRstPModal").click(function() {
        $("#RstPModal").data("kendoWindow").center().open();
    });

    $("#btnCloseRstPModal").click(function() {
        $("#RstPModal").data("kendoWindow").close();
    });

    $("#btnProcceddRestoreP").click(function() {
        var url = Globals.baseURL + "/admin/configs/restartP";

        $.post(url)
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Reinicio completo');
            $("#RstPModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnPro..click.

    // votacion

    var questionModal = $("#tVotacionModal").kendoWindow({
        title: "Escoger sistema de votación?",
        visible: false,
        width: "300px"
    }).data("kendoWindow");


    $("#btnOpentVotacionModal").click(function() {
        $("#tVotacionModal").data("kendoWindow").center().open();
    });

    $("#btnClosetVotacionModal").click(function() {
        $("#tVotacionModal").data("kendoWindow").close();
    });

    $("#btnProcceddtVotacionP").click(function() {
        var url = Globals.baseURL + "/admin/configs/tipoVotacion";

        $.post(url, {tipoVotacion: "P"})
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error, cambiando a la votacion por Presidente!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Se ha escogido la votacion por Presidentes');
            $("#tVotacionModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnProcceddtVotacionP..click.

    $("#btnProcceddtVotacionN").click(function() {
        var url = Globals.baseURL + "/admin/configs/tipoVotacion";

        $.post(url, {tipoVotacion: "N"})
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error, cambiando a la votacion por Presidentes!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Se ha escogido la votacion por Presidentes');
            $("#tVotacionModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnProcceddtVotacionN..click.


    $("#btnProcceddtVotacion5").click(function() {
        var url = Globals.baseURL + "/admin/configuraciones/setCincoElecciones";

        $.post(url)
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error, cambiando a la votacion por 10 Candidatos!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Se ha escogido la votacion por 10 Candidatos');
            $("#tVotacionModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnProcceddtVotacionN..click.

    // votacion

    var questionModal = $("#tVotacionCModal").kendoWindow({
        title: "Escoger sistema de votación?",
        visible: false,
        width: "300px"
    }).data("kendoWindow");


    $("#btnOpentVotacionCModal").click(function() {
        $("#tVotacionCModal").data("kendoWindow").center().open();
    });

    $("#btnClosetVotacionCModal").click(function() {
        $("#tVotacionCModal").data("kendoWindow").close();
    });

    $("#btnProcceddtVotacionCP").click(function() {
        var url = Globals.baseURL + "/admin/configs/setCandidatoP";

        $.post(url)
                .always(function() {
            $("#message").removeClass('hidden');
        })
                .fail(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('critical')
                    .text('Ha ocurrido un error, cambiando la fase en la votacion por Candidatos!');
        })
                .done(function() {
            $("#message")
                    .removeClass('hidden')
                    .addClass('success')
                    .text('Se ha escogido la fase en la votacion por candidatos');
           $("#tVotacionCModal").data("kendoWindow").close();
            location.href = Globals.baseURL + "/admin/configs";
        });
    }); // end btnProcceddtVotacionP..click.

});


